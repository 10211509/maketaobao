package nobugs.team.shopping.repo.api.mock;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import nobugs.team.shopping.app.base.MyApplication;
import nobugs.team.shopping.mvp.model.Order;
import nobugs.team.shopping.mvp.model.Shop;
import nobugs.team.shopping.mvp.model.User;
import nobugs.team.shopping.repo.api.GetOrderListApi;
import nobugs.team.shopping.repo.api.model.OrderListResult;
import nobugs.team.shopping.repo.api.model.ShopListResult;
import nobugs.team.shopping.repo.mapper.Mapper;
import nobugs.team.shopping.repo.mapper.OrderListMapper;
import nobugs.team.shopping.utils.CommonTools;

/**
 * Created by xiayong on 2015/8/26.
 */
public class GetOrderListApiMock implements GetOrderListApi {
    private static final String JSON_TEST_FILEPATH = "orderlist.json";
    private List<Order> mOrders;

    private final Mapper mapper;

    public GetOrderListApiMock() {
        mapper = new OrderListMapper();
        loadJsonFromAsset();
    }
    private void loadJsonFromAsset() {
        String jsonTest = null;
        try {
            jsonTest = CommonTools.loadJSONFromAsset(MyApplication.getInstance().getApplicationContext(), JSON_TEST_FILEPATH);
            Gson gson = new Gson();
            mOrders = (List<Order>) mapper.map(gson.fromJson(jsonTest, OrderListResult.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public List<Order> getOrderList(User loginer, int everyPage, int currentPage, boolean isOver) {
        //synchronize
        return mOrders;
    }

    @Override
    public void getOrderList(User loginer, int everyPage, int currentPage, boolean isOver, Callback callback) {
        if (callback != null){
            callback.onFinish(mOrders);
        }
    }

}
