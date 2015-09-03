package nobugs.team.shopping.mvp.view;

import nobugs.team.shopping.mvp.model.User;

/**
 * Created by xiayong on 2015/8/22.
 */
public interface MainPageView extends IView {
    void showShop();
    void showOrderList();
    void initView(User user);

}
