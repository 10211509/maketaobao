package nobugs.team.shopping.mvp.model;

/**
 * Created by xiayong on 2015/8/10.
 * 商品类别
 */
public class ProductType {
    private int id;//主键
    private String name;//类别名称，如水果（苹果、香蕉...）
    private String imgurl;//图片url
    private ProductType parent;//父类别

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

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public ProductType getParent() {
        return parent;
    }

    public void setParent(ProductType parent) {
        this.parent = parent;
    }
}
