package nobugs.team.shopping.event;

import com.yuntongxun.ecsdk.ECMessage;

/**
 * Created by wangyf on 2015/8/30 0030.
 */
public class IMEvent implements Event {
    ECMessage msg;

    public IMEvent(ECMessage msg) {
        this.msg = msg;
    }

    public ECMessage getMsg() {
        return msg;
    }

    public void setMsg(ECMessage msg) {
        this.msg = msg;
    }
}
