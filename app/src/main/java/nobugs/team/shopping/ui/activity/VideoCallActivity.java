package nobugs.team.shopping.ui.activity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.OnClick;
import nobugs.team.shopping.R;
import nobugs.team.shopping.app.base.BaseActivity;
import nobugs.team.shopping.repo.db.entity.UserPo;
import nobugs.team.shopping.mvp.presenter.VideoCallPresenter;
import nobugs.team.shopping.mvp.presenter.VideoCallPresenterImpl;
import nobugs.team.shopping.mvp.view.VoipCallView;

/**
 * make a call or receive a call.You can answer or hang up the phone here!
 */
public class VideoCallActivity extends BaseActivity<VideoCallPresenter> implements VoipCallView {


    @Bind(R.id.btn_hangup)
    Button btnHangup;//button to hang up the call
    @Bind(R.id.txt_calleename)
    TextView txtCalleename;
    @Bind(R.id.btn_accept)
    Button btnAccept;
    @Bind(R.id.tv_calling)
    TextView tvCalling;

    @Override
    protected VideoCallPresenter initPresenter() {
        return new VideoCallPresenterImpl(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_voip_call;
    }


    @OnClick({R.id.btn_hangup, R.id.btn_accept})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_hangup:
                getPresenter().onHangupBtnClick();
                break;
            case R.id.btn_accept:
                getPresenter().onAnswerBtnClick();
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
    public void showCallInView(UserPo userPo) {
        txtCalleename.setText(userPo.getName());
        btnAccept.setVisibility(View.VISIBLE);
        tvCalling.setText(getString(R.string.tv_waiting_accept));
    }

    @Override
    public void showCallOutView(UserPo userPo) {
        txtCalleename.setText(userPo.getName());
        btnAccept.setVisibility(View.GONE);
        tvCalling.setText(getString(R.string.tv_calling));
    }

    @Override
    public void contactFailed() {

    }
}
