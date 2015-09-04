package nobugs.team.shopping.repo.mapper;

import android.text.TextUtils;

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
        if (!TextUtils.isEmpty(order.getOrderid())) {
            po.setId(Integer.valueOf(order.getOrderid()));
        }
        po.setPrice(order.getPrice());
        po.setNumber(order.getProduct_count());
        po.setState(order.getState());
        po.setPlacetime(order.getPlace_time());
        po.setOrdernum(order.getOrderSn());

        if (order.getBuyer() != null && order.getBuyer().getId() != null) {
            po.setBuyid(order.getBuyer().getId().intValue());
        }
        if (order.getSeller() != null && order.getSeller().getId() != null) {
            po.setSaleid(order.getSeller().getId().intValue());
        }
        po.setIspay("0");
        po.setIscoll("0");
        po.setIsgoods("0");
        po.setIsesc("0");

        if (order.getProduct() != null) {
            po.setProductname(order.getProduct().getName());
            po.setProductid(order.getProduct().getId());

            if (order.getProduct().getType() != null) {
                po.setType(order.getProduct().getType().getUnit());
            }
        }
        return po;
    }

    @Override
    public Order toModel(OrderPo orderPo) {
        Order order = new Order();
        if (orderPo.getId() != null) {
            order.setOrderid(String.valueOf(orderPo.getId()));
        }
        if (orderPo.getPrice() != null){
            order.setPrice(orderPo.getPrice());
        }
        if (orderPo.getNumber() != null){
            order.setProduct_count(orderPo.getNumber());
        }
        if (orderPo.getState() != null){
            order.setOrderState(orderPo.getState());
        }

        order.setOrderSn(orderPo.getOrdernum());
        order.setPlace_time(orderPo.getPlacetime());

        //TODO 买家卖家信息

        Product product = new Product();
        if (orderPo.getProductid() != null){
            product.setId(orderPo.getProductid());
        }
        product.setName(orderPo.getProductname());

        ProductType productType = new ProductType();
        productType.setUnit(orderPo.getType());

        product.setType(productType);

        order.setProduct(product);
        return order;
    }

}
