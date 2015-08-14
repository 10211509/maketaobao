package nobugs.team.shopping.ui.activity;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.OnClick;
import nobugs.team.shopping.R;
import nobugs.team.shopping.app.base.BaseActivity;
import nobugs.team.shopping.mvp.presenter.LoginPresenter;
import nobugs.team.shopping.mvp.presenter.LoginPresenterImpl;
import nobugs.team.shopping.mvp.view.LoginView;

public class LoginActivity extends BaseActivity implements LoginView {

    @Bind(R.id.edit_name)
    EditText editName;

    @Bind(R.id.edit_password)
    EditText editPassword;

    @Bind(R.id.login_btn)
    Button loginBtn;

    @Bind(R.id.progress)
    ProgressBar progress;

    private LoginPresenter presenter;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_login);

        presenter = new LoginPresenterImpl(this);
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void updateData() {
    }


    @OnClick(R.id.login_btn)
    void onLoginClick() {
        presenter.validateCredentials(editName.getText().toString(), editPassword.getText().toString());
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
        username.setError("用户名错误");
    }

    @Override
    public void setPasswordError() {
        password.setError("密码错误");
    }
*/

    @Override
    public void setLoginError() {
        Toast.makeText(this, "登录错误！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setNewWorkDown() {
        Toast.makeText(this, "网路错误！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToHome() {
        // 跳转到主页面
        startActivity(new Intent(this, MainPageActivity.class));
    }
}
