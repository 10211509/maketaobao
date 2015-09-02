package nobugs.team.shopping.repo.api;

import nobugs.team.shopping.repo.entity.OrderPo;

/**
 * Created by xiayong on 2015/8/26.
 */
public interface AddOrderApi {
    void addOrder(String sellerId,OrderPo orderPo, Callback callback);

    interface Callback {

        void onFinish(int orderId);

        void onError(int errType, String errMsg);
    }
}