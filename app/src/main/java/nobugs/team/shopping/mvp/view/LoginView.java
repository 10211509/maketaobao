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

package nobugs.team.shopping.mvp.view;

/**
 * 登录界面展示层
 */
public interface LoginView extends IView {
   /* public void showProgress();

    public void hideProgress();

    public void setUsernameError();

    public void setPasswordError();*/

    void setLoginError();//有网，登录失败

    void setNewWorkDown();//无网络

    void navigateToHome();//登录成功，跳转到下一个页面

//    void onCCPRegistResult(final int reason, final String msg);
}
