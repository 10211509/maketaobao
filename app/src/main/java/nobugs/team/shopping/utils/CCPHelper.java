/*
 *  Copyright (c) 2013 The CCP project authors. All Rights Reserved.
 *
 *  Use of this source code is governed by a Beijing Speedtong Information Technology Co.,Ltd license
 *  that can be found in the LICENSE file in the root of the web site.
 *
 *   http://www.cloopen.com
 *
 *  An additional intellectual property rights grant can be found
 *  in the file PATENTS.  All contributing project authors may
 *  be found in the AUTHORS file in the root of the source tree.
 */
package nobugs.team.shopping.utils;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.hisun.phone.core.voice.CCPCall;
import com.hisun.phone.core.voice.Device;
import com.hisun.phone.core.voice.Device.CallType;
import com.hisun.phone.core.voice.DeviceListener;
import com.hisun.phone.core.voice.listener.OnChatroomListener;
import com.hisun.phone.core.voice.listener.OnIMListener;
import com.hisun.phone.core.voice.listener.OnInterphoneListener;
import com.hisun.phone.core.voice.listener.OnVoIPListener;
import com.hisun.phone.core.voice.model.CloopenReason;
import com.hisun.phone.core.voice.model.DownloadInfo;
import com.hisun.phone.core.voice.model.chatroom.Chatroom;
import com.hisun.phone.core.voice.model.chatroom.ChatroomMember;
import com.hisun.phone.core.voice.model.chatroom.ChatroomMsg;
import com.hisun.phone.core.voice.model.im.IMAttachedMsg;
import com.hisun.phone.core.voice.model.im.IMDismissGroupMsg;
import com.hisun.phone.core.voice.model.im.IMInviterJoinGroupReplyMsg;
import com.hisun.phone.core.voice.model.im.IMInviterMsg;
import com.hisun.phone.core.voice.model.im.IMJoinGroupMsg;
import com.hisun.phone.core.voice.model.im.IMProposerMsg;
import com.hisun.phone.core.voice.model.im.IMQuitGroupMsg;
import com.hisun.phone.core.voice.model.im.IMRemoveMemeberMsg;
import com.hisun.phone.core.voice.model.im.IMReplyJoinGroupMsg;
import com.hisun.phone.core.voice.model.im.IMTextMsg;
import com.hisun.phone.core.voice.model.im.InstanceMsg;
import com.hisun.phone.core.voice.model.interphone.InterphoneInviteMsg;
import com.hisun.phone.core.voice.model.interphone.InterphoneMember;
import com.hisun.phone.core.voice.model.interphone.InterphoneMsg;
import com.hisun.phone.core.voice.model.interphone.InterphoneOverMsg;
import com.hisun.phone.core.voice.model.setup.UserAgentConfig;
import com.hisun.phone.core.voice.util.Log4Util;
import com.hisun.phone.core.voice.util.SdkErrorCode;

import nobugs.team.shopping.app.base.MyApplication;
import nobugs.team.shopping.constant.CCPConfig;
import nobugs.team.shopping.ui.activity.CallInActivity;

/**
 * VOIP Helper for Activity, it already has been did something important jobs
 * that activity just show state of ui by handler.
 * 
 * Before running the demo, you should be become a developer by CCP web site so that 
 * you can get the main account and token, otherwise also see test info.
 * 
 * @version 1.0.0
 */
