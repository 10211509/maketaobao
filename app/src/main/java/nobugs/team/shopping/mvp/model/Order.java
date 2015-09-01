package nobugs.team.shopping.mvp.model;

/**
 * Created by xiayong on 2015/8/10.
 * 订单实体bean
 */
public class Order {
    //    private int id;//主键id
    private String orderid;//订单编号
    private Product product;//用户购买的商品
    private int product_count;//商品数量
    private double price;//总价格
    private Shop shop;//购买商品所在商店
    private User buyer;//买家
    private String place_time;
    private State orderState = State.payed;

    public Order() {
        //empty constructor
    }

    public Order(String orderid, Product product, int product_count, double price, Shop shop, User buyer, String place_time) {
        this.orderid = orderid;
        this.product = product;
        this.product_count = product_count;
        this.price = price;
        this.shop = shop;
        this.buyer = buyer;
        this.place_time = place_time;
    }

    //订单状态
    public enum State {
        placed,
        payed,
        collected,
        delivered,
        received,
        canceled
    }

    public State getOrderState() {
        return orderState;
    }

    public int getState() {
        if (orderState == null)
            return 0;
        switch (orderState) {
            case placed:
                return 0;
            case payed:
                return 1;
            case collected:
                return 2;
            case delivered:
                return 3;
            case received:
                return 4;
            case canceled:
                return 5;
            default:
                throw new IllegalStateException("no such order state!");
        }
    }

    public void setOrderState(State orderState) {
        this.orderState = orderState;
    }

    public void setOrderState(int orderState) {
        switch (orderState) {
            case 0:
                this.orderState = State.placed;
                break;
            case 1:
                this.orderState = State.payed;
                break;
            case 2:
                this.orderState = State.collected;
                break;
            case 3:
                this.orderState = State.delivered;
                break;
            case 4:
                this.orderState = State.received;
                break;
            case 5:
                this.orderState = State.canceled;
                break;
            default:
                throw new IllegalStateException("no such order state!");
        }
    }


    public String getPlace_time() {
        return place_time;
    }

    public void setPlace_time(String place_time) {
        this.place_time = place_time;
    }


    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getProduct_count() {
        return product_count;
    }

    public void setProduct_count(int product_count) {
        this.product_count = product_count;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public User getBuyer() {
        return buyer;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }
}
