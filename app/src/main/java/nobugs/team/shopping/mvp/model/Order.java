package nobugs.team.shopping.mvp.model;

/**
 * Created by xiayong on 2015/8/10.
 * 订单实体bean
 */
public class Order {
    private int id;//主键id
    private int orderid;//订单编号
    private Product product;//用户购买的商品
    private int product_count;//商品数量
    private double price;//总价格
    private Shop shop;//购买商品所在商店
    private User buyer;//买家


    //订单状态
    public enum State{

    }

}
