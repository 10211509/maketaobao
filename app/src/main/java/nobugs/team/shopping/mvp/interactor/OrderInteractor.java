package nobugs.team.shopping.mvp.interactor;


import java.util.List;

import nobugs.team.shopping.repo.db.entity.OrderPo;
import nobugs.team.shopping.repo.db.entity.UserPo;

/**
 * Created by xiayong on 2015/8/22.
 */
public interface OrderInteractor  {

    void getOrdersInProgress(UserPo userPo,int pageCount,int curPage,Callback callback);

    void getOrdersInFinished(UserPo userPo,int pageCount,int curPage,Callback callback);

    interface Callback {

        void onNetWorkError();

        void onFailure();

        void onOrderListSuccess(List<OrderPo> orderPoList);
    }
}
