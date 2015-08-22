package nobugs.team.shopping.utils;

import android.util.Log;

import com.yuntongxun.ecsdk.ECError;
import com.yuntongxun.ecsdk.ECVoIPCallManager;
import com.yuntongxun.ecsdk.ECVoIPCallManager.CallBackEntity;
import com.yuntongxun.ecsdk.ECVoIPCallManager.OnMakeCallBackListener;
import com.yuntongxun.ecsdk.ECVoIPSetupManager;
import com.yuntongxun.ecsdk.VoIPCallUserInfo;

/**
 * com.yuntongxun.ecdemo.ui.voip in ECDemo_Android
 * Created by Jorstin on 2015/7/3.
 */
public class VoIPCallHelper implements OnMakeCallBackListener {

    private static final String TAG = "VoIPCallHelper";
    private static VoIPCallHelper ourInstance = new VoIPCallHelper();

    public static VoIPCallHelper getInstance() {
        return ourInstance;
    }

	private ECVoIPCallManager mCallInterface;
	private ECVoIPSetupManager mCallSetInterface;
	private SubVoIPCallback mVoIPCallback;
	private OnCallEventNotifyListener mOnCallEventNotifyListener;
	/** 当前正在进行的VoIP通话信息 */
	private ECVoIPCallManager.VoIPCall mCallEntry;
	/** 是否正在通话 */
	private boolean isCalling = false;
	public static boolean mHandlerVideoCall = false;
	protected VoIPCallUserInfo mUserInfo;
	
	private VoIPCallHelper() {
		mVoIPCallback = new SubVoIPCallback();

        mUserInfo = new VoIPCallUserInfo();
//        final ClientUser clientUser = CCPAppManager.getClientUser();
       /* if(clientUser != null) {

//            Log.d(TAG , "username " + clientUser.getUserName() + " , userphone " + clientUser.getUserId());
            mUserInfo.setNickName(clientUser.getUserName());
            mUserInfo.setPhoneNumber(clientUser.getUserId());
        }*/
	}

    /**
     * 发起�?��VoIP呼叫
     * @param callType 呼叫类型（音视频、落地）
     * @param number 呼叫号码
     */
    public static String makeCall(ECVoIPCallManager.CallType callType , String number) {
        initCall();
        if(getInstance().mCallInterface == null) {
            return null;
        }
        if(getInstance().mCallSetInterface != null) {
            getInstance().mCallSetInterface.setVoIPCallUserInfo(getInstance().mUserInfo);
        }
        return getInstance().mCallInterface.makeCall(callType , number);
    }
    
    public static void makeCallBack(ECVoIPCallManager.CallType callType , String number) {
        initCall();
        if(getInstance().mCallInterface == null) {
//            Log.e(TAG , "make call error : ECVoIPCallManager null");
            return ;
        }
        CallBackEntity callBackEntity =new CallBackEntity();
//        callBackEntity.caller=CCPAppManager.getUserId();
        callBackEntity.caller="";
        callBackEntity.callerSerNum=number;
        
        callBackEntity.called=number;
//        callBackEntity.calledSerNum=CCPAppManager.getUserId();
        callBackEntity.calledSerNum="";

         getInstance().mCallInterface.makeCallBack(callBackEntity, getInstance());
    }

    /**
     * 返回SDK静音状�?
     * @return 静音状�?
     */
    public static boolean getMute(){
        if(getInstance().mCallSetInterface == null) {
//            Log.e(TAG , "get mute error : CallSetInterface null");
            return false;
        }
        return getInstance().mCallSetInterface.getMuteStatus();
    }

    /**
     * 返回SDK免提状�?
     * @return 免提状�?
     */
    public static boolean getHandFree(){
        if(getInstance().mCallSetInterface == null) {
            Log.e(TAG , "get hand free error : CallSetInterface null");
            return false;
        }
        return getInstance().mCallSetInterface.getLoudSpeakerStatus();
    }

    /**
     * 切换SDK静音状�?
     */
	public static void setMute() {
		initCall();
		if (getInstance().mCallSetInterface == null) {
            Log.e(TAG, "set mute error : CallSetInterface null");
            return ;
		}
		getInstance().mCallSetInterface .setMute(!getInstance().mCallSetInterface.getMuteStatus());
	}

    /**
     * 切换SDK免提状�?
     */
	public static void setHandFree() {

		initCall();
		if (getInstance().mCallInterface == null) {
//            Log.e(TAG , "set hand free error : CallSetInterface null");
            return ;
		}
		int r = getInstance().mCallSetInterface .enableLoudSpeaker(!getInstance().mCallSetInterface.getLoudSpeakerStatus());
	}

	/**
	 * 释放通话
	 * 
	 * @param callId
	 *            通话唯一标识
	 */
	public static void releaseCall(String callId) {
		initCall();
		if (getInstance().mCallInterface == null) {
			Log.e(TAG, "release call error : ECVoIPCallManager null");
			return;
		}
		getInstance().mCallInterface.releaseCall(callId);
	}

