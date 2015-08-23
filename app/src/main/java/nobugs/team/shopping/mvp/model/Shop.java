package nobugs.team.shopping.mvp.model;

import java.util.List;

/**
 * Created by xiayong on 2015/8/10.
 * 店家实体类
 */
public class Shop {
    private int id;//店家id
    private int ownerId;//店主id
    private String name;//店铺名称
    private String introduction;//简介
    private User owner;//店主
    private List<Product> products;//商店卖的商品,一家商店可以卖多件商品


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }
}
