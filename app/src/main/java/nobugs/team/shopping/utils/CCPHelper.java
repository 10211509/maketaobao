package nobugs.team.shopping.utils;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.yuntongxun.ecsdk.ECDevice;
import com.yuntongxun.ecsdk.ECError;
import com.yuntongxun.ecsdk.ECInitParams;
import com.yuntongxun.ecsdk.ECVoIPCallManager;
import com.yuntongxun.ecsdk.ECVoIPSetupManager;
import com.yuntongxun.ecsdk.SdkErrorCode;

import nobugs.team.shopping.constant.AppConfig;
import nobugs.team.shopping.ui.activity.VideoCallActivity;

/**
 * Created by xiayong on 2015/8/20.
 */
public class CCPHelper implements ECDevice.InitListener, ECDevice.OnECDeviceConnectListener, ECDevice.OnLogoutListener {

    private static final String TAG = "CCPHelper";
    private ECInitParams mInitParams;
    private Context mContext;
    private static CCPHelper instance;

    public static CCPHelper getInstance(Context context) {
        if (instance == null) {
            synchronized (CCPHelper.class) {
                if (instance == null) {
                    instance = new CCPHelper(context);
                }
            }
        }
        return instance;
    }

    public static ECVoIPCallManager getVoIPCallManager() {
        return ECDevice.getECVoIPCallManager();
    }

    public static ECVoIPSetupManager getVoIPSetManager() {
        return ECDevice.getECVoIPSetupManager();
    }

    private CCPHelper(Context context) {
        this.mContext = context;
    }

    //初始化SDK
    public void init() {
        // 判断SDK是否已经初始化，如果已经初始化则可以直接调用登陆接口
// 没有初始化则先进行初始化SDK，然后调用登录接口注册SDK
        if (!ECDevice.isInitialized()) {
            ECDevice.initial(mContext, this);
        }
    }


    @Override
    public void onInitialized() {
        // SDK已经初始化成功
        Log.d(TAG, "ECSDK is ready");
//            ClientUser clientUser = CCPAppManager.getClientUser();
        if (mInitParams == null || mInitParams.getInitParams() == null || mInitParams.getInitParams().isEmpty()) {
            mInitParams = ECInitParams.createParams();
        }
        mInitParams.reset();
        // 如：VoIP账号/手机号码/..
        mInitParams.setUserid(AppConfig.CCP_ACCOUNT_SID);
        // appkey
        mInitParams.setAppKey(AppConfig.CCP_APP_ID);
        // mInitParams.setAppKey(/*clientUser.getAppKey()*/"ff8080813d823ee6013d856001000029");
        // appToken
        mInitParams.setToken(AppConfig.CCP_APP_TOKEN);
        // mInitParams.setToken(/*clientUser.getAppToken()*/"d459711cd14b443487c03b8cc072966e");
        // ECInitParams.LoginMode.FORCE_LOGIN
        mInitParams.setMode(ECInitParams.LoginMode.AUTO);

        // 如果有密码（VoIP密码，对应的登陆验证模式是）
        // ECInitParams.LoginAuthType.PASSWORD_AUTH
           /* if (!TextUtils.isEmpty(clientUser.getPassword())) {
                mInitParams.setPwd(clientUser.getPassword());
            }

            // 设置登陆验证模式（是否验证密码/如VoIP方式登陆）
            if (clientUser.getLoginAuthType() != null) {
                mInitParams.setAuthType(clientUser.getLoginAuthType());
            }

            if (!mInitParams.validate()) {
                ToastUtil.showMessage(R.string.regist_params_error);
                Intent intent = new Intent(ACTION_SDK_CONNECT);
                intent.putExtra("error", -1);
                mContext.sendBroadcast(intent);
                return;
            }*/

        //第三步：验证参数是否正确，注册SDK
        if (!mInitParams.validate()) {

            return;
        }
        // 设置接收VoIP来电事件通知Intent
        // 呼入界面activity、开发者需修改该类
        Intent intent = new Intent(mContext, VideoCallActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mInitParams.setPendingIntent(pendingIntent);

        // 设置SDK注册结果回调通知，当第一次初始化注册成功或者失败会通过该引用回调
        // 通知应用SDK注册状态
        // 当网络断开导致SDK断开连接或者重连成功也会通过该设置回调
//            mInitParams.setOnChatReceiveListener(IMChattingHelper.getInstance());//TODO 貌似是聊天相关的
        mInitParams.setOnDeviceConnectListener(this);


        if (ECDevice.getECMeetingManager() != null) {
            //TODO 暂时删除，貌似IM相关
//                ECDevice.getECMeetingManager().setOnMeetingListener(MeetingMsgReceiver.getInstance());
        }
        ECDevice.login(mInitParams);

    }

    @Override
    public void onError(Exception exception) {
        Log.e(TAG, "ECSDK couldn't start: " + exception.getLocalizedMessage());
        ECDevice.unInitial();
    }

    @Override
    public void onConnect() {
       // 兼容4.0，5.0可不必处理
    }

    @Override
    public void onDisconnect(ECError ecError) {
        // 兼容4.0，5.0可不必处理
    }

    @Override
    public void onConnectState(ECDevice.ECConnectState state, ECError error) {
        if (state == ECDevice.ECConnectState.CONNECT_FAILED) {
            if (error.errorCode == SdkErrorCode.SDK_KICKED_OFF) {
                //账号异地登陆
            } else {
                //连接状态失败
            }
            return;
        } else if (state == ECDevice.ECConnectState.CONNECT_SUCCESS) {
            // 登陆成功
        }
    }

    @Override
    public void onLogout() {

    }
}
