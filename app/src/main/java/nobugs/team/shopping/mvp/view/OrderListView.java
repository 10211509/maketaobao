package nobugs.team.shopping.mvp.view;

import android.widget.ImageView;

import java.util.List;

import nobugs.team.shopping.db.entity.Order;

/**
 * Created by xiayong on 2015/8/22.
 * OrderFinishedFragment and OrderInProgressFragment share the same view
 */
public interface OrderListView extends IView {
    void navigateToOrderDetailsAvtivity(Order order);

    void refreshOrderList(List<Order> orderList);

    void loadMoreOrders(List<Order> orderList);

    void showOrderList(List<Order> orderList);
}
