package nobugs.team.shopping.im.entity;

/**
 * Created by wangyf on 2015/8/30 0030.
 */
public class IMBase {
    public static final String TYPE_SELECT_SHOP = "selectShop";
    public static final String TYPE_ADD_ORDER = "refreshViewPager";
    public static final String TYPE_DEL_ORDER = "delOrder";
    public static final String TYPE_SHOPPING_CART_COMMIT = "shoppingCartCommit";

    private String type;

    public IMBase() {

    }
    public IMBase(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
