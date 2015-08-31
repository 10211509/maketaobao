package nobugs.team.shopping.repo.entity;

import com.google.gson.annotations.Expose;

/**
 * Created by xiayong on 2015/8/30.
 */
public class ProductPo extends BasePo {
    @Expose
    private Integer id;

    @Expose
    private Integer typeid;

    @Expose
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTypeid() {
        return typeid;
    }

    public void setTypeid(Integer typeid) {
        this.typeid = typeid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
