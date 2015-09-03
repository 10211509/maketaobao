package nobugs.team.shopping.event;

import com.yuntongxun.ecsdk.ECMessage;

/**
 * Created by wangyf on 2015/8/30 0030.
 */
public class RemoteShoppingCartSubmitEvent extends IMEvent {
    private int productTotal;
    private double priceTotal;

    public RemoteShoppingCartSubmitEvent(int productTotal, double priceTotal, ECMessage msg) {
        super(msg);
        this.productTotal = productTotal;
        this.priceTotal = priceTotal;
    }

    public int getProductTotal() {
        return productTotal;
    }

    public void setProductTotal(int productTotal) {
        this.productTotal = productTotal;
    }

    public double getPriceTotal() {
        return priceTotal;
    }

    public void setPriceTotal(double priceTotal) {
        this.priceTotal = priceTotal;
    }
}
