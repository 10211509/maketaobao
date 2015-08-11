package nobugs.team.shopping.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import nobugs.team.shopping.R;
import nobugs.team.shopping.app.base.BaseActivity;
import nobugs.team.shopping.mvp.presenter.LoginPresenterImpl;
import nobugs.team.shopping.mvp.presenter.LoginPresenter;
import nobugs.team.shopping.mvp.view.LoginView;

public class LoginActivity extends BaseActivity implements LoginView,View.OnClickListener{

    private ProgressBar progressBar;
    private EditText username;

    private EditText password;
    private LoginPresenter presenter;
    private Button loginbtn;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_login);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        username = (EditText) findViewById(R.id.edit_name);
        password = (EditText) findViewById(R.id.edit_password);
        loginbtn = (Button) findViewById(R.id.login_btn);
        presenter = new LoginPresenterImpl(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
        loginbtn.setOnClickListener(this);
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
        Toast.makeText(this,"登录错误！",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setNewWorkDown() {
        Toast.makeText(this,"网路错误！",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToHome() {
        // 跳转到主页面
        startActivity(new Intent(this,MainPageActivity.class));
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.login_btn:
                presenter.validateCredentials(username.getText().toString(), password.getText().toString());
                break;
            default:
                break;
        }
    }
}
