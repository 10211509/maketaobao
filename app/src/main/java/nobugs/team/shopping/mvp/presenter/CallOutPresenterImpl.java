package nobugs.team.shopping.mvp.presenter;

import android.text.TextUtils;

import de.greenrobot.event.EventBus;
import nobugs.team.shopping.db.entity.User;
import nobugs.team.shopping.mvp.view.CallOutView;
import nobugs.team.shopping.utils.CCPHelper;

/**
 * Created by xiayong on 2015/8/17.
 */
public class CallOutPresenterImpl extends BasePresenter<CallOutView> implements CallOutPresenter {

    //    private UserInteractor userInteractor;
    private User mUser;
    private String mCurrentCallId;

    public CallOutPresenterImpl(CallOutView callOutView) {
        setView(callOutView);
//        this.userInteractor = new UserInteractorImpl();
    }

    @Override
    public void onCreate() {
        EventBus.getDefault().registerSticky(this);
        makeCall();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    public void onEventMainThread(User user) {
        mUser = user;
    }

    @Override
    public void onDestroy() {

        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onHangupBtnClick() {
        //hang up the phone
        releaseCall();
        getView().hangup();
    }

    private void makeCall() {
      /*  if (CCPHelper.getInstance().checkDevice()) {
            //发起一个通话，通过mCurrentCallId来主动挂断通话
//            mCurrentCallId = CCPHelper.getInstance().getDevice().makeCall(Device.CallType.VIDEO, mUser.getVoipAccount());
            getView().showCalledSubscriber(mUser);
            getView().showCallProgress();
        }*/
    }

    private void releaseCall() {
        /*if (!TextUtils.isEmpty(mCurrentCallId) && CCPHelper.getInstance().checkDevice()) {
            CCPHelper.getInstance().getDevice().releaseCall(mCurrentCallId);
        }*/
    }
}
