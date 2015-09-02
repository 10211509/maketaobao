package nobugs.team.shopping.mvp.interactor;

import nobugs.team.shopping.mvp.model.Order;
import nobugs.team.shopping.repo.RepoCallback;
import nobugs.team.shopping.repo.Repository;

/**
 * Created by xiayong on 2015/8/29.
 */
public class ShoppingCarInteractorImpl implements ShoppingCarInteractor {

    @Override
    public void addProduct(final Order order, final Callback callback) {
        //String productId, int number, double price, long sellerId, String units, long buyerId
        Repository.getInstance().addOrder(order,new RepoCallback.Add<Order> (){

            @Override
            public void onAddDataSuccess(int id) {
                order.setOrderState(id);
                if(callback != null){
                    callback.onAddSuccess(order);
                }
            }

            @Override
            public void onError(int errType, String errMsg) {
                callback.onFailure();
            }
        });
    }

    @Override
    public void deleteProduct(final String orderid, final Callback callback) {
        Repository.getInstance().removeOrder(Integer.valueOf(orderid),new RepoCallback.Remove<Order>(){

            @Override
            public void onRemoveDataSuccess() {
                if(callback != null){
                    callback.onDeleteSuccess(orderid);
                }
            }

            @Override
            public void onError(int errType, String errMsg) {
                if(callback != null){
                    callback.onFailure();
                }
            }
        });
    }
}
