package nobugs.team.shopping.mvp.interactor;

import java.util.List;

import nobugs.team.shopping.mvp.model.Order;
import nobugs.team.shopping.mvp.model.User;
import nobugs.team.shopping.repo.RepoCallback;
import nobugs.team.shopping.repo.Repository;

/**
 * Created by xiayong on 2015/8/22.
 */
public class OrderInteractorImpl implements OrderInteractor {



    private void getOrdersBuyer(int buyerId, int pageCount, int curPage, boolean isOver, final GetListCallback callback) {
        Repository.getInstance().getOrderListSeller(buyerId, pageCount, curPage, isOver, new RepoCallback.GetList<Order>() {
            @Override
            public void onGotDataListSuccess(List<Order> orders) {
                callback.onGetOrderListSuccess(orders);
            }

            @Override
            public void onError(int errType, String errMsg) {
                callback.onFailure();
            }
        });
    }

    private void getOrdersSeller(int sellerId, int pageCount, int curPage, boolean isOver, final GetListCallback callback) {
        Repository.getInstance().getOrderListBuyer(sellerId, pageCount, curPage, isOver, new RepoCallback.GetList<Order>() {
            @Override
            public void onGotDataListSuccess(List<Order> orders) {
                callback.onGetOrderListSuccess(orders);
            }

            @Override
            public void onError(int errType, String errMsg) {
                callback.onFailure();
            }
        });
    }


    @Override
    public void getOrdersInProgress(User user, int pageCount, int curPage, GetListCallback callback) {
        if (user.getType() != null) {
            switch (user.getType()) {
                case BUYER:
                    getOrdersSeller(user.getId().intValue(), pageCount, curPage, false, callback);
                    break;
                case SELLER:
                    getOrdersBuyer(user.getId().intValue(), pageCount, curPage, false, callback);
                    break;
            }
        }
    }

    @Override
    public void getOrdersInFinished(User user, int pageCount, int curPage, GetListCallback callback) {
        if (user.getType() != null) {
            switch (user.getType()) {
                case BUYER:
                    getOrdersSeller(user.getId().intValue(), pageCount, curPage, true, callback);
                    break;
                case SELLER:
                    getOrdersBuyer(user.getId().intValue(), pageCount, curPage, true, callback);
                    break;
            }
        }
    }

    @Override
    public void getOrder(String orderId, final GetCallback callback) {
        Repository.getInstance().getOrder(Integer.parseInt(orderId), new RepoCallback.Get<Order>() {
            @Override
            public void onGotDataSuccess(Order order) {
                callback.onGetOrderSuccess(order);
            }
            @Override
            public void onError(int errType, String errMsg) {
                callback.onFailure();
            }
        });
    }

    @Override
    public void addOrder(Order order, final AddCallback callback) {
        Repository.getInstance().addOrder(order, new RepoCallback.Add<Order>() {
            @Override
            public void onAddDataSuccess(int id) {
                callback.onAddOrderSuccess(id);
            }

            @Override
            public void onError(int errType, String errMsg) {
                callback.onFailure();
            }
        });
    }

    @Override
    public void updateOrderState(String orderId, Order.State state, final UpdateCallback callback) {
        Order order = new Order();
        order.setOrderid(orderId);
        order.setOrderState(state);

        Repository.getInstance().updateOrder(order, new RepoCallback.Update<Order>() {
            @Override
            public void onUpateDataSuccess() {
                callback.onOrderStateUpdateSuccess();
            }

            @Override
            public void onError(int errType, String errMsg) {
                callback.onFailure();
            }
        });
    }

    @Override
    public void removeOrder(String orderId, final DeleteCallback callback) {
        Repository.getInstance().removeOrder(Integer.parseInt(orderId), new RepoCallback.Remove<Order>() {
            @Override
            public void onRemoveDataSuccess() {
                callback.onDeleteSuccess();
            }

            @Override
            public void onError(int errType, String errMsg) {
                callback.onFailure();
            }
        });
    }
}
