package nobugs.team.shopping.mvp.interactor;


import java.util.List;

import nobugs.team.shopping.db.entity.Order;
import nobugs.team.shopping.db.entity.User;

/**
 * Created by xiayong on 2015/8/22.
 */
public interface OrderInteractor  {

    void getOrdersInProgress(User user,int pageCount,int curPage,Callback callback);

    void getOrdersInFinished(User user,int pageCount,int curPage,Callback callback);

    interface Callback {

        void onNetWorkError();

        void onFailure();

        void onOrderListSuccess(List<Order> orderList);
    }
}
