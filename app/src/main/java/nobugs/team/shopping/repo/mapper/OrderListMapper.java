package nobugs.team.shopping.repo.mapper;

import java.util.ArrayList;
import java.util.List;

import nobugs.team.shopping.mvp.model.Order;
import nobugs.team.shopping.mvp.model.Product;
import nobugs.team.shopping.repo.api.model.OrderListResult;
import nobugs.team.shopping.repo.model.OrderPo;

/**
 * Created by xiayong on 2015/8/26.
 */
public class OrderListMapper implements Mapper<OrderListResult, List<Order>> {
    @Override
    public List<Order> map(OrderListResult orderListResult) {
        List<Order> orders = new ArrayList<>();
        for(OrderPo orderPo : orderListResult.getData()){
            orders.add(mapOrder(orderPo));
        }
        return orders;
    }

    private Order mapOrder(OrderPo orderPo) {
        Order order =  new Order();
        order.setOrderid(orderPo.getOrdernum());
        order.setPrice(orderPo.getPrice());
        order.setOrderState(orderPo.getState());
        order.setProduct_count(orderPo.getNumber());
        Product product = new Product();
        product.setName(orderPo.getProductname());
        order.setProduct(product);
        order.setPlace_time(orderPo.getPlacetime());
        return order;
    }
}
