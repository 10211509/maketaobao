package nobugs.team.shopping.mvp.view;

import nobugs.team.shopping.mvp.model.Order;

/**
 * Created by xiayong on 2015/8/23.
 */
public interface OrderDetailsView extends IView {
    void showBuyerView(Order order);
    void showSellerView(Order order);
}
