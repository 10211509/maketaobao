package nobugs.team.shopping.event;

import nobugs.team.shopping.mvp.model.Shop;

/**
 * Created by Administrator on 2015/8/23 0023.
 */
public class ShopSelectEvent implements Event {

    private Shop shop;

    public ShopSelectEvent(Shop shop) {
        this.shop = shop;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

}
