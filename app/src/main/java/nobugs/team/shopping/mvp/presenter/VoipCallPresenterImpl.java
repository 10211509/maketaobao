package nobugs.team.shopping.mvp.presenter;

import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;

import com.yuntongxun.ecsdk.ECVoIPCallManager;

import org.w3c.dom.Text;

import de.greenrobot.event.EventBus;
import nobugs.team.shopping.db.entity.User;
import nobugs.team.shopping.mvp.view.VoipCallView;
import nobugs.team.shopping.utils.CCPHelper;
import nobugs.team.shopping.utils.VoIPCallHelper;

/**
 * Created by xiayong on 2015/8/17.
 */
public class VoipCallPresenterImpl extends BasePresenter<VoipCallView> implements VoipCallPresenter {

    public static final String EXTRA_OUTGOING_CALL = "con.yuntongxun.ecdemo.VoIP_OUTGOING_CALL";
    //    private UserInteractor userInteractor;
    private User mUser;
    private String mCurrentCallId;
    private boolean isConnect = false;
    private boolean isIncomingCall;

    public VoipCallPresenterImpl(VoipCallView callOutView) {
        setView(callOutView);
    }

    @Override
    public void onCreate() {
        EventBus.getDefault().registerSticky(this);
//        makeCall();
    }


    public void onEventMainThread(User user) {
        mUser = user;

        isIncomingCall = !(getContext().getIntent().getBooleanExtra(EXTRA_OUTGOING_CALL, false));
        if (isIncomingCall){
            //action to launch a call
            getView().showCallInView(mUser);//show view
            makeCall();

        }else{
            //action to receive a call
            getView().showCallOutView(mUser);
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
        //TODO navigate to VideoActivity

    }

    private void makeCall() {
        mCurrentCallId = VoIPCallHelper.makeCall(ECVoIPCallManager.CallType.VIDEO,mUser.getPhone());
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
}
