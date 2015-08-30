package nobugs.team.shopping.event;

import nobugs.team.shopping.mvp.model.Order;

/**
 * Created by xiayong on 2015/8/25.
 */
public class OrderSelectEvent implements Event {
    private Order order;
    public OrderSelectEvent(Order order){
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
