package nobugs.team.shopping.event;

import nobugs.team.shopping.mvp.model.Shop;
import nobugs.team.shopping.mvp.model.User;

/**
 * Created by Administrator on 2015/8/26 0026.
 */
public class CallBeginEvent implements Event{
    private boolean isIncomingCall;
    private User peer;
    private Shop shop;
    private String callId;

    public CallBeginEvent(boolean isIncomingCall, User peer, Shop shop, String callId) {
        this.isIncomingCall = isIncomingCall;
        this.peer = peer;
        this.shop = shop;
        this.callId = callId;
    }

    public boolean isIncomingCall() {
        return isIncomingCall;
    }

    public void setIsCaller(boolean isIncomingCall) {
        this.isIncomingCall = isIncomingCall;
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