public class CCPHelper implements CCPCall.InitListener, DeviceListener 
															,OnVoIPListener
															,OnIMListener
															,OnChatroomListener
															,OnInterphoneListener{

	public static final String DEMO_TAG = "CCP_Demo";
	// our suggestion this context should be ApplicationContext
	private Context context;

	// invoked after created it
	private Device device;
	
	private RegistCallBack mCallback;
	
	public static final int SDK_NOTIFICATION		    = 99;
	public static final int ICON_LEVEL_ORANGE		    = 0;
	public static final int ICON_LEVEL_GREEN		    = 1;
	public static final int ICON_LEVEL_RED			    = 2;
	public static final int ICON_LEVEL_BLACK			= 3;
	
	private static CCPHelper sInstance;

	/**
	 * 
	* <p>Title: getInstance</p>
	* <p>Description: </p>
	* @see CCPHelper#init(Context, Handler)
	* @return
	 */
	public static CCPHelper getInstance(Context context) {
		if(sInstance == null) {
			sInstance = new CCPHelper(context);
		}
		return sInstance;
	}
	
	
	/**
	 * Constructs a new {@code VoiceHelper} instance.
	 * 
	 * @param context
	 * @see #Context
	 */
	private CCPHelper(Context context ,RegistCallBack rcb) {
		this.context = context;
		this.mCallback = rcb;
		
	}
	
	/**
	 * Constructs a new {@code VoiceHelper} instance.
	 * 
	 * @param context
	 * @see #Context
	 */
	private CCPHelper(Context context) {
		this(context, null);
	}
	
	/**
	 * 
	 * @param rcb
	 */
	public void registerCCP(RegistCallBack rcb) {
		setRegistCallback(rcb);

		Log4Util.init(true);
		CCPCall.init(context, this);
		Log4Util.d(DEMO_TAG , "[VoiceHelper] CCPCallService init");
		
	}

	/**
	 * Callback this method when SDK init success.
	 * 
	 * Please note: you must write info that those remark start.
	 * 
	 * SDK init just once, but device can create more.
	 * 
	 * @see #onInitialized()
	 */
	@Override
	public void onInitialized() {
		// CCP SDK 初始化成功回调此方法，开发者可在该处向云通讯平台注册你的帐号信息
		//利用开发者帐号信息通过CCPCall.createDevice方法与云通讯平台的服务器完成认证，建立连接，并同时创建一个与服务器端
		//通信的Device实例对象，并且设置当收到一个CCP VoIP呼入请求时，启动Android组件的PendingIntent
		try {
			createDevice();
		} catch (Exception e) {
			e.printStackTrace();
			//throw new RuntimeException(e);
			onError(e);
		}
	}
	
	/**
	 * 
	* <p>Title: createDevice</p>
	* <p>Description: modify by version 3.5</p>
	* @throws Exception
	 */
	private void createDevice() throws Exception {
		// 封装参数
		Map<String, String> params = new HashMap<String, String>();
		// * REST服务器地址
		params.put(UserAgentConfig.KEY_IP, CCPConfig.REST_SERVER_ADDRESS);
		// * REST服务器端口
		params.put(UserAgentConfig.KEY_PORT, CCPConfig.REST_SERVER_PORT);
		// * VOIP账号 , 可以填入CCP网站Demo管理中的测试VOIP账号信息
		params.put(UserAgentConfig.KEY_SID, CCPConfig.VoIP_ID);
		// * VOIP账号密码, 可以填入CCP网站Demo管理中的测试VOIP账号密码
		params.put(UserAgentConfig.KEY_PWD, CCPConfig.VoIP_PWD);
		// * 子账号, 可以填入CCP网站Demo管理中的测试子账号信息
		params.put(UserAgentConfig.KEY_SUBID, CCPConfig.Sub_Account);
		// * 子账号密码, 可以填入CCP网站Demo管理中的测试子账号密码
		params.put(UserAgentConfig.KEY_SUBPWD, CCPConfig.Sub_Token);
		// User-Agent
		params.put(UserAgentConfig.KEY_UA, MyApplication.getInstance().getCCPUserAgent());
		
		// 创建Device
		device = CCPCall.createDevice(this /* DeviceListener */, params);
		// 设置当呼入请求到达时, 唤起的界面
		Intent intent = new Intent(context, CallInActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		device.setIncomingIntent(pendingIntent);
		
		
		// set Listener ...
		// In a later version of SDK 3.5, SDK will implement the Interphone, VOIP, voice Chatroom, 
		// completely separate from the IM instant messaging function, if the need to use a function, 
		// only need to set the listener, do not need to ignore 
		// for SDK version 3.5 above
		device.setOnVoIPListener(this);
		device.setOnIMListener(this);
		device.setOnInterphoneListener(this);
		device.setOnChatroomListener(this);
		
		Log4Util.d(DEMO_TAG, "[onInitialized] sdk init success. done");
	}
	
	/**
	 * Callback this method when sdk init errors.
	 * 
	 * @param exception
	 *            SDK init execption
	 * @see CCPHelper#init(Context, Handler)
	 */
	@Override
	public void onError(Exception exception) {
		
		Log4Util.d(DEMO_TAG , "[onError] " + "SDK init error , " + exception.getMessage());
		// CCP SDK注册失败回调此方法
		if(mCallback != null ) {
			mCallback.onRegistResult(WHAT_INIT_ERROR ,"SDK初始化错误, " +  exception.getMessage());
		}
		
		// If not null, you will not be able to execute the init method
		//sInstance = null;
		CCPCall.shutdown();
	}

	/**
	 * handler 转换消息id
	 */
	public static final int WHAT_ON_CONNECT = 0x2000;
	public static final int WHAT_ON_DISCONNECT = 0x2001;
	public static final int WHAT_INIT_ERROR = 0x200A;
	public static final int WHAT_ON_CALL_ALERTING = 0x2002;
	public static final int WHAT_ON_CALL_ANSWERED = 0x2003;
	public static final int WHAT_ON_CALL_PAUSED = 0x2004;
	public static final int WHAT_ON_CALL_PAUSED_REMOTE = 0x2005;
	public static final int WHAT_ON_CALL_RELEASED = 0x2006;
	public static final int WHAT_ON_CALL_PROCEEDING = 0x2007;
	public static final int WHAT_ON_CALL_TRANSFERED = 0x2008;
	public static final int WHAT_ON_CALL_MAKECALL_FAILED = 0x2009;
	public static final int WHAT_ON_CALL_BACKING = 0x201B;
	
	//2013.3.11
	public static final int WHAT_ON_NEW_VOICE = 0x201C;
	public static final int WHAT_ON_AMPLITUDE = 0x201D;
	public static final int WHAT_ON_RECODE_TIMEOUT = 0x202A;
	public static final int WHAT_ON_UPLOAD_VOICE_RES = 0x202B;
	public static final int WHAT_ON_PLAY_VOICE_FINSHING = 0x202C;
	
	public static final int WHAT_ON_INTERPHONE = 0x203A;
	public static final int WHAT_ON_CONTROL_MIC = 0x203B;
	public static final int WHAT_ON_RELEASE_MIC = 0x203C;
	public static final int WHAT_ON_INTERPHONE_MEMBERS = 0x203D;
	public static final int WHAT_ON_INTERPHONE_SIP_MESSAGE = 0x203E;
	public static final int WHAT_ON_DIMISS_DIALOG = 0x204A;;
	
	public static final int WHAT_ON_REQUEST_MIC_CONTROL = 0x204C;
	public static final int WHAT_ON_RELESE_MIC_CONTROL = 0x204D;
	public static final int WHAT_ON_PLAY_MUSIC = 0x204E;
	public static final int WHAT_ON_STOP_MUSIC = 0x204F;

	
	public static final int WHAT_ON_VERIFY_CODE_SUCCESS = 0x205A;
	public static final int WHAT_ON_VERIFY_CODE_FAILED = 0x205B;
	
	// Chatroom
	public static final int WHAT_ON_CHATROOM_SIP_MESSAGE = 0x205C;
	public static final int WHAT_ON_CHATROOM_MEMBERS = 0x205D;
	public static final int WHAT_ON_CHATROOM_LIST = 0x205E;
	public static final int WHAT_ON_CHATROOM = 0x206A;
	public static final int WHAT_ON_CHATROOM_INVITE = 0x206B;
	public static final int WHAT_ON_MIKE_ANIM = 0x206C;
	public static final int WHAT_ON_CNETER_ANIM = 0x206D;
	public static final int WHAT_ON_VERIFY_CODE = 0x206E;
	public static final int WHAT_ON_CHATROOMING = 0x207A;
	public static final int WHAT_ON_CHATROOM_KICKMEMBER = 0x207B;
	public static final int WHAT_ON_SET_MEMBER_SPEAK = 0x207C;
	
	
	// IM
	public static final int WHAT_ON_SEND_MEDIAMSG_RES = 0x208A;
	public static final int WHAT_ON_NEW_MEDIAMSG = 0x208B;
	public static final int WHAT_ON_RECEIVE_SYSTEM_EVENTS = 0x208C;
	
	public static final int WHAT_ON_CALL_TRANSFERSTATESUCCEED = 0x208d;
	
	public static final int WHAT_ON_CALLVIDEO_RATIO_CHANGED = 0x2032;
	
	/**
	 * handler for update activity
	 */
	private Handler handler;

	/**
	 * set handler.
	 * 
	 * @param handler
	 *            activity handler
	 */
	public void setHandler(final Handler handler) {
		this.handler = handler;
	}

	/**
	 * get the device.
	 * 
	 * @return the device
	 */
	public Device getDevice() {
		return device;
	}

	long t = 0;
	private long currentTime=0;

	/**
	 * send object to activity by handler.
	 * 
	 * @param what
	 *            message id of handler
	 * @param obj
	 *            message of handler
	 */
	private void sendTarget(int what, Object obj) {
		t = System.currentTimeMillis();
		// for kinds of mobile phones
		while (handler == null && (System.currentTimeMillis() - t < 3200)) {
			Log4Util.d(DEMO_TAG , "[VoiceHelper] handler is null, activity maybe destory, wait...");
			try {
				Thread.sleep(80L);
			} catch (InterruptedException e) {
			}
		}

		if (handler == null) {
			Log4Util.d(DEMO_TAG , "[VoiceHelper] handler is null, need adapter it.");
			return;
		}

		Message msg = Message.obtain(handler);
		msg.what = what;
		msg.obj = obj;
		msg.sendToTarget();
	}

	/***********************************************************************************
	 *                                                                                 *
	 *            Following are DeviceListener Callback Methods                        *
	 *                                                                                 *
	 ************************************************************************************/

	/**
	 * Callback this method when register successful, developer can show
	 * notification to user.
	 */
	@Override
	public void onConnected() {
		// 成功注册上云通讯服务器
//		Log4Util.d(DEMO_TAG , "[VoiceHelper - onConnected]Connected on the cloud communication platform success..");
		if(mCallback != null) {
			mCallback.onRegistResult(WHAT_ON_CONNECT , "Connected on the cloud communication platform success");
			return ;
		} 
		
		this.context.sendBroadcast(new Intent(CCPIntentUtils.INTENT_CONNECT_CCP));
	}

	/**
	 * Callback this method when register failed, developer can show
	 * hint to user.
	 * 
	 * @param reason
	 *            register failed reason
	 */
	@Override
	public void onDisconnect(Reason reason) {
		// 注册失败,与云通讯服务器失去连接
		if(mCallback != null) {
			mCallback.onRegistResult(WHAT_ON_DISCONNECT , reason.toString());
			return;
		}
		
		if(reason == Reason.KICKEDOFF) {
			Log4Util.d(DEMO_TAG, "Login account in other places.");
//			this.context.sendBroadcast(new Intent(CCPIntentUtils.INTENT_KICKEDOFF));
			return ;
		} 

		if(reason == Reason.INVALIDPROXY) {
			Log4Util.d(DEMO_TAG, reason.getValue());
//			this.context.sendBroadcast(new Intent(CCPIntentUtils.INTENT_INVALIDPROXY));
			return;
		}
		if(currentTime==0||System.currentTimeMillis()-currentTime>5*60*1000)//超过5分钟提示 
		{
//			this.context.sendBroadcast(new Intent(CCPIntentUtils.INTENT_DISCONNECT_CCP));
			currentTime = System.currentTimeMillis();
		}
		Log4Util.d(DEMO_TAG , "[VoiceHelper - onDisconnect]Can't connect the cloud communication platform" +
				", please check whether the network connection,");
	}

	/**
	 * Callback this method when call arrived in remote.
	 * 
	 * @param callid
	 */
	@Override
	public void onCallAlerting(String callid) {
		sendTarget(WHAT_ON_CALL_ALERTING, callid);
	}

	/**
	 * Callback this method when remote answered.
	 * 
	 * @param callid
	 *           calling id
	 */
	@Override
	public void onCallAnswered(String callid) {
		sendTarget(WHAT_ON_CALL_ANSWERED, callid);
	}

	/**
	 * Callback this method when call arrived in soft-switch platform.
	 * 
	 * @param callid
	 *            calling id
	 */
	@Override
	public void onCallProceeding(String callid) {
		sendTarget(WHAT_ON_CALL_PROCEEDING, callid);
	}

	/**
	 * Callback this method when remote hangup call.
	 * 
	 * @param callid
	 *            calling id
	 */
	@Override
	public void onCallReleased(String callid) {
		sendTarget(WHAT_ON_CALL_RELEASED, callid);
	}

	/**
	 * Callback this method when make call failed.
	 * 
	 * @param callid
	 *            calling id
	 * @param destionation
	 *            destionation account
	 */
	@Override
	public void onCallTransfered(String callid, String destionation) {
		Bundle b = new Bundle();
		b.putString(Device.CALLID, callid);
		b.putString(Device.DESTIONATION, destionation);
		sendTarget(WHAT_ON_CALL_TRANSFERED, b);
	}

	/**
	 * Callback this method when make call failed.
	 * 
	 * @param callid
	 *            calling id
	 * @param reason
	 *            failed reason
	 */
	@Override
	public void onMakeCallFailed(String callid, Reason reason) {
		Bundle b = new Bundle();
		b.putString(Device.CALLID, callid);
		b.putSerializable(Device.REASON, reason);
		sendTarget(WHAT_ON_CALL_MAKECALL_FAILED, b);
	}


	/**
	 * Callback this method when dial-call success.
	 * 
	 * @param status
	 *            dial-call state
	 * @param self
	 *            Self phone number
	 * @param dest
	 *            Dest phone number
	 */
	@Override
	public void onCallback(int status, String self, String dest) {
		Bundle b = new Bundle();
		b.putInt(Device.CBSTATE, status);
		b.putString(Device.SELFPHONE, self);
		b.putString(Device.DESTPHONE, dest);
		sendTarget(WHAT_ON_CALL_BACKING, b);
	}

	/**
	 * Callback this method when localize pause current call.
	 * 
	 * @param callid
	 *            calling id
	 */
	@Override
	public void onCallPaused(String callid) {

	}

	/**
	 * Callback this method when Remote pause current call.
	 * 
	 * @param callid
	 *            calling id
	 */
	@Override
	public void onCallPausedByRemote(String callid) {

	}

	public void release() {
		this.context = null;
		this.device = null;
		this.handler = null;
		
		sInstance = null;
	}
	

	
	/**********************************************************************
	 *                     voice message                                  *
	 **********************************************************************/
	
	
	@Override
	public void onFinishedPlaying() {
		Log4Util.d(DEMO_TAG, "[onFinishedPlaying ] MediaPlayManager play is stop ..");
		Bundle b = new Bundle();
		sendTarget(WHAT_ON_PLAY_VOICE_FINSHING, b);
		
//		context.sendBroadcast(new Intent(CCPIntentUtils.INTENT_VOICE_PALY_COMPLETE));
	}


	@Override
	public void onRecordingAmplitude(double amplitude) {
		Bundle b = new Bundle();
		b.putDouble(Device.VOICE_AMPLITUDE, amplitude);
		sendTarget(WHAT_ON_AMPLITUDE, b);
		
	}

	@Override
	public void onRecordingTimeOut(long mills) {
		Bundle b = new Bundle();
		b.putLong("mills", mills);
		sendTarget(WHAT_ON_RECODE_TIMEOUT, b);
		
	}

	@Override
	public void onInterphoneState(CloopenReason reason, String confNo) {
		Log4Util.d(DEMO_TAG , "[onInterphoneState ] oninter phone state  , reason  " +reason.getReasonCode() + " , and confNo " + confNo);
		if(!reason.isError()){
			if(CCPApplication.interphoneIds == null || CCPApplication.interphoneIds.indexOf(confNo)<0){
				CCPApplication.interphoneIds.add(confNo);
				Intent intent = new Intent(CCPIntentUtils.INTENT_RECIVE_INTER_PHONE);
				context.sendBroadcast(intent);
			}
		} else {
			showToastMessage(reason);
		}
		Bundle b = new Bundle();
		b.putInt(Device.REASON, reason.getReasonCode());
		b.putString(Device.CONFNO, confNo);
		sendTarget(WHAT_ON_INTERPHONE, b);
	}

	@Override
	public void onControlMicState(CloopenReason reason, String speaker) {
		Log4Util.d(DEMO_TAG , "[onControlMicState ] control mic return  , reason " +reason.getReasonCode() + " , and speaker " + speaker );
		showToastMessage(reason);
		Bundle b = new Bundle();
		b.putInt(Device.REASON, reason.getReasonCode());
		b.putString(Device.SPEAKER, speaker);
		sendTarget(WHAT_ON_CONTROL_MIC, b);
	}

	@Override
	public void onReleaseMicState(CloopenReason reason) {
		Log4Util.d(DEMO_TAG , "[onReleaseMicState ] on release mic return reason  .. " +reason.getReasonCode());
		showToastMessage(reason);
		Bundle b = new Bundle();
		b.putInt(Device.REASON, reason.getReasonCode());
		sendTarget(WHAT_ON_RELEASE_MIC, b);
	}

	@Override
	public void onInterphoneMembers(CloopenReason reason, List<InterphoneMember> member) {
		Log4Util.d(DEMO_TAG , "[onInterphoneMembers ] on inter phone members that .. " + member);
		showToastMessage(reason);
		Bundle b = new Bundle();
		b.putInt(Device.REASON, reason.getReasonCode());
		b.putSerializable(Device.MEMBERS, (ArrayList<InterphoneMember>) member);
		sendTarget(WHAT_ON_INTERPHONE_MEMBERS, b);
	}

	@Override
	public void onReceiveInterphoneMsg(InterphoneMsg body) {
		Log4Util.d(DEMO_TAG , "[onReceiveInterphoneMsg ] Receive inter phone message  , id :" + body.interphoneId);
		if(body instanceof InterphoneOverMsg){
				CCPApplication.interphoneIds.remove(body.interphoneId);
				Intent intent = new Intent(CCPIntentUtils.INTENT_RECIVE_INTER_PHONE);
				context.sendBroadcast(intent);
		} else if (body instanceof InterphoneInviteMsg) {
			if(CCPApplication.interphoneIds.indexOf(body.interphoneId)<0){
				CCPApplication.interphoneIds.add(body.interphoneId);
			}
			Intent intent = new Intent(CCPIntentUtils.INTENT_RECIVE_INTER_PHONE);
			try {
				CCPNotificationManager.showNewInterPhoneNoti(context, body.interphoneId);
			} catch (IOException e) {
				e.printStackTrace();
			}
			context.sendBroadcast(intent);
		} 
		Bundle b = new Bundle();
		b.putSerializable(Device.INTERPHONEMSG, body);
		sendTarget(WHAT_ON_INTERPHONE_SIP_MESSAGE, b);
	}

	@Override
	public void onChatroomState(CloopenReason reason, String confNo) {
		Log4Util.d(DEMO_TAG , "[onChatRoomState ] reason " + reason + " , confNo "  +  confNo);
		showToastMessage(reason);
		Bundle b = new Bundle();
		b.putInt(Device.REASON, reason.getReasonCode());
		b.putString(Device.CONFNO, confNo);
		sendTarget(WHAT_ON_CHATROOM, b);
	}


	@Override
	public void onReceiveChatroomMsg(ChatroomMsg msg) {
		Log4Util.d(DEMO_TAG , "[onReceiveChatRoomMsg ] Receive Chat Room message  , id :" + msg.getRoomNo());
		Bundle b = new Bundle();
		b.putSerializable(Device.CHATROOM_MSG, msg);
		sendTarget(WHAT_ON_CHATROOM_SIP_MESSAGE, b);
	}

	@Override
	public void onChatroomMembers(CloopenReason reason, List<ChatroomMember> member) {
		Log4Util.d(DEMO_TAG , "[onChatRoomMembers ] on Chat Room  members that .. " + member);
		showToastMessage(reason);
		Bundle b = new Bundle();
		b.putSerializable(Device.CHATROOM_MEMBERS, (ArrayList<ChatroomMember>) member);
		sendTarget(WHAT_ON_CHATROOM_MEMBERS, b);
	}

	@Override
	public void onChatroomInviteMembers(CloopenReason reason, String confNo) {
		Log4Util.d(DEMO_TAG , "[onChatRoomInvite ] reason " + reason + " , confNo "  +  confNo);
		showToastMessage(reason);
		Bundle b = new Bundle();
		b.putInt(Device.REASON, reason.getReasonCode());
		b.putString(Device.CONFNO, confNo);
		sendTarget(WHAT_ON_CHATROOM_INVITE, b);
	}

	@Override
	public void onChatrooms(CloopenReason reason, List<Chatroom> chatRoomList) {
		Log4Util.d(DEMO_TAG , "[onChatrooms ] on Chat Room  chatrooms that .. " + chatRoomList);
		showToastMessage(reason);
		Bundle b = new Bundle();
		b.putSerializable(Device.CHATROOM_LIST, (ArrayList<Chatroom>) chatRoomList);
		sendTarget(WHAT_ON_CHATROOM_LIST, b);
		
	}

	@Override
	public void onSendInstanceMessage(CloopenReason reason, InstanceMsg data) {
		Log4Util.d(DEMO_TAG , "[onSendInstanceMessage ] on send Instance Message that reason .. " + reason);
		showToastMessage(reason);
		if(data == null) {
			return;
		}
		try {
			// If the current activity is not in the chat interface, 
			// so need here to update the database
			// If you are in a chat interface, then because here has to update the database,
			// when the chat interface to update the database will not update message state 
			// Because this message state isn't IMChatMessageDetail.STATE_IM_SENDING
			int msgType = -1;
			if(!reason.isError()) {
				msgType = IMChatMessageDetail.STATE_IM_SEND_SUCCESS;
			} else {
				if(reason.getReasonCode() != 230007) {
					msgType = IMChatMessageDetail.STATE_IM_SEND_FAILED;
				}
			}
			if(msgType != -1) {
				String messageId = null;;
				if(data instanceof IMTextMsg) {
					messageId = ((IMTextMsg)data).getMsgId();
				} else if (data instanceof IMAttachedMsg) {
					messageId = ((IMAttachedMsg)data).getMsgId();
				}
				CCPSqliteManager.getInstance().updateIMMessageSendStatusByMessageId(messageId, msgType);
			}
		} catch (Exception e) {
			// 
		}
		Bundle b = new Bundle();
		b.putInt(Device.REASON, reason.getReasonCode());
		b.putSerializable(Device.MEDIA_MESSAGE, data);
		sendTarget(WHAT_ON_SEND_MEDIAMSG_RES, b);
	}


	@Override
	public void onDownloadAttached(CloopenReason reason, String fileName) {
		Log4Util.d(DEMO_TAG , "[onDownloadAttachmentFiles ]  reason " + reason.getReasonCode() +  " , fileName= " + fileName);
		showToastMessage(reason);
		final IMChatMessageDetail rMediaInfo = (IMChatMessageDetail)CCPApplication.getInstance().getMediaData(fileName);
		try {
			if(!reason.isError()) {//success
				if(rMediaInfo != null ) {
					String msgid[] = {rMediaInfo.getMessageId()};
					CCPSqliteManager.getInstance().updateIMMessageDate(rMediaInfo.getMessageId(), CCPUtil.getDateCreate());
					getDevice().confirmIntanceMessage(msgid);
							
					Intent intent = new Intent(CCPIntentUtils.INTENT_IM_RECIVE);
					intent.putExtra(GroupBaseActivity.KEY_GROUP_ID, rMediaInfo.getSessionId());
					intent.putExtra(GroupBaseActivity.KEY_MESSAGE_ID, rMediaInfo.getMessageId());
					context.sendBroadcast(intent);
					
					CCPVibrateUtil.getInstace().doVibrate();
				} 
			
			} else if (reason.getReasonCode() == SdkErrorCode.SDK_AMR_CANCLE || reason.getReasonCode() == SdkErrorCode.SDK_FILE_NOTEXIST) {
				// delete this message in database.
				if(rMediaInfo == null) {
					return;
				}
				CCPSqliteManager.getInstance().deleteIMMessageByMessageId(rMediaInfo.getMessageId());
			} else {
				// do download again ...
				
			}
			CCPApplication.getInstance().removeMediaData(fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onReceiveInstanceMessage(InstanceMsg msg) {
		Log4Util.d(DEMO_TAG , "[onReceiveInstanceMessage ] "+msg.getClass().getName());
		try {
			
			boolean isNewMessageVibrate = true;
			if(msg != null) {
				if(msg instanceof IMAttachedMsg || msg instanceof IMTextMsg){
					if(msg instanceof IMAttachedMsg) {
						
						// if this message is attache , then push thread download.
						isNewMessageVibrate = false;
						IMAttachedMsg aMsg = (IMAttachedMsg)msg;
						if (aMsg.getSender() != null	&& aMsg.getSender().equals(CCPConfig.VoIP_ID)) {
							return;
						}
						
						String receiver = aMsg.getReceiver();
						String sender = aMsg.getSender();
						if(TextUtils.isEmpty(receiver)){
							return;
						}
						
						String contactId = "";
						
						if(CCPConfig.VoIP_ID.equals(receiver)) {
							// not group message 
							contactId = sender;
						} else {
							// group 
							contactId = receiver;
						}
						
						
						int index = aMsg.getFileUrl().indexOf("fileName=");
						String fileName = aMsg.getFileUrl().substring(index+9, aMsg.getFileUrl().length());
						String localPath = new File(CCPApplication.getInstance().getVoiceStore(), fileName).getAbsolutePath();
						
						String msgContent = aMsg.getFileUrl().substring(index+9, aMsg.getFileUrl().length());
						
						IMChatMessageDetail chatMessageDetail = null;
						if("amr".equals(aMsg.getExt())) {
							chatMessageDetail = IMChatMessageDetail.getGroupItemMessageReceived(aMsg.getMsgId(), IMChatMessageDetail.TYPE_MSG_VOICE 
									, contactId, sender);
						} else {
							chatMessageDetail = IMChatMessageDetail.getGroupItemMessageReceived(aMsg.getMsgId(), IMChatMessageDetail.TYPE_MSG_FILE ,
									contactId, sender) ;
							chatMessageDetail.setMessageContent(msgContent);
						}
						chatMessageDetail.setFileExt(aMsg.getExt());
						
						// file path in server
						// Voice file or multimedia attachments on the file server URL 
						// Save this address. You can download it again in the download failed 
						chatMessageDetail.setFileUrl(aMsg.getFileUrl()); 
						
						// local save path 
						// File in the local store path of SDCARD.
						chatMessageDetail.setFilePath(localPath);              
						chatMessageDetail.setUserData(aMsg.getUserData());
						chatMessageDetail.setDateCreated(aMsg.getDateCreated()); //file dateCreate in server ..
						
						CCPApplication.getInstance().putMediaData(localPath, chatMessageDetail);
						CCPSqliteManager.getInstance().insertIMMessage(chatMessageDetail);
						
						// download ..
						ArrayList<DownloadInfo> dLoadList = new ArrayList<DownloadInfo>();
						dLoadList.add(new DownloadInfo(aMsg.getFileUrl(), localPath , aMsg.isChunked()));
						getDevice().downloadAttached(dLoadList);
						
					} else if(msg instanceof IMTextMsg){
						IMTextMsg aMsg = (IMTextMsg)msg;
						String sender = aMsg.getSender();
						String message = aMsg.getMessage();
						String receiver = aMsg.getReceiver();
						
						if(TextUtils.isEmpty(sender) || TextUtils.isEmpty(message) ||TextUtils.isEmpty(receiver)) {
							return ;
						}
						
						if(CCPConfig.VoIP_ID.equals(sender)) {
							return;
						}
						
						String contactId = "";
						if(CCPConfig.VoIP_ID.equals(receiver)) {
							// not group message 
							contactId = sender;
						} else {
							// group 
							contactId = receiver;
						}
						
						IMChatMessageDetail chatMessageDetail = IMChatMessageDetail.getGroupItemMessageReceived(aMsg.getMsgId(),IMChatMessageDetail.TYPE_MSG_TEXT
								, contactId, sender);
						chatMessageDetail.setMessageContent(message);
						chatMessageDetail.setDateCreated(aMsg.getDateCreated());
						chatMessageDetail.setUserData(aMsg.getUserData());
						
						CCPSqliteManager.getInstance().insertIMMessage(chatMessageDetail);
						Intent intent = new Intent(CCPIntentUtils.INTENT_IM_RECIVE);
						intent.putExtra(GroupBaseActivity.KEY_GROUP_ID, contactId);
						context.sendBroadcast(intent);
						
					} 
					
					
				} else if (msg instanceof IMInviterMsg) {
					// Received the invitation to join the group
					IMInviterMsg imInviterMsg = (IMInviterMsg) msg;
					Log4Util.d(DEMO_TAG , "[VoiceHelper - onReceiveInstanceMessage ] Receive invitation to join the group ,that amdin " +
							imInviterMsg.getAdmin() + " , and group id :" + imInviterMsg.getGroupId());
					CCPSqliteManager.getInstance().insertNoticeMessage(msg, IMSystemMessage.SYSTEM_TYPE_INVITE_JOIN);
					Intent intent = new Intent(CCPIntentUtils.INTENT_RECEIVE_SYSTEM_MESSAGE);
					context.sendBroadcast(intent);
				} else if (msg instanceof IMJoinGroupMsg) {
					
					// The receipt of the application to join the group
					IMJoinGroupMsg imJoinMsg = (IMJoinGroupMsg) msg;
					Log4Util.d(DEMO_TAG , "[VoiceHelper - onReceiveInstanceMessage ] Receive join message that Joiner " +
							imJoinMsg.getProposer() + " , and group id :" + imJoinMsg.getGroupId());
					CCPSqliteManager.getInstance().insertNoticeMessage(msg, IMSystemMessage.SYSTEM_TYPE_APPLY_JOIN_UNVALIDATION);
					Intent intent = new Intent(CCPIntentUtils.INTENT_RECEIVE_SYSTEM_MESSAGE);
					context.sendBroadcast(intent);
					
					
					// BUG ..
				} else if (msg instanceof IMProposerMsg ) {
					
					// The receipt of the application to join the group
					IMProposerMsg imProposerMsg = (IMProposerMsg) msg;
					Log4Util.d(DEMO_TAG , "[VoiceHelper - onReceiveInstanceMessage ] Receive proposer message that Proposer " +
							imProposerMsg.getProposer() + " , and group id :" + imProposerMsg.getGroupId());
					CCPSqliteManager.getInstance().insertNoticeMessage(msg, IMSystemMessage.SYSTEM_TYPE_APPLY_JOIN);
					Intent intent = new Intent(CCPIntentUtils.INTENT_RECEIVE_SYSTEM_MESSAGE);
					context.sendBroadcast(intent);
				} else if (msg instanceof IMRemoveMemeberMsg) {
					// Remove group received system information
					IMRemoveMemeberMsg imrMemeberMsg = (IMRemoveMemeberMsg) msg;
					Log4Util.d(DEMO_TAG , "[VoiceHelper - onReceiveInstanceMessage ] Received system information that " +
							"remove from group  id " + imrMemeberMsg.getGroupId());
					CCPSqliteManager.getInstance().insertNoticeMessage(msg, IMSystemMessage.SYSTEM_TYPE_REMOVE);
					Intent intent = new Intent(CCPIntentUtils.INTENT_REMOVE_FROM_GROUP);
					intent.putExtra(GroupBaseActivity.KEY_GROUP_ID, imrMemeberMsg.getGroupId());
					context.sendBroadcast(intent);
				} else if (msg instanceof IMReplyJoinGroupMsg) {
					// Remove group received system information
					IMReplyJoinGroupMsg imAcceptRejectMsg = (IMReplyJoinGroupMsg) msg;
					Log4Util.d(DEMO_TAG , "[VoiceHelper - onReceiveInstanceMessage ] Received system information that " +
							"reject or accept from group  id " + imAcceptRejectMsg.getGroupId());
					CCPSqliteManager.getInstance().insertNoticeMessage(msg, IMSystemMessage.SYSTEM_TYPE_ACCEPT_OR_REJECT_JOIN);
					Intent intent = null;
					if("0".equals(imAcceptRejectMsg.getConfirm())){
						intent = new Intent(CCPIntentUtils.INTENT_JOIN_GROUP_SUCCESS);
						intent.putExtra(GroupBaseActivity.KEY_GROUP_ID, imAcceptRejectMsg.getGroupId());
					} else {
						intent = new Intent(CCPIntentUtils.INTENT_RECEIVE_SYSTEM_MESSAGE);
					}
					context.sendBroadcast(intent);
				} else if (msg instanceof IMDismissGroupMsg) {
					// The group manager dismiss this group..
					IMDismissGroupMsg imDismissGroupMsg = (IMDismissGroupMsg) msg;
					Log4Util.d(DEMO_TAG , "[VoiceHelper - onReceiveInstanceMessage ] Received system information that " +
							"group manager dismiss this group  id " + imDismissGroupMsg.getGroupId());
					CCPSqliteManager.getInstance().insertNoticeMessage(msg, IMSystemMessage.SYSTEM_TYPE_GROUP_DISMISS);
					Intent intent = null;
					intent = new Intent(CCPIntentUtils.INTENT_REMOVE_FROM_GROUP);
					intent.putExtra(GroupBaseActivity.KEY_GROUP_ID, imDismissGroupMsg.getGroupId());
					context.sendBroadcast(intent);
				} else if (msg instanceof IMQuitGroupMsg) {
					// The group manager dismiss this group..
					IMQuitGroupMsg imQuitGroupMsg = (IMQuitGroupMsg) msg;
					Log4Util.d(DEMO_TAG , "[VoiceHelper - onReceiveInstanceMessage ] Received system information that " +
							"Members quit from a group id " + imQuitGroupMsg.getGroupId());
					CCPSqliteManager.getInstance().insertNoticeMessage(msg, IMSystemMessage.SYSTEM_TYPE_GROUP_MEMBER_QUIT);
					Intent intent = null;
					intent = new Intent(CCPIntentUtils.INTENT_RECEIVE_SYSTEM_MESSAGE);
					intent.putExtra(GroupBaseActivity.KEY_GROUP_ID, imQuitGroupMsg.getGroupId());
					context.sendBroadcast(intent);
				} else if (msg instanceof IMInviterJoinGroupReplyMsg) {
					IMInviterJoinGroupReplyMsg replyMsg = (IMInviterJoinGroupReplyMsg) msg;
					CCPSqliteManager.getInstance().insertNoticeMessage(msg, IMSystemMessage.SYSTEM_TYPE_REPLY_GROUP_APPLY);
					Intent intent = null;
					intent = new Intent(CCPIntentUtils.INTENT_RECEIVE_SYSTEM_MESSAGE);
					intent.putExtra(GroupBaseActivity.KEY_GROUP_ID, replyMsg.getGroupId());
					context.sendBroadcast(intent);
				}
				
				if(isNewMessageVibrate) {
					CCPVibrateUtil.getInstace().doVibrate();
				}
			}		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onCallMediaUpdateRequest(String callid, int reason) {
		Log4Util.d(DEMO_TAG , "[onCallMediaUpdateRequest ]  callid="+ callid + ", reason="+reason);
		getDevice().answerCallTypeUpdate(callid, 1);
	}

	@Override
	public void onCallMediaUpdateResponse(String callid, int reason) {
		Log4Util.d(DEMO_TAG , "[onCallMediaUpdateResponse ]  callid="+ callid + ", reason="+reason);
	}

	@Override
	public void onCallVideoRatioChanged(String callid, String resolution) {
		/*Log4Util.d(DEMO_TAG , "[onCallVideoRatioChanged ]  callid="+ callid + ", resolution="+resolution);
		Bundle b = new Bundle();
		b.putString(Device.CALLID, callid);
		b.putString("resolution", resolution);
		sendTarget(WHAT_ON_CALLVIDEO_RATIO_CHANGED, b);*/
	}
	
	@Override
	public void onCallVideoRatioChanged(String callid, int width, int height) {
		Log4Util.d(DEMO_TAG , "[onCallVideoRatioChanged ]  callid="+ callid + ", width="+width + " , height=" + height);
		Bundle b = new Bundle();
		b.putString(Device.CALLID, callid);
		b.putInt("width", width);
		b.putInt("height", height);
		sendTarget(WHAT_ON_CALLVIDEO_RATIO_CHANGED, b);
	}

	@Override
	public void onCallMediaInitFailed(String callid, int reason) {
		Log4Util.d(DEMO_TAG , "[onCallMediaInitFailed ]  callid="+ callid + ", reason="+reason);
	}

	@Override
	public void onConfirmIntanceMessage(CloopenReason reason) {
		showToastMessage(reason);
	}

	@Override
	public void onChatroomDismiss(CloopenReason reason, String roomNo) {
		Intent intent = new Intent(CCPIntentUtils.INTENT_CHAT_ROOM_DISMISS);
		showToastMessage(reason);
		intent.putExtra("roomNo", roomNo);
		context.sendBroadcast(intent);
	}

	@Override
	public void onChatroomRemoveMember(CloopenReason reason, String member) {
		showToastMessage(reason);
		Bundle b = new Bundle();
		b.putInt(Device.REASON, reason.getReasonCode());
		b.putString("kick_member", member);
		sendTarget(WHAT_ON_CHATROOM_KICKMEMBER, b);
	}

	@Override
	public void onFirewallPolicyEnabled() {
		Intent intent = new Intent(CCPIntentUtils.INTENT_P2P_ENABLED);
		context.sendBroadcast(intent);
	}

	/**
	 * Callback this method when networks changed.
	 * 
	 * @param apn
	 *            mobile access point name
	 * @param ns
	 *            mobile network state
	 *            
	 * @version 3.5
	 */
	@Override
	public void onReceiveEvents(CCPEvents events/*, APN network, NetworkState ns*/) {
		Log4Util.d(DEMO_TAG, "Receive CCP events , " + events)  ;
		if(events == CCPEvents.SYSCallComing) {
			Bundle b = new Bundle();
			sendTarget(WHAT_ON_RECEIVE_SYSTEM_EVENTS, b);
		}
	}

	public void showToastMessage(CloopenReason reason) {
		if(reason != null && reason.isError()) {
			if(reason.getReasonCode() == 230007) {
				Toast.makeText(context, "语音发送被取消[" + reason.getReasonCode() + "]", Toast.LENGTH_SHORT).show();
				return;
			}
			
			if(reason.getReasonCode() == SdkErrorCode.SDK_FILE_NOTEXIST 
					|| reason.getReasonCode() == SdkErrorCode.SDK_AMR_CANCLE) {
				return;
			}
			Toast.makeText(context, reason.getMessage() + "[" + reason.getReasonCode() + "]", Toast.LENGTH_SHORT).show();
		}
	}
	
	protected void finalize () {
		
		if(device != null) {
			device.release();
			device = null;
			sInstance = null;
		}
	}
	
	/**
	 * 
	 * @param rcb
	 */
	public void setRegistCallback(RegistCallBack rcb) {
		this.mCallback = rcb;
	}
	
	/**
	 * 
	 * @ClassName: RegistCallBack.java
	 * @Description: TODO
	 * @author Jorstin Chan 
	 * @date 2013-12-12
	 * @version 3.6
	 */
	public interface RegistCallBack {
		/**WHAT_INIT_ERROR
		 * call back when regist over.
		 * @param reason {@link WHAT_INIT_ERROR} {@link WHAT_ON_CONNECT} {@link WHAT_ON_DISCONNECT} 
		 * @param msg regist failed message
		 * 
		 * @see CCPHelper#WHAT_ON_CONNECT
		 * @see CCPHelper#WHAT_INIT_ERROR
		 * @see CCPHelper#WHAT_ON_DISCONNECT
		 */
		void onRegistResult(int reason, String msg);
	}


	@Override
	public void onTransferStateSucceed(String callid, boolean result) {
		if(result)
		sendTarget(WHAT_ON_CALL_TRANSFERSTATESUCCEED, callid);
	}


	/* (non-Javadoc)
	 * @see com.hisun.phone.core.voice.listener.OnChatroomListener#onSetMemberSpeakOpt(com.hisun.phone.core.voice.model.CloopenReason, java.lang.String)
	 */
	@Override
	public void onSetMemberSpeakOpt(CloopenReason reason, String member) {
		showToastMessage(reason);
		Bundle b = new Bundle();
		b.putInt(Device.REASON, reason.getReasonCode());
		b.putString("kick_member", member);
		sendTarget(WHAT_ON_SET_MEMBER_SPEAK, b);
	}


	@Override
	public void onSwitchCallMediaTypeRequest(String callid, CallType callType) {
		
	}


	@Override
	public void onSwitchCallMediaTypeResponse(String callid, CallType callType) {
		
	}


	@Override
	public void onCallMediaInitFailed(String callid, CallType callType) {
		
	}


}
