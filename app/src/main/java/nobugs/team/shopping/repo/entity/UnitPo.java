package nobugs.team.shopping.repo.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2015/8/23 0023.
 */
public class UnitPo extends BasePo{
    @Expose
//    @SerializedName("id")
    private String unitName;

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
}
