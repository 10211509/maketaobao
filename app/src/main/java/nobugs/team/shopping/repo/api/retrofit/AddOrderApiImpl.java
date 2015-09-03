package nobugs.team.shopping.repo.api.retrofit;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nobugs.team.shopping.mvp.model.Order;
import nobugs.team.shopping.repo.api.AddOrderApi;
import nobugs.team.shopping.repo.api.entity.OrderListResult;
import nobugs.team.shopping.repo.entity.OrderPo;
import nobugs.team.shopping.repo.mapper.OrderListMapper;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by xiayong on 2015/8/26.
 */
public class AddOrderApiImpl extends BaseRetrofitHandler implements AddOrderApi {

    public AddOrderApiImpl(RetrofitAdapter adapter) {
        super(adapter);
        mapper = new OrderListMapper();
    }

    @Override
    public void addOrder(OrderPo orderPo, final Callback callback) {
        Map<String, String> map = toMap(orderPo);
        getService().addOrder(map, new retrofit.Callback<OrderListResult>() {
            @Override
            public void success(OrderListResult orderListResult, Response response) {
                if (orderListResult != null) {
                    List<Order> orders = (List<Order>) mapper.map(orderListResult);
                    if (orders != null && orders.size() > 0) {
                        callback.onFinish(Integer.parseInt(orders.get(0).getOrderid()));
                    }
                } else {
                    callback.onError(0, "返回为空");
                }

            }

            @Override
            public void failure(RetrofitError error) {
                callback.onError(getErrorType(error), error.getMessage());
            }
        });
    }


    /**
     * 将javaBean转换成Map
     *
     * @param javaBean javaBean
     * @return Map对象
     */
    public static Map<String, String> toMap(Object javaBean) {
        Map<String, String> result = new HashMap<String, String>();
        Method[] methods = javaBean.getClass().getDeclaredMethods();

        for (Method method : methods) {
            try {
                if (method.getName().startsWith("get")) {
                    String field = method.getName();
                    field = field.substring(field.indexOf("get") + 3);
                    field = field.toLowerCase().charAt(0) + field.substring(1);

                    Object value = method.invoke(javaBean, (Object[]) null);
                    if (value != null){
                        result.put(field, value.toString());
                    }
                }
            } catch (Exception e) {
            }
        }

        return result;
    }

}
