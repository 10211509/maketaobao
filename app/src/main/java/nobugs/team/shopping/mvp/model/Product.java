package nobugs.team.shopping.mvp.model;

/**
 * Created by xiayong on 2015/8/10.
 * 商品实体类
 */
public class Product {
    private int id;//主键id
    private String name;//名称
    private ProductType type;//商品类别

    public Product(){

    }
    public Product(int id, String name, ProductType type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

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

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }
}
