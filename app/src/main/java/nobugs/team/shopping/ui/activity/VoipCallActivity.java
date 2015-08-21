package nobugs.team.shopping.ui.activity;

import android.view.View;
import android.widget.Button;

import butterknife.Bind;
import butterknife.OnClick;
import nobugs.team.shopping.R;
import nobugs.team.shopping.app.base.BaseActivity;
import nobugs.team.shopping.db.entity.User;
import nobugs.team.shopping.mvp.presenter.VoipCallPresenter;
import nobugs.team.shopping.mvp.presenter.VoipCallPresenterImpl;
import nobugs.team.shopping.mvp.view.VoipCallView;

/**
 * make a call or receive a call.You can answer or hang up the phone here!
 */
public class VoipCallActivity extends BaseActivity<VoipCallPresenter> implements VoipCallView {


    @Bind(R.id.btn_hangup)
    Button btnHangup;//button to hang up the call

    @Override
    protected VoipCallPresenter initPresenter() {
        return new VoipCallPresenterImpl(this);
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
    public void answer() {

    }

    @Override
    public void showCallInView(User user) {

    }

    @Override
    public void showCallOutView(User user) {

    }

}
