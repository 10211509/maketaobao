package nobugs.team.shopping.event;

import nobugs.team.shopping.mvp.model.Shop;
import nobugs.team.shopping.mvp.model.User;

/**
 * Created by Administrator on 2015/8/23 0023.
 */
public class SelectShopEvent extends ShopEvent{

    public SelectShopEvent(Shop shop) {
        super(shop);
    }
}