    /**
     * 接听来电
     * @param callId 通话唯一标识
     */
    public static void acceptCall(String callId) {
        initCall();
        if(getInstance().mCallInterface == null) {
            Log.e(TAG , "accept call error : ECVoIPCallManager null");
            return ;
        }
        getInstance().mCallInterface.acceptCall(callId);
    }

    /**
     * 拒接来电
     * @param callId 通话唯一标识
     */
    public static void rejectCall(String callId) {
        initCall();
        if(getInstance().mCallInterface == null) {
            Log.e(TAG , "reject call error : ECVoIPCallManager null");
            return ;
        }
        // 3 主动拒接
        getInstance().mCallInterface.rejectCall(callId , 3);
    }

	/**
	 * 初始化呼叫控制器
	 */
	private static void initCall() {
		if (ourInstance == null) {
			return;
		}
        ourInstance.mCallInterface = CCPHelper.getVoIPCallManager();
        ourInstance.mCallSetInterface = CCPHelper.getVoIPSetManager();

		if (ourInstance.mCallInterface != null) {
			ourInstance.mCallInterface.setOnVoIPCallListener(getInstance().mVoIPCallback);
		}
        if(ourInstance.mCallSetInterface != null) {
//            ClientUser clientUser = CCPAppManager.getClientUser();
           /* if(clientUser != null) {
                // 设置呼叫参数信息[呼叫昵称、呼叫手机号]
                VoIPCallUserInfo info = new VoIPCallUserInfo(clientUser.getUserName() , clientUser.getUserId());
                //ourInstance.mCallSetInterface.setVoIPCallUserInfo(info);
            }*/
        }
	}

    /**
     * 设置通话界面刷新通知接口
     * @param callback OnCallEventNotifyListener
     */
    public static void setOnCallEventNotifyListener(OnCallEventNotifyListener callback) {
        getInstance().mOnCallEventNotifyListener = callback;
        initCall();
    }

    /**
     * 当前是否正在进行VoIP通话
     * @return 是否通话
     */
    public static boolean isHoldingCall() {
        return getInstance().isCalling;
    }

    public void release() {
        ourInstance = null;
    }

    /**
     * VoIP通话状�?通知
     */
    public interface OnCallEventNotifyListener {
        /**
         * 正在连接服务�?
         * @param callId 通话的唯�?���?
         */
        void onCallProceeding(String callId);
        
        void onMakeCallback(ECError arg0, String arg1, String arg2);

        /**
         * 对方正在振铃
         * @param callId 通话的唯�?���?
         */
        void onCallAlerting(String callId);

        /**
         * 对方应答（�?话完全建立）
         * @param callId 通话的唯�?���?
         */
        void onCallAnswered(String callId);

        /**
         * 呼叫失败
         * @param callId 通话的唯�?��识（有可能为Null�?
         * @param reason 呼叫失败原因
         */
        void onMakeCallFailed(String callId, int reason);

        /**
         * VoIP通话结束
         * @param callId 通话的唯�?���?
         */
        void onCallReleased(String callId);
    }

    private class SubVoIPCallback implements  ECVoIPCallManager.OnVoIPListener {

        @Override
        public void onCallEvents(ECVoIPCallManager.VoIPCall voipCall) {
            // 接收VoIP呼叫事件回调
            if(voipCall == null) {
                Log.e(TAG , "handle call event error , voipCall null" );
                return ;
            }
            OnCallEventNotifyListener notifyListener = VoIPCallHelper.this.mOnCallEventNotifyListener;
            if(notifyListener == null) {
                Log.e(TAG , "notify error , notifyListener null");
                return ;
            }
            mCallEntry = voipCall;
            String callId = mCallEntry.callId;
            switch (voipCall.callState) {
                case ECCALL_PROCEEDING:
                    notifyListener.onCallProceeding(callId);
                    break;
                case ECCALL_ALERTING:
                    notifyListener.onCallAlerting(callId);
                    break;
                case ECCALL_ANSWERED:
                    mHandlerVideoCall = false;
                    notifyListener.onCallAnswered(callId);
                    break;
                case ECCALL_FAILED:
                    notifyListener.onMakeCallFailed(callId , mCallEntry.reason);
                    break;
                case ECCALL_RELEASED:
                    mHandlerVideoCall = false;
                    notifyListener.onCallReleased(callId);
                    break;
                default:
                    break;
            }
            isCalling = (voipCall.callState == ECVoIPCallManager.ECCallState.ECCALL_ANSWERED);
        }
    }

	@Override
	public void onMakeCallback(ECError ecError, String caller, String called) {
		OnCallEventNotifyListener notifyListener = VoIPCallHelper.this.mOnCallEventNotifyListener;
		if(notifyListener == null) {
			return ;
		}
		notifyListener.onMakeCallback(ecError, caller, called);
		
	}
	
	
	

}