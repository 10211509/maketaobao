package nobugs.team.shopping.repo.api.entity;

import com.google.gson.annotations.Expose;

import nobugs.team.shopping.repo.entity.ShopPo;

/**
 * Created by wangyf on 2015/9/2 0002.
 */
public class ShopAndSeller extends ShopPo {

    @Expose
    private String username;

    @Expose
    private String phone;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
