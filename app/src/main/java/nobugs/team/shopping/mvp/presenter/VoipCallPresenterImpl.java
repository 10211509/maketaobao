package nobugs.team.shopping.mvp.presenter;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yuntongxun.ecsdk.CameraCapability;
import com.yuntongxun.ecsdk.CameraInfo;
import com.yuntongxun.ecsdk.ECDevice;
import com.yuntongxun.ecsdk.ECError;
import com.yuntongxun.ecsdk.ECVoIPCallManager;
import com.yuntongxun.ecsdk.ECVoIPSetupManager;
import com.yuntongxun.ecsdk.SdkErrorCode;

import org.webrtc.videoengine.ViERenderer;

import java.util.Arrays;

import de.greenrobot.event.EventBus;
import nobugs.team.shopping.event.CallBeginEvent;
import nobugs.team.shopping.event.RemoteShopSelectEvent;
import nobugs.team.shopping.event.ShopSelectEvent;
import nobugs.team.shopping.im.IMSendHelper;
import nobugs.team.shopping.mvp.model.Shop;
import nobugs.team.shopping.mvp.model.User;
import nobugs.team.shopping.mvp.view.VoipCallView;
import nobugs.team.shopping.repo.Repository;
import nobugs.team.shopping.utils.VoIPCallHelper;

/**
 * Created by xiayong on 2015/8/17.
 */
public class VoipCallPresenterImpl extends BasePresenter<VoipCallView> implements VoipCallPresenter, VoIPCallHelper.OnCallEventNotifyListener {

    public static final String EXTRA_OUTGOING_CALL = "con.yuntongxun.ecdemo.VoIP_OUTGOING_CALL";
    private static final String TAG = "DEBUG_VOIP";
    private User mOwnUser;
    private User mPeerUser;
    private Shop mSellerShop;
    private String mCurrentCallId;
    private boolean isConnect = false;
    private boolean isIncomingCall;
    private CameraInfo[] cameraInfos;
    private int numberOfCameras;
    private int defaultCameraId;
    private int mCameraCapbilityIndex;
    private int cameraCurrentlyLocked;
    private boolean isFrontCamera;

    public VoipCallPresenterImpl(VoipCallView callOutView) {
        super(callOutView);
    }

    @Override
    public void onCreate() {
        VoIPCallHelper.mHandlerVideoCall = true;

        isIncomingCall = !(getActivity().getIntent().getBooleanExtra(EXTRA_OUTGOING_CALL, false));

        mOwnUser = Repository.getInstance().getLoginUser();

        initCameraInfo();
        initCameraSurfaceView();
        initSpeaker();
        isConnect = true;

        if (isIncomingCall) {
            // action to receive a call
            handleReceiveCall();
        }

        EventBus.getDefault().registerSticky(this);
    }

    @Override
    public void onStart() {
        VoIPCallHelper.setOnCallEventNotifyListener(this);
    }

    @Override
    public void onStop() {
        VoIPCallHelper.setOnCallEventNotifyListener(null);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        VoIPCallHelper.mHandlerVideoCall = false;
    }

    public void onEventMainThread(ShopSelectEvent event) {
        if (event.getShop() != null) {
            mSellerShop = event.getShop();
            mPeerUser = event.getShop().getOwner();
        }
        if (!isIncomingCall && mPeerUser != null) {
            //action to launch a call
            getView().showCallOutView(mPeerUser);//show view
            doMakeCall(ECVoIPCallManager.CallType.VIDEO);
            IMSendHelper.sendSelectShop(mOwnUser, mPeerUser.getPhone(), mSellerShop);
        }
        // remove the sticky event
        EventBus.getDefault().removeStickyEvent(event);
    }

    public void onEventMainThread(RemoteShopSelectEvent event) {
        mSellerShop = event.getShop();
        mPeerUser = event.getBuyer();

        getView().showCallInView(mPeerUser);

        // remove the sticky event
        EventBus.getDefault().removeStickyEvent(event);
    }

    private void initCameraSurfaceView() {
        ECDevice.getECVoIPSetupManager().setVideoView(getView().getRemoteCameraView(), null);
        DisplayLocalSurfaceView();
    }

    private void initCameraInfo() {
        cameraInfos = ECDevice.getECVoIPSetupManager().getCameraInfos();
        // Find the ID of the default camera
        if (cameraInfos != null) {
            numberOfCameras = cameraInfos.length;
            // Find the total number of cameras available
            for (int i = 0; i < numberOfCameras; i++) {
                if (cameraInfos[i].index == android.hardware.Camera.CameraInfo.CAMERA_FACING_FRONT) {
                    defaultCameraId = i;
                    comportCapbilityIndex(cameraInfos[i].caps);
                }
            }
        }
    }

    private void initSpeaker() {
        ECDevice.getECVoIPSetupManager().enableLoudSpeaker(true);
    }

    @Override
    public void onUIHangupCall() {
        //hang up the phone
        doHandUpCall();
    }

    @Override
    public void onUIAnswerCall() {
        VoIPCallHelper.acceptCall(mCurrentCallId);
    }

    @Override
    public void onUIChangeSilence() {
        boolean isMute = ECDevice.getECVoIPSetupManager().getMuteStatus();
        Toast.makeText(getActivity(), "已" + (isMute ? "关闭" : "开启") + "静音模式", Toast.LENGTH_SHORT).show();
        ECDevice.getECVoIPSetupManager().setMute(!isMute);
    }

    @Override
    public void onUIChangeCamera() {
// check for availability of multiple cameras
        if (numberOfCameras == 1) {
            return;
        }
//        mCameraSwitch.setEnabled(false);
        getView().showChangeCamera(isFrontCamera, false);
        // OK, we have multiple cameras.
        // Release this camera -> cameraCurrentlyLocked
        cameraCurrentlyLocked = (cameraCurrentlyLocked + 1)
                % numberOfCameras;
        comportCapbilityIndex(cameraInfos[cameraCurrentlyLocked].caps);

        ECDevice.getECVoIPSetupManager().selectCamera(cameraCurrentlyLocked,
                mCameraCapbilityIndex, 15, ECVoIPSetupManager.Rotate.ROTATE_AUTO, false);

        if (cameraCurrentlyLocked == android.hardware.Camera.CameraInfo.CAMERA_FACING_FRONT) {
            defaultCameraId = android.hardware.Camera.CameraInfo.CAMERA_FACING_FRONT;
            Toast.makeText(getActivity(), "切换到前置摄像头", Toast.LENGTH_SHORT).show();
            isFrontCamera = true;
        } else {
            defaultCameraId = android.hardware.Camera.CameraInfo.CAMERA_FACING_BACK;
            Toast.makeText(getActivity(), "切换到后置摄像头", Toast.LENGTH_SHORT).show();
            isFrontCamera = false;
        }
//        mCameraSwitch.setEnabled(true);
        getView().showChangeCamera(isFrontCamera, true);
    }

    @Override
    public void onUIExit() {
        // Hang up the video call...
        isConnect = false;
        doHandUpCall();
    }

    private void doMakeCall(ECVoIPCallManager.CallType callType) {
        mCurrentCallId = VoIPCallHelper.makeCall(callType, mPeerUser.getPhone());
        if (TextUtils.isEmpty(mCurrentCallId)) {
            //call failed
            getView().contactFailed();
        }
    }

    private void handleReceiveCall() {
        mCurrentCallId = getActivity().getIntent().getStringExtra(ECDevice.CALLID);
        String callPhone = getActivity().getIntent().getStringExtra(ECDevice.CALLER);

//        mPeerUser = new User();
//        mPeerUser.setPhone(callPhone);

//        getView().showCallInView(mPeerUser);
    }

