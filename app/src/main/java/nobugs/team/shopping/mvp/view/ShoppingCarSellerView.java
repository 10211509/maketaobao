package nobugs.team.shopping.mvp.view;

import java.util.List;

import nobugs.team.shopping.mvp.model.Order;
import nobugs.team.shopping.mvp.model.ProductType;
import nobugs.team.shopping.mvp.model.Shop;

/**
 * Created by xiayong on 2015/8/30.
 */
public interface ShoppingCarSellerView extends IView {
    void initViewPager(Shop shop,List<String> productTypes);
    void refreshViewPagerWhenDataSetChange(List<Order> orders);
    void showCommitView(String name,int amount,double totalPrice);
    void showPagerLast();
//    void addItemToViewPager(Order order);
//    void deleteItemOfViewPager(String orderid);
}
