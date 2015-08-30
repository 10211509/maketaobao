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


import de.greenrobot.event.EventBus;
import nobugs.team.shopping.app.base.MyApplication;
import nobugs.team.shopping.event.UserLoginEvent;
import nobugs.team.shopping.mvp.interactor.LoginInteractor;
import nobugs.team.shopping.mvp.interactor.LoginInteractorImpl;
import nobugs.team.shopping.mvp.model.User;
import nobugs.team.shopping.mvp.view.LoginView;
import nobugs.team.shopping.utils.CCPHelper;

public class LoginPresenterImpl extends BasePresenter<LoginView> implements LoginPresenter, LoginInteractor.Callback {

    private LoginInteractor loginInteractor;

    public LoginPresenterImpl(LoginView loginView) {
        super(loginView);

        this.loginInteractor = new LoginInteractorImpl();
    }

    @Override
    public void validateCredentials(String username, String password) {
        //请求服务器
        loginInteractor.login(username, password, this);
    }

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
    public void onSuccess(User user) {
        CCPHelper.getInstance(MyApplication.getInstance()).init(user.getPhone());

        EventBus.getDefault().postSticky(new UserLoginEvent(user));

        getView().navigateToHome();
    }

}
