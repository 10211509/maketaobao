package nobugs.team.shopping.mvp.model;

import java.util.List;

/**
 * Created by xiayong on 2015/8/10.
 * 商品类
 */
public class ProductType {
    private int id;//主键
    private int parentId;//父类id
    private String name;//类别名称，如水果（苹果、香蕉...）
    private String imgUrl;//图片url
    private List<ProductType> subTypes;//子类别
    private List<Shop> shops;//分类下的商铺

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public List<ProductType> getSubTypes() {
        return subTypes;
    }

    public void setSubTypes(List<ProductType> subTypes) {
        this.subTypes = subTypes;
    }

    public List<Shop> getShops() {
        return shops;
    }

    public void setShops(List<Shop> shops) {
        this.shops = shops;
    }


}