package nobugs.team.shopping.mvp.presenter;

import android.text.TextUtils;

import com.yuntongxun.ecsdk.ECDevice;
import com.yuntongxun.ecsdk.ECVoIPCallManager;

import de.greenrobot.event.EventBus;
import nobugs.team.shopping.db.entity.User;
import nobugs.team.shopping.mvp.view.VoipCallView;
import nobugs.team.shopping.utils.VoIPCallHelper;

/**
 * Created by xiayong on 2015/8/17.
 */
public class VideoCallPresenterImpl extends BasePresenter<VoipCallView> implements VideoCallPresenter {

    public static final String EXTRA_OUTGOING_CALL = "con.yuntongxun.ecdemo.VoIP_OUTGOING_CALL";
    private User mCallee;
    private String mCurrentCallId;
    private boolean isConnect = false;
    private boolean isIncomingCall;

    public VideoCallPresenterImpl(VoipCallView callOutView) {
        setView(callOutView);
    }

    @Override
    public void onCreate() {
        isIncomingCall = !(getActivity().getIntent().getBooleanExtra(EXTRA_OUTGOING_CALL, false));
        EventBus.getDefault().registerSticky(this);

        if(isIncomingCall){
            // action to receive a call
            handleReceiveCall();
        }
    }


    public void onEventMainThread(User user) {
        mCallee = user;
        if (!isIncomingCall){
            //action to launch a call            
            getView().showCallOutView(mCallee);//show view
            makeCall(ECVoIPCallManager.CallType.VIDEO);
        }
    }

    @Override
    public void onDestroy() {

        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onHangupBtnClick() {
        //hang up the phone
        hangupCall();
    }

    @Override
    public void onAnswerBtnClick() {
        //TODO navigate to VideoActivity,send the mCurrentCallId
        getView().answer();
    }

    private void makeCall(ECVoIPCallManager.CallType callType) {
        mCurrentCallId = VoIPCallHelper.makeCall(callType, mCallee.getPhone());
        if(TextUtils.isEmpty(mCurrentCallId)){
            //call failed
            getView().contactFailed();
        }
    }

    private void hangupCall() {
        try {
            if (!TextUtils.isEmpty(mCurrentCallId)) {

                if(isIncomingCall && !isConnect){
                    VoIPCallHelper.rejectCall(mCurrentCallId);
                }else {
                    VoIPCallHelper.releaseCall(mCurrentCallId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!isConnect) {
            getView().hangup();
        }
    }
    private void handleReceiveCall(){
        mCurrentCallId = getActivity().getIntent().getStringExtra(ECDevice.CALLID);
        String callPhone = getActivity().getIntent().getStringExtra(ECDevice.CALLER);
        mCallee = new User();
        mCallee.setPhone(callPhone);
        getView().showCallInView(mCallee);
    }
}
