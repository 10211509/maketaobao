package nobugs.team.shopping.im.entity;

import com.google.gson.annotations.Expose;

/**
 * Created by wangyf on 2015/8/30 0030.
 */
public class IMDelOrder extends IMBase{
    @Expose
    private int orderId;

    public IMDelOrder() {
    }
    public IMDelOrder(String type, int orderId) {
        super(type);
        this.orderId = orderId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
