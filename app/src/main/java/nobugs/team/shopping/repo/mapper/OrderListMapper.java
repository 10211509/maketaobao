package nobugs.team.shopping.repo.mapper;

import java.util.ArrayList;
import java.util.List;

import nobugs.team.shopping.mvp.model.Order;
import nobugs.team.shopping.repo.api.entity.OrderListResult;

/**
 * Created by xiayong on 2015/8/26.
 */
public class OrderListMapper implements IResultMapper<OrderListResult, List<Order>> {
    OrderMapper mapper = new OrderMapper();

    @Override
    public List<Order> map(OrderListResult orderListResult) {
        List<Order> orders = new ArrayList<>();
        if (orderListResult.getData() != null) {
            for (int i = 0; i < orderListResult.getData().size(); i++) {
                if (orderListResult.getData().get(i) != null)
                    orders.add(mapper.toModel(orderListResult.getData().get(i)));
            }
        }
        return orders;
    }
}
