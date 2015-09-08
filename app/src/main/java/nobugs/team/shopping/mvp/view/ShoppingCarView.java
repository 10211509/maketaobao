package nobugs.team.shopping.mvp.view;

import java.util.List;

import nobugs.team.shopping.mvp.model.Order;

/**
 * Created by xiayong on 2015/8/29.
 */
public interface ShoppingCarView extends IView{
    void refreshViewPager(List<Order> orders);

    void showCommitView(int amount,double totalPrice);

   /* void removeOrder(Order order);*/

//    void loadCar(List<Order> orders);

}