    protected void doHandUpCall() {
        // Hang up the video call...
        Log.d(TAG, "Voip talk hand up, CurrentCallId " + mCurrentCallId);
        try {
            if (mCurrentCallId != null) {
                if (isIncomingCall && !isConnect) {
                    VoIPCallHelper.rejectCall(mCurrentCallId);
                } else {
                    VoIPCallHelper.releaseCall(mCurrentCallId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                getView().goBack();
            }
        }, 2000L);
    }

    /**
     * 连接到服务器
     *
     * @param callId 通话的唯一标识
     */
    @Override
    public void onCallProceeding(String callId) {
        if (callId != null && callId.equals(mCurrentCallId)) {
            Toast.makeText(getActivity(), "正在呼叫对方，请稍后...", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onMakeCallback(ECError arg0, String arg1, String arg2) {

    }

    /**
     * 连接到对端用户，播放铃音
     *
     * @param callId 通话的唯一标识
     */
    @Override
    public void onCallAlerting(String callId) {
        if (callId != null && callId.equals(mCurrentCallId)) {
            Toast.makeText(getActivity(), "等待对方接听...", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 应答成功，通话计时开始
     *
     * @param callId 通话的唯一标识
     * @param direct
     */
    @Override
    public void onCallAnswered(String callId, ECVoIPCallManager.ECCallDirect direct) {
        if (callId != null && callId.equals(mCurrentCallId)) {
            User loginUser = Repository.getInstance().getLoginUser();
//            getView().showVideoView(loginUser);
            EventBus.getDefault().postSticky(new CallBeginEvent(mOwnUser, mPeerUser, mSellerShop, mCurrentCallId));

            switch (direct) {
                case EC_INCOMING:   //打进来，卖家
                    getView().showSellerVideoView(loginUser, mSellerShop.getId());
                    break;
                case EC_OUTGOING:   //打出去，买家
                default:
                    getView().showBuyerVideoView(loginUser);
                    break;
            }
        }
    }

    /**
     * 呼叫失败
     *
     * @param callId 通话的唯一标识（有可能为Null）
     * @param reason 呼叫失败原因
     */
    @Override
    public void onMakeCallFailed(String callId, int reason) {
        if (callId != null && callId.equals(mCurrentCallId)) {
            switch (reason) {
                case SdkErrorCode.REMOUNT_CALL_BUSY:
                    Toast.makeText(getActivity(), "拨号失败，对方拒接", Toast.LENGTH_SHORT).show();
                    break;
                case 175408:
                    Toast.makeText(getActivity(), "拨号次数太多，请稍后再试", Toast.LENGTH_SHORT).show();
                    break;
                case 175404:
                    Toast.makeText(getActivity(), "拨号失败，对方不在线", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(getActivity(), "拨号失败，原因：reason" + reason, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
        doHandUpCall();
    }

    /**
     * 通话结束，通话计时结束
     *
     * @param callId 通话的唯一标识
     */
    @Override
    public void onCallReleased(String callId) {
        if (callId != null && callId.equals(mCurrentCallId)) {
            Toast.makeText(getActivity(), "通话结束...", Toast.LENGTH_SHORT).show();
            isConnect = false;
            doHandUpCall();
        }
    }

    public void comportCapbilityIndex(CameraCapability[] caps) {
        if (caps == null) {
            return;
        }
        int pixel[] = new int[caps.length];
        int _pixel[] = new int[caps.length];
        for (CameraCapability cap : caps) {
            if (cap.index >= pixel.length) {
                continue;
            }
            pixel[cap.index] = cap.width * cap.height;
        }

        System.arraycopy(pixel, 0, _pixel, 0, caps.length);

        Arrays.sort(_pixel);
        for (int i = 0; i < caps.length; i++) {
            if (pixel[i] == /*_pixel[0]*/ 352 * 288) {
                mCameraCapbilityIndex = i;
                return;
            }
        }
    }

    public void DisplayLocalSurfaceView() {
        // Create a RelativeLayout container that will hold a SurfaceView,
        // and set it as the content of our activity.
        SurfaceView localView = ViERenderer.CreateLocalRenderer(getActivity());
        // localView.setLayoutParams(layoutParams);
        ViewGroup vgLocal = getView().getLocalCameraView();
        localView.setZOrderOnTop(true);
        vgLocal.removeAllViews();
        vgLocal.setBackgroundColor(getActivity().getResources().getColor(
                android.R.color.white));
        vgLocal.addView(localView);
    }

}
