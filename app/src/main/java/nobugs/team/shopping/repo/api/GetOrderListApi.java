package nobugs.team.shopping.repo.api;

import java.util.List;

import nobugs.team.shopping.mvp.model.Order;

/**
 * Created by xiayong on 2015/8/26.
 */
public interface GetOrderListApi {
//    List<Order> getOrderList(int buyerId, int everyPage, int currentPage, boolean isOver);

    void getOrderListBuyer(int buyerId, int everyPage, int currentPage, boolean isOver,Callback callback);

    void getOrderListSeller(int sellerId, int everyPage, int currentPage, boolean isOver,Callback callback);

    interface Callback {

        void onFinish(List<Order> orders);

        void onError(int errType, String errMsg);
    }
}