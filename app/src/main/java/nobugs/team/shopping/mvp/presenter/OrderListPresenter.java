package nobugs.team.shopping.mvp.presenter;

import nobugs.team.shopping.mvp.model.Order;

/**
 * Created by xiayong on 2015/8/23.
 */
public interface OrderListPresenter extends IPresenter {
    void showOrderInprogressList();
    void showOrderFinishList();
    void navigateToOrderDetailsActivity(Order order);
    void refreshOrderList(boolean finished);
    void loadMoreOrder(boolean finished);
}
