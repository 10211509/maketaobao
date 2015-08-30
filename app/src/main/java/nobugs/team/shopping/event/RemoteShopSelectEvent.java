package nobugs.team.shopping.event;

import nobugs.team.shopping.mvp.model.Shop;
import nobugs.team.shopping.mvp.model.User;

/**
 * Created by Administrator on 2015/8/23 0023.
 */
public class RemoteShopSelectEvent extends ShopSelectEvent {

    private User buyer;

    public RemoteShopSelectEvent(Shop shop, User buyer) {
        super(shop);
        this.buyer = buyer;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }
}
