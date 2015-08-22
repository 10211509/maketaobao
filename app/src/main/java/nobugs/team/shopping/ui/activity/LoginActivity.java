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

public class LoginActivity extends BaseActivity <LoginPresenter> implements LoginView {

    @Bind(R.id.edit_name)
    EditText editName;

    @Bind(R.id.edit_password)
    EditText editPassword;

    @Bind(R.id.login_btn)
    Button loginBtn;

    @Bind(R.id.progress)
    ProgressBar progress;

    @Override
    protected LoginPresenter initPresenter() {
        return new LoginPresenterImpl(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login;
    }

    @OnClick(R.id.login_btn)
    void onLoginClick() {
        getPresenter().validateCredentials(editName.getText().toString(), editPassword.getText().toString());
    }


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

        startActivity(new Intent(this, MainShopActivity.class));

    }

}
