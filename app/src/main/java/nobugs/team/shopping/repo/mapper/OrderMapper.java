package nobugs.team.shopping.repo.mapper;

import nobugs.team.shopping.mvp.model.Order;
import nobugs.team.shopping.mvp.model.Product;
import nobugs.team.shopping.repo.entity.OrderPo;

/**
 * Created by wangyf on 2015/8/30 0030.
 */
public class OrderMapper implements IModelMapper<Order, OrderPo> {

    @Override
    public OrderPo fromModel(Order order) {
//        OrderPo po = new OrderPo();
//        po.setId(order.getOrderid());
//        order.setOrderid(orderPo.getOrdernum());
//        order.setPrice(orderPo.getPrice());
//        order.setOrderState(orderPo.getState());
//        order.setProduct_count(orderPo.getNumber());
//        Product product = new Product();
//        product.setName(orderPo.getProductname());
//        order.setProduct(product);
//        order.setPlace_time(orderPo.getPlacetime());
        return null;
    }

    @Override
    public Order toModel(OrderPo orderPo) {
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
