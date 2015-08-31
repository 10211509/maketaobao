package nobugs.team.shopping.repo.mapper;

import java.util.ArrayList;
import java.util.List;

import nobugs.team.shopping.mvp.model.Order;
import nobugs.team.shopping.repo.api.entity.OrderListResult;
import nobugs.team.shopping.repo.entity.OrderPo;

/**
 * Created by xiayong on 2015/8/26.
 */
public class OrderListMapper implements IResultMapper<OrderListResult, List<Order>> {
    OrderMapper mapper = new OrderMapper();

    @Override
    public List<Order> map(OrderListResult orderListResult) {
        List<Order> orders = new ArrayList<>();
        for(OrderPo orderPo : orderListResult.getData()){
            orders.add(mapper.toModel(orderPo));
        }
        return orders;
    }
}
