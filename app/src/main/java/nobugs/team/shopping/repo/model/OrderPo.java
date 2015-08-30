package nobugs.team.shopping.repo.model;

import com.google.gson.annotations.Expose;

/**
 * Created by xiayong on 2015/8/26.
 */
public class OrderPo extends BasePo {
    @Expose
    private Integer id;

    @Expose
    private Integer saleid;

    @Expose
    private Integer productid;

    @Expose
    private Double price;

    @Expose
    private String ordernum;

    @Expose
    private Integer number;

    @Expose
    private Integer state;

    @Expose
    private String type;

    @Expose
    private String productname;

    @Expose
    private String placetime;

    @Expose
    private String ispay;

    @Expose
    private String iscoll;

    @Expose
    private String isgoods;

    @Expose
    private String isesc;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPlacetime() {
        return placetime;
    }

    public void setPlacetime(String placetime) {
        this.placetime = placetime;
    }

    public String getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(String ordernum) {
        this.ordernum = ordernum;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public Integer getSaleid() {
        return saleid;
    }

    public void setSaleid(Integer saleid) {
        this.saleid = saleid;
    }

    public Integer getProductid() {
        return productid;
    }

    public void setProductid(Integer productid) {
        this.productid = productid;
    }

    public String getIspay() {
        return ispay;
    }

    public void setIspay(String ispay) {
        this.ispay = ispay;
    }

    public String getIscoll() {
        return iscoll;
    }

    public void setIscoll(String iscoll) {
        this.iscoll = iscoll;
    }

    public String getIsgoods() {
        return isgoods;
    }

    public void setIsgoods(String isgoods) {
        this.isgoods = isgoods;
    }

    public String getIsesc() {
        return isesc;
    }

    public void setIsesc(String isesc) {
        this.isesc = isesc;
    }
}
