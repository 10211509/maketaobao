package nobugs.team.shopping.repo.api.retrofit;

import java.util.List;

import nobugs.team.shopping.mvp.model.Order;
import nobugs.team.shopping.mvp.model.User;
import nobugs.team.shopping.repo.api.GetOrderListApi;

/**
 * Created by xiayong on 2015/8/26.
 */
public class GetOrderListApiImpl extends BaseRetrofitHandler implements GetOrderListApi {


    public GetOrderListApiImpl(RetrofitAdapter adapter) {
        super(adapter);
    }

    @Override
    public List<Order> getOrderList(User loginer, int everyPage, int currentPage, boolean isOver) {
        return null;
    }

    @Override
    public void getOrderList(User loginer, int everyPage, int currentPage, boolean isOver, Callback callback) {

    }
}
