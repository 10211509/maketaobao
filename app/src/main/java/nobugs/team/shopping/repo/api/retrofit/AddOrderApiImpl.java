package nobugs.team.shopping.repo.api.retrofit;

import java.util.List;

import nobugs.team.shopping.mvp.model.Order;
import nobugs.team.shopping.repo.api.AddOrderApi;
import nobugs.team.shopping.repo.api.entity.OrderListResult;
import nobugs.team.shopping.repo.entity.OrderPo;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by xiayong on 2015/8/26.
 */
public class AddOrderApiImpl extends BaseRetrofitHandler implements AddOrderApi {

    public AddOrderApiImpl(RetrofitAdapter adapter) {
        super(adapter);
    }

    @Override
    public void addOrder(OrderPo orderPo, final Callback callback) {
        getService().addOrder(orderPo, new retrofit.Callback<OrderListResult>() {
            @Override
            public void success(OrderListResult orderListResult, Response response) {
                List<Order> orders = (List<Order>) mapper.map(orderListResult);
                if (orders != null && orders.size() > 0) {
                    callback.onFinish(Integer.parseInt(orders.get(0).getOrderid()));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                callback.onError(getErrorType(error), error.getMessage());
            }
        });
    }
}
