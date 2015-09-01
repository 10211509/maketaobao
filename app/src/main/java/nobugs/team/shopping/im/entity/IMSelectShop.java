package nobugs.team.shopping.im.entity;

import com.google.gson.annotations.Expose;

import nobugs.team.shopping.repo.entity.UserPo;

/**
 * Created by wangyf on 2015/8/30 0030.
 */
public class IMSelectShop extends IMBase{
    @Expose
    private int shopId;

    @Expose
    private UserPo buyer;

    public IMSelectShop() {
    }

    public IMSelectShop(int shopId, UserPo buyer) {
        super(TYPE_SELECT_SHOP);
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
