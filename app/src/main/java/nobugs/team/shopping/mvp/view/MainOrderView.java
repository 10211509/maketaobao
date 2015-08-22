package nobugs.team.shopping.mvp.view;

import java.util.List;

import nobugs.team.shopping.db.entity.Order;

/**
 * Created by xiayong on 2015/8/22.
 */
public interface MainOrderView extends IView  {
    void navigateToOrderDetailsActivity(Order order);

    void showOrdersInProgress(List<Order> orderList);

    void showOrderFinished(List<Order> orderList);

}
