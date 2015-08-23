package nobugs.team.shopping.mvp.view;

import java.util.List;

import nobugs.team.shopping.mvp.model.Order;

/**
 * Created by xiayong on 2015/8/22.
 */
public interface MainOrderView extends IView  {
    void navigateToOrderDetailsActivity(Order orderPo);

    void showOrdersInProgress(List<Order> orderPoList);

    void showOrderFinished(List<Order> orderPoList);

}
