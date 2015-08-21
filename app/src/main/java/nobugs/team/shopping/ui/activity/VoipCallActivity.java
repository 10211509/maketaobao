package nobugs.team.shopping.ui.activity;

import android.view.View;
import android.widget.Button;

import butterknife.Bind;
import butterknife.OnClick;
import nobugs.team.shopping.R;
import nobugs.team.shopping.app.base.BaseActivity;
import nobugs.team.shopping.db.entity.User;
import nobugs.team.shopping.mvp.presenter.CallOutPresenter;
import nobugs.team.shopping.mvp.presenter.CallOutPresenterImpl;
import nobugs.team.shopping.mvp.view.CallOutView;

/**
 * make a call or receive a call.You can answer or hang up the phone here!
 */
public class VoipCallActivity extends BaseActivity<CallOutPresenter> implements CallOutView {


    @Bind(R.id.btn_hangup)
    Button btnHangup;//button to hang up the call

    @Override
    protected CallOutPresenter initPresenter() {
        return new CallOutPresenterImpl(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_voip_call;
    }


    @OnClick(R.id.btn_hangup)
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_hangup:
//                releaseCall();
                getPresenter().onHangupBtnClick();
                break;
        }
    }


    @Override
    public void hangup() {
        onBackPressed();
    }

    @Override
    public void showCalledSubscriber(User user) {


    }

    @Override
    public void showCallProgress() {

    }
}
