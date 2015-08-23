package nobugs.team.shopping.mvp.presenter;

import nobugs.team.shopping.mvp.model.Order;

/**
 * Created by xiayong on 2015/8/23.
 */
public interface OrderListPresenter extends IPresenter {
    void showOrderInprogressList();
    void navigateToOrderDetailsActivity(Order order);
    void refreshOrderList();
    void loadMoreOrder();
}