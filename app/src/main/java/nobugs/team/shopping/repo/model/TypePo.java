package nobugs.team.shopping.repo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2015/8/23 0023.
 */
public class TypePo extends BasePo{
    @Expose
//    @SerializedName("id")
    private Integer id;

    @Expose
    private Integer parentId;

    @Expose
    private String typename;

    @Expose
    private String imageurl;

    @Expose
    @SerializedName("type")
    private String unit;

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
