package nobugs.team.shopping.event;

import com.yuntongxun.ecsdk.ECMessage;

/**
 * Created by wangyf on 2015/8/30 0030.
 */
public class RemoteOrderDelEvent extends IMEvent {

    private int orderId;

    public RemoteOrderDelEvent(int orderId, ECMessage msg) {
        super(msg);
        this.orderId = orderId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
