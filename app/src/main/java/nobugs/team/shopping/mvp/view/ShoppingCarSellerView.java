package nobugs.team.shopping.mvp.view;

import nobugs.team.shopping.mvp.model.Order;
import nobugs.team.shopping.mvp.model.Shop;

/**
 * Created by xiayong on 2015/8/30.
 */
public interface ShoppingCarSellerView extends IView {
    void initViewPager(Shop shop);
    void addItemToViewPager(Order order);
    void deleteItemOfViewPager(String orderid);
}
