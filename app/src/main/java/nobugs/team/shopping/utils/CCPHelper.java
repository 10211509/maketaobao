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
import nobugs.team.shopping.ui.activity.CallInActivity;

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

    //第二步：设置注册参数、设置通知回调监听
    /*public void register() {
        ECInitParams params = ECInitParams.createParams();
        params.setUserid(AppConfig.CCP_ACCOUNT_SID);
        params.setAppKey(AppConfig.CCP_APP_ID);
        params.setToken(AppConfig.CCP_APP_TOKEN);
        *//*
         params.setUserid("用户的app账号");
        params.setAppKey("应用ID");
        params.setToken("应用Token");*//*
        // 1代表用户名+密码登陆（可以强制上线，踢掉已经在线的设备）
        // 2代表自动重连注册（如果账号已经在其他设备登录则会提示异地登陆）
        // 3 LoginMode（FORCE_LOGIN  AUTO）
        params.setMode(ECInitParams.LoginMode.AUTO);
        // 设置登陆状态回调
        params.setOnDeviceConnectListener(new ECDevice.OnECDeviceConnectListener() {
            public void onConnect() {
                // 兼容4.0，5.0可不必处理
            }

            @Override
            public void onDisconnect(ECError error) {
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
        });

        // 设置SDK接收消息回调
        params.setOnChatReceiveListener(new OnChatReceiveListener() {
            @Override
            public void OnReceivedMessage(ECMessage msg) {
                // 收到新消息
            }

            @Override
            public void OnReceiveGroupNoticeMessage(ECGroupNoticeMessage notice) {
                // 收到群组通知消息（有人加入、退出...）
                // 可以根据ECGroupNoticeMessage.ECGroupMessageType类型区分不同消息类型
            }

            @Override
            public void onOfflineMessageCount(int count) {
                // 登陆成功之后SDK回调该接口通知账号离线消息数
            }

            @Override
            public int onGetOfflineMessage() {
                return 0;
            }

            @Override
            public void onReceiveOfflineMessage(List msgs) {
                // SDK根据应用设置的离线消息拉去规则通知应用离线消息
            }

            @Override
            public void onReceiveOfflineMessageCompletion() {
                // SDK通知应用离线消息拉取完成
            }

            @Override
            public void onServicePersonVersion(int version) {
                // SDK通知应用当前账号的个人信息版本号
            }

            @Override
            public void onReceiveDeskMessage(ECMessage ecMessage) {

            }

            @Override
            public void onSoftVersion(String s, int i) {

            }
        });

        // 获得SDKVoIP呼叫接口
        // 注册VoIP呼叫事件回调监听
        ECVoIPCallManager callInterface = ECDevice.getECVoIPCallManager();
        if (callInterface != null) {
            callInterface.setOnVoIPCallListener(new ECVoIPCallManager.OnVoIPListener() {
                @Override
                public void onCallEvents(ECVoIPCallManager.VoIPCall voipCall) {
                    // 处理呼叫事件回调
                    if (voipCall == null) {
                        Log.e("SDKCoreHelper", "handle call event error , voipCall null");
                        return;
                    }
                    // 根据不同的事件通知类型来处理不同的业务
                    ECVoIPCallManager.ECCallState callState = voipCall.callState;
                    switch (callState) {
                        case ECCALL_PROCEEDING:
                            // 正在连接服务器处理呼叫请求
                            break;
                        case ECCALL_ALERTING:
                            // 呼叫到达对方客户端，对方正在振铃
                            break;
                        case ECCALL_ANSWERED:
                            // 对方接听本次呼叫
                            break;
                        case ECCALL_FAILED:
                            // 本次呼叫失败，根据失败原因播放提示音
                            break;
                        case ECCALL_RELEASED:
                            // 通话释放[完成一次呼叫]
                            break;
                        default:
                            Log.e("SDKCoreHelper", "handle call event error , callState " + callState);
                            break;
                    }
                }
            });
        }

        // 注册会议消息处理监听
        if (ECDevice.getECMeetingManager() != null) {
            ECDevice.getECMeetingManager().setOnMeetingListener(new OnMeetingListener() {
                @Override
                public void onReceiveInterPhoneMeetingMsg(ECInterPhoneMeetingMsg msg) {
                    // 处理实时对讲消息Push
                }

                @Override
                public void onReceiveVoiceMeetingMsg(ECVoiceMeetingMsg msg) {
                    // 处理语音会议消息push
                }

                @Override
                public void onReceiveVideoMeetingMsg(ECVideoMeetingMsg msg) {
                    // 处理视频会议消息Push（暂未提供）
                }
            });
        }


    }*/

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
        Intent intent = new Intent(mContext, CallInActivity.class);
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

    }

    @Override
    public void onDisconnect(ECError ecError) {

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
