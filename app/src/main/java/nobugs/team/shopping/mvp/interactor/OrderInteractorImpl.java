package nobugs.team.shopping.mvp.interactor;

import java.util.ArrayList;
import java.util.List;

import nobugs.team.shopping.mvp.model.Order;
import nobugs.team.shopping.mvp.model.User;
import nobugs.team.shopping.repo.RepoCallback;
import nobugs.team.shopping.repo.Repository;

/**
 * Created by xiayong on 2015/8/22.
 */
public class OrderInteractorImpl implements OrderInteractor {

    private void filterIsOver(List<Order> orders, boolean isOver){
        List<Order> orderList = new ArrayList<Order>();
        for (Order order : orders) {
            if (isOver && order.isCompleted()) {

                orderList.add(order);
            } else if (!isOver && !order.isCompleted()) {
                orderList.add(order);
            }
        }
        orders = orderList;
    }

    private void getOrdersBuyer(int buyerId, int pageCount, int curPage, final  boolean isOver, final GetListCallback callback) {
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

    private void getOrdersSeller(int sellerId, int pageCount, int curPage, final boolean isOver, final GetListCallback callback) {
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
//        RequestFactory.createPostRequest()
//        OkVolleyUtils.addRequest(new GsonGetRequest<Object>());

        order.setOrderid(null);
        if (order.getProduct() != null) {
            order.getProduct().setName(null);
        }
        final Order retOrder = order;
        Repository.getInstance().addOrder(order, new RepoCallback.Add<Order>() {
            @Override
            public void onAddDataSuccess(int id) {
                retOrder.setOrderid(String.valueOf(id));
                callback.onAddOrderSuccess(retOrder);
            }

            @Override
            public void onError(int errType, String errMsg) {
                callback.onFailure();
            }
        });
    }

    @Override
    public void updateOrderState(String orderId, final Order.State state, final UpdateCallback callback) {
        Order order = new Order();
        order.setOrderid(orderId);
        order.setOrderState(state);

        Repository.getInstance().updateOrder(order, new RepoCallback.Update<Order>() {
            @Override
            public void onUpdateDataSuccess() {
                callback.onOrderStateUpdateSuccess(state);
            }

            @Override
            public void onError(int errType, String errMsg) {
                callback.onFailure();
            }
        });
    }

    @Override
    public void removeOrder(final String orderId, final DeleteCallback callback) {
        Repository.getInstance().removeOrder(Integer.parseInt(orderId), new RepoCallback.Remove<Order>() {
            @Override
            public void onRemoveDataSuccess() {
                callback.onDeleteSuccess(orderId);
            }

            @Override
            public void onError(int errType, String errMsg) {
                callback.onFailure();
            }
        });
    }
}
