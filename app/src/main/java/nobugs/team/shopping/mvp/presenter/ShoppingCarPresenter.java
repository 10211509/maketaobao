package nobugs.team.shopping.mvp.presenter;

import nobugs.team.shopping.mvp.model.Order;

/**
 * Created by xiayong on 2015/8/29.
 */
public interface ShoppingCarPresenter extends IPresenter {
//    void commitShoppingCart(int index);
    void deleteProduct(int index);
    void addProduct(Order order);
    void onConfirmBtnClick();
}
