package nobugs.team.shopping.event;

import nobugs.team.shopping.mvp.model.Shop;
import nobugs.team.shopping.mvp.model.User;

/**
 * Created by Administrator on 2015/8/23 0023.
 */
public class ShopEvent extends Event{
    private Shop shop;

    public ShopEvent(Shop shop) {
        this.shop = shop;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}
