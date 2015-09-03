package nobugs.team.shopping.im.entity;

import com.google.gson.annotations.Expose;

/**
 * Created by wangyf on 2015/8/30 0030.
 */
public class IMShoppingCartCommit extends IMBase {
    @Expose
    private int productTotal;

    @Expose
    private double priceTotal;

    public IMShoppingCartCommit() {
    }

    public IMShoppingCartCommit(int productTotal, double priceTotal) {
        super(IMBase.TYPE_SHOPPING_CART_COMMIT);
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
