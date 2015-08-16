/*
 *
 *  * Copyright (C) 2014 Antonio Leiva Gordillo.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package nobugs.team.shopping.mvp.presenter;


import nobugs.team.shopping.mvp.interactor.LoginInteractor;
import nobugs.team.shopping.mvp.interactor.LoginInteractorImpl;
import nobugs.team.shopping.mvp.view.LoginView;

public class LoginPresenterImpl extends BasePresenter<LoginView> implements LoginPresenter, LoginInteractor.Callback {

    private LoginInteractor loginInteractor;

    public LoginPresenterImpl(LoginView loginView) {
        setView(loginView);
        this.loginInteractor = new LoginInteractorImpl();
    }

    @Override
    public void validateCredentials(String username, String password) {
        //请求服务器
        loginInteractor.login(username, password, this);
    }

   /* @Override public void on() {
        loginView.setUsernameError();
        loginView.hideProgress();
    }

    @Override public void onPasswordError() {
        loginView.setPasswordError();
        loginView.hideProgress();
    }*/

    @Override
    public void onNetWorkError() {
        //提示网络不好
        getView().setNewWorkDown();
    }

    @Override
    public void onFailure() {
        getView().setLoginError();
    }

    @Override
    public void onSuccess() {
        getView().navigateToHome();
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void onDestroy() {
    }
}
