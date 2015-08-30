package nobugs.team.shopping.im.model;

/**
 * Created by wangyf on 2015/8/30 0030.
 */
public class IMBase {
    public static final String TYPE_SELECT_SHOP = "selectShop";


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
