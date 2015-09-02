package nobugs.team.shopping.repo.mapper;

import nobugs.team.shopping.mvp.model.Order;
import nobugs.team.shopping.mvp.model.Product;
import nobugs.team.shopping.mvp.model.ProductType;
import nobugs.team.shopping.repo.entity.OrderPo;

/**
 * Created by wangyf on 2015/8/30 0030.
 */
public class OrderMapper implements IModelMapper<Order, OrderPo> {

    @Override
    public OrderPo fromModel(Order order) {
        OrderPo po = new OrderPo();
        po.setId(Integer.valueOf(order.getOrderid()));
        po.setPrice(order.getPrice());
        po.setNumber(order.getProduct_count());
        po.setState(order.getState());
        po.setPlacetime(order.getPlace_time());

        if (order.getProduct() != null){
            po.setProductname(order.getProduct().getName());
            po.setProductid(order.getProduct().getId());

            if (order.getProduct().getType() != null){
                po.setType(order.getProduct().getType().getUnit());
            }
        }
        return po;
    }

    @Override
    public Order toModel(OrderPo orderPo) {
        Order order =  new Order();
        order.setOrderid(String.valueOf(orderPo.getId()));
        order.setPrice(orderPo.getPrice());
        order.setProduct_count(orderPo.getNumber());
        order.setOrderState(orderPo.getState());
        order.setPlace_time(orderPo.getPlacetime());

        Product product = new Product();
        product.setName(orderPo.getProductname());

        ProductType productType = new ProductType();
        productType.setUnit(orderPo.getType());

        product.setType(productType);

        order.setProduct(product);
        return order;
    }

}
