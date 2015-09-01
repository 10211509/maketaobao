package nobugs.team.shopping.repo.api;

import nobugs.team.shopping.mvp.model.Order;

/**
 * Created by xiayong on 2015/8/26.
 */
public interface GetOrderApi {
//    List<Order> getOrderList(int buyerId, int everyPage, int currentPage, boolean isOver);

    void getOrder(int orderId, Callback callback);

    interface Callback {

        void onFinish(Order order);

        void onError(int errType, String errMsg);
    }
}