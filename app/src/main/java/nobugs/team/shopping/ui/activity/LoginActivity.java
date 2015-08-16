package nobugs.team.shopping.ui.activity;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hisun.phone.core.voice.util.Log4Util;

import butterknife.Bind;
import butterknife.OnClick;
import nobugs.team.shopping.R;
import nobugs.team.shopping.app.base.BaseActivity;
import nobugs.team.shopping.mvp.presenter.LoginPresenterImpl;
import nobugs.team.shopping.mvp.view.LoginView;
import nobugs.team.shopping.utils.CCPHelper;

public class LoginActivity extends BaseActivity implements LoginView,CCPHelper.RegistCallBack {

    @Bind(R.id.edit_name)
    EditText editName;

    @Bind(R.id.edit_password)
    EditText editPassword;

    @Bind(R.id.login_btn)
    Button loginBtn;

    @Bind(R.id.progress)
    ProgressBar progress;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void initData() {
        setPresenter(new LoginPresenterImpl(this));
    }

    @Override
    protected void updateData() {
    }

    @Override
    public LoginPresenterImpl getPresenter() {
        return (LoginPresenterImpl) super.getPresenter();
    }

    @OnClick(R.id.login_btn)
    void onLoginClick() {
        getPresenter().validateCredentials(editName.getText().toString(), editPassword.getText().toString());
    }

/*

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setUsernameError() {
        username.setError("鐢ㄦ埛鍚嶉敊璇�);
    }

    @Override
    public void setPasswordError() {
        password.setError("瀵嗙爜閿欒");
    }
*/

    @Override
    public void setLoginError() {
        Toast.makeText(this, "Sorry, Login Error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setNewWorkDown() {
        Toast.makeText(this, "Network Error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToHome() {

        startActivity(new Intent(this, MainPageActivity.class));

//        CCPHelper.getInstance(this).registerCCP(this);
    }

    @Override
    public void onRegistResult(final int reason,final String msg) {

        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                try {
//                    closeConnectionProgress();
                    if (reason == CCPHelper.WHAT_ON_CONNECT) {

                       /* Intent startService = new Intent(LoginActivity.this, T9Service.class);
                        startService(startService);
*/
//                        startAction();
                    } else if (reason == CCPHelper.WHAT_ON_DISCONNECT || reason == CCPHelper.WHAT_INIT_ERROR) {
                        // do nothing ...
//                        showInitErrToast(msg);
                    } else {
                        Log4Util.d(CCPHelper.DEMO_TAG, "Sorry , can't handle a message " + msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                CCPHelper.getInstance(LoginActivity.this).setRegistCallback(null);
            }
        });

    }
}
