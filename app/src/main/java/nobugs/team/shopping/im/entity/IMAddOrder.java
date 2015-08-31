package nobugs.team.shopping.im.entity;

import com.google.gson.annotations.Expose;

import nobugs.team.shopping.repo.entity.OrderPo;

/**
 * Created by wangyf on 2015/8/30 0030.
 */
public class IMAddOrder extends IMBase{
    @Expose
    private OrderPo order;

    public IMAddOrder() {
    }

    public IMAddOrder(String type, OrderPo order) {
        super(type);
        this.order = order;
    }

    public OrderPo getOrder() {
        return order;
    }

    public void setOrder(OrderPo order) {
        this.order = order;
    }
}
