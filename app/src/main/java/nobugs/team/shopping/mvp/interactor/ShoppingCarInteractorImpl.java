package nobugs.team.shopping.mvp.interactor;

import nobugs.team.shopping.mvp.model.Order;
import nobugs.team.shopping.repo.Repository;

/**
 * Created by xiayong on 2015/8/29.
 */
public class ShoppingCarInteractorImpl implements ShoppingCarInteractor {

    @Override
    public void addProduct(Order order, Callback callback) {
        //String productId, int number, double price, long sellerId, String units, long buyerId
        if(callback!=null){
            callback.onAddSuccess(order);
        }
    }

    @Override
    public void deleteProduct(String produtId,Callback callback) {
        if (callback != null){
            callback.onDeleteSuccess(produtId);
        }
    }
}
