package nobugs.team.shopping.event;

import nobugs.team.shopping.mvp.model.Shop;
import nobugs.team.shopping.mvp.model.User;

/**
 * Created by Administrator on 2015/8/26 0026.
 */
public class CallBeginEvent implements Event{
    private User me;
    private User peer;
    private Shop shop;
    private String callId;

    public CallBeginEvent(User me,  User peer, Shop shop, String callId) {
        this.me = me;
        this.peer = peer;
        this.shop = shop;
        this.callId = callId;
    }

    public User getMe() {
        return me;
    }

    public void setMe(User me) {
        this.me = me;
    }

    public User getPeer() {
        return peer;
    }

    public void setPeer(User peer) {
        this.peer = peer;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public String getCallId() {
        return callId;
    }

    public void setCallId(String callId) {
        this.callId = callId;
    }
}
