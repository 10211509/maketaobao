package nobugs.team.shopping.mvp.interactor;


import java.util.List;

import nobugs.team.shopping.mvp.model.Order;
import nobugs.team.shopping.mvp.model.User;

/**
 * Created by xiayong on 2015/8/22.
 */
public interface OrderInteractor {

    void getOrdersInProgress(User user, int pageCount, int curPage, GetListCallback callback);

    void getOrdersInFinished(User user, int pageCount, int curPage, GetListCallback callback);

    void getOrder(String orderId, GetCallback callback);

    void addOrder(Order order, AddCallback callback);

    void updateOrderState(String orderId, Order.State state, UpdateCallback callback);

    void removeOrder(String orderId, DeleteCallback callback);

    void updateOrdersState(String orderIds, Order.State state, UpdateCallback callback);


//    void getOrderByPosition(int position);

    interface Callback {
        void onNetWorkError();

        void onFailure();
    }

    interface GetListCallback extends Callback {
        void onGetOrderListSuccess(List<Order> orderList);
    }

    interface GetCallback extends Callback {
        void onGetOrderSuccess(Order order);
    }

    interface AddCallback extends Callback {
        void onAddOrderSuccess(Order order);
    }

    interface UpdateCallback extends Callback {
        void onOrderStateUpdateSuccess(Order.State newState);
    }

    interface DeleteCallback extends Callback {
        void onDeleteSuccess(String orderId);
    }
}
