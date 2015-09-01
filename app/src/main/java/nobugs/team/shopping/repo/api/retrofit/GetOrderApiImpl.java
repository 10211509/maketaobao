package nobugs.team.shopping.repo.api.retrofit;

import java.util.List;

import nobugs.team.shopping.mvp.model.Order;
import nobugs.team.shopping.repo.api.GetOrderApi;
import nobugs.team.shopping.repo.api.entity.OrderListResult;
import nobugs.team.shopping.repo.mapper.OrderListMapper;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by xiayong on 2015/8/26.
 */
public class GetOrderApiImpl extends BaseRetrofitHandler implements GetOrderApi {

    public GetOrderApiImpl(RetrofitAdapter adapter) {
        super(adapter);
        this.mapper = new OrderListMapper();
    }

    @Override
    public void getOrder(int orderId, final Callback callback) {
        getService().getOrder(orderId, new retrofit.Callback<OrderListResult>() {
            @Override
            public void success(OrderListResult orderListResult, Response response) {
                List<Order> orders = (List<Order>) mapper.map(orderListResult);
                if (orders != null && orders.size() > 0){
                    callback.onFinish(orders.get(0));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                callback.onError(getErrorType(error), error.getMessage());
            }
        });
    }
}
