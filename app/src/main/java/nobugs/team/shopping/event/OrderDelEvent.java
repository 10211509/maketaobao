package nobugs.team.shopping.event;

import com.yuntongxun.ecsdk.ECMessage;

import nobugs.team.shopping.mvp.model.Order;

/**
 * Created by wangyf on 2015/8/30 0030.
 */
public class OrderDelEvent extends IMEvent {
    private Order order;

    public OrderDelEvent(Order order, ECMessage msg) {
        super(msg);
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
