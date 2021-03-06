package nobugs.team.shopping.mvp.presenter;

import nobugs.team.shopping.mvp.model.Order;

/**
 * Created by xiayong on 2015/8/22.
 */
public interface MainOrderPresenter extends IPresenter {
    void showOrderInprogess();

    void showOrderFinished();

    void onOrderSelected(Order order);


}
