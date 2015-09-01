package nobugs.team.shopping.repo.api;

import nobugs.team.shopping.repo.entity.OrderPo;

/**
 * Created by xiayong on 2015/8/26.
 */
public interface UpdateOrderApi {
//    List<Order> getOrderList(int buyerId, int everyPage, int currentPage, boolean isOver);

    void updateOrder(OrderPo orderPo, Callback callback);

    interface Callback {

        void onFinish();

        void onError(int errType, String errMsg);
    }
}