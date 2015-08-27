package nobugs.team.shopping.repo.api;

import java.util.List;

import nobugs.team.shopping.mvp.interactor.OrderInteractor;
import nobugs.team.shopping.mvp.model.Order;
import nobugs.team.shopping.mvp.model.User;

/**
 * Created by xiayong on 2015/8/26.
 */
public interface GetOrderListApi {
    List<Order> getOrderList(User loginer, int everyPage, int currentPage, boolean isOver);
    void getOrderList(User loginer, int everyPage, int currentPage, boolean isOver,Callback callback);
    interface Callback {

        void onFinish(List<Order> orders);

        void onError(int errType, String errMsg);
    }
}