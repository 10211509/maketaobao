package nobugs.team.shopping.mvp.interactor;


import java.util.List;

import nobugs.team.shopping.mvp.model.Order;
import nobugs.team.shopping.mvp.model.User;
import nobugs.team.shopping.repo.db.entity.OrderPo;
import nobugs.team.shopping.repo.db.entity.UserPo;

/**
 * Created by xiayong on 2015/8/22.
 */
public interface OrderInteractor  {

    void getOrdersInProgress(User user,int pageCount,int curPage,Callback callback);

    void getOrdersInFinished(User user,int pageCount,int curPage,Callback callback);

    interface Callback {

        void onNetWorkError();

        void onFailure();

        void onOrderListSuccess(List<Order> orderPoList);
    }
}
