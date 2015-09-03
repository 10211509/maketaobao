package nobugs.team.shopping.ui.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.OnClick;
import nobugs.team.shopping.R;
import nobugs.team.shopping.app.base.BaseActivity;
import nobugs.team.shopping.mvp.model.User;
import nobugs.team.shopping.mvp.presenter.VoipCallPresenter;
import nobugs.team.shopping.mvp.presenter.VoipCallPresenterImpl;
import nobugs.team.shopping.mvp.view.VoipCallView;
import nobugs.team.shopping.ui.fragment.ShoppingCarBuyerFragment;
import nobugs.team.shopping.ui.fragment.ShoppingCarSellerFragment;

/**
 * make a call or receive a call.You can showVideoView or hang up the phone here!
 */
public class VoipCallActivity extends BaseActivity<VoipCallPresenter> implements VoipCallView,ShoppingCarBuyerFragment.FragmentActionListener {
    @Bind(R.id.root_voip_call)
    RelativeLayout rootVoipCall;

    @Bind(R.id.root_voip_video)
    RelativeLayout rootVoipVideo;

    @Bind(R.id.btn_hangup)
    Button btnHangup;//button to hang up the call

    @Bind(R.id.txt_calleename)
    TextView txtCalleename;

    @Bind(R.id.imageView)
    ImageView imageView;

    @Bind(R.id.btn_accept)
    Button btnAccept;

    @Bind(R.id.tv_calling)
    TextView tvCalling;

    @Bind(R.id.sv_video_remote)
    SurfaceView svVideoRemote;

    @Bind(R.id.rl_video_local)
    RelativeLayout rlVideoLocal;

    @Bind(R.id.btn_silence)
    Button btnSilence;

    @Bind(R.id.btn_exit)
    Button btnExit;

    @Bind(R.id.btn_camera_switch)
    ImageView btnCameraSwitch;

    @Bind(R.id.fl_frag_content)
    FrameLayout flFragContent;

    @Override
    protected VoipCallPresenter initPresenter() {
        return new VoipCallPresenterImpl(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_voip_call;
    }

    @Override
    protected void initView() {
        svVideoRemote.getHolder().setFixedSize(240, 320);
    }

    @Override
    public void onBackPressed() {
        onExitClick();
    }

    @OnClick(R.id.btn_hangup)
    public void onHangupClick() {
        getPresenter().onUIHangupCall();
    }
    @OnClick(R.id.btn_accept)
    public void onAcceptClick() {
        getPresenter().onUIAnswerCall();
    }
    @OnClick(R.id.btn_silence)
    public void onChangeSilenceClick() {
        getPresenter().onUIChangeSilence();
    }
    @OnClick(R.id.btn_camera_switch)
    public void onCameraSwitchClick() {
        getPresenter().onUIChangeCamera();
    }
    @OnClick(R.id.btn_exit)
    public void onExitClick() {
//        svVideoLocal.removeAllViews();
        getPresenter().onUIExit();
    }

    @Override
    public void goBack() {
        finish();
    }

    @Override
    public void showSellerVideoView(User user, int shopid) {
        rootVoipCall.setVisibility(View.GONE);
        rootVoipVideo.setVisibility(View.VISIBLE);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_frag_content, new ShoppingCarSellerFragment());
        fragmentTransaction.commit();
    }

    @Override
    public void showBuyerVideoView(User user) {
        rootVoipCall.setVisibility(View.GONE);
        rootVoipVideo.setVisibility(View.VISIBLE);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_frag_content, new ShoppingCarBuyerFragment());
        fragmentTransaction.commit();
    }

   /* @Override
    public void showVideoView(User user) {
        rootVoipCall.setVisibility(View.GONE);
        rootVoipVideo.setVisibility(View.VISIBLE);
        //add fragment to bottom
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = user.isSeller()? new AddShoppingCarFragment() : new ShoppingCarFragment();
        fragmentTransaction.replace(R.id.fl_frag_content, fragment);
        fragmentTransaction.commit();
    }*/

    @Override
    public void showCallInView(User user) {
        txtCalleename.setText(user.getNickname());
        btnAccept.setVisibility(View.VISIBLE);
        tvCalling.setText(getString(R.string.tv_waiting_accept));
    }

    @Override
    public void showCallOutView(User user) {
        txtCalleename.setText(user.getNickname());
        btnAccept.setVisibility(View.GONE);
        tvCalling.setText(getString(R.string.tv_calling));
    }

    @Override
    public void contactFailed() {

    }

    @Override
    public void showSilence(boolean silence, boolean clickable) {

    }

    @Override
    public void showChangeCamera(boolean isFront, boolean clickable) {
        btnSilence.setEnabled(clickable);
    }

    @Override
    public SurfaceView getRemoteCameraView() {
        return svVideoRemote;
    }

    @Override
    public ViewGroup getLocalCameraView() {
        return rlVideoLocal;
    }

    @Override
    public void navigateToOrderListView() {

    }

    @Override
    public void onFragmentChange(View view) {
        if(view.getId() == R.id.btn_commit_shopping_cart){
            btnHangup.performClick();
        }
    }
}
