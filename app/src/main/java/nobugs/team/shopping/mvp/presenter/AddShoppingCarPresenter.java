package nobugs.team.shopping.mvp.presenter;

import nobugs.team.shopping.mvp.model.Order;

/**
 * Created by xiayong on 2015/8/30.
 */
public interface AddShoppingCarPresenter extends IPresenter{
    void addOrder(Order order);
    void deleteOrder(int index);
}
