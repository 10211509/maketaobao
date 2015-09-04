package nobugs.team.shopping.repo.api.retrofit;

import java.util.List;

import nobugs.team.shopping.mvp.model.Order;
import nobugs.team.shopping.repo.api.GetOrderListApi;
import nobugs.team.shopping.repo.api.entity.OrderListResult;
import nobugs.team.shopping.repo.mapper.OrderListMapper;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by xiayong on 2015/8/26.
 */
public class GetOrderListApiImpl extends BaseRetrofitHandler implements GetOrderListApi {

    public GetOrderListApiImpl(RetrofitAdapter adapter) {
        super(adapter);
        this.mapper = new OrderListMapper();
    }

    @Override
    public void getOrderListBuyer(int buyerId, int everyPage, int currentPage, boolean isOver, final Callback callback) {
        getService().getOrderListBuyer(buyerId, everyPage, currentPage, isOver ? 2 : 1, new retrofit.Callback<OrderListResult>() {
            @Override
            public void success(OrderListResult orderListResult, Response response) {
                callback.onFinish((List<Order>) mapper.map(orderListResult));
            }

            @Override
            public void failure(RetrofitError error) {
                callback.onError(getErrorType(error), error.getMessage());
            }
        });
    }

    @Override
    public void getOrderListSeller(int sellerId, int everyPage, int currentPage, boolean isOver, final Callback callback) {
        getService().getOrderListSeller(sellerId, everyPage, currentPage, isOver ? 2 : 1, new retrofit.Callback<OrderListResult>() {
            @Override
            public void success(OrderListResult orderListResult, Response response) {
                callback.onFinish((List<Order>) mapper.map(orderListResult));
            }

            @Override
            public void failure(RetrofitError error) {
                callback.onError(getErrorType(error), error.getMessage());
            }
        });
    }
}
