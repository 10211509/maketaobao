package nobugs.team.shopping.im.model;

import nobugs.team.shopping.repo.model.UserPo;

/**
 * Created by wangyf on 2015/8/30 0030.
 */
public class IMSelectShop extends IMBase{
    private int shopId;
    private UserPo buyer;

    public IMSelectShop() {
    }

    public IMSelectShop(String type, int shopId, UserPo buyer) {
        super(type);
        this.shopId = shopId;
        this.buyer = buyer;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public UserPo getBuyer() {
        return buyer;
    }
    public void setBuyer(UserPo buyer) {
        this.buyer = buyer;
    }
}
