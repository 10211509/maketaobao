package nobugs.team.shopping.mvp.interactor;

import nobugs.team.shopping.mvp.model.Order;

/**
 * Created by xiayong on 2015/8/29.
 */
public interface ShoppingCarInteractor {
    void addProduct(Order order,Callback callback);

    void deleteProduct(String orderid,Callback callback);

    //    void commitAllProduct(long sellerId,long buyerId );
    interface Callback {
        void onDeleteSuccess(String id);

        void onAddSuccess(Order order);

        void onNetWorkError();

        void onFailure();
    }
}
