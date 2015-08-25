package nobugs.team.shopping.repo.model;

import com.google.gson.annotations.Expose;

/**
 * Created by Administrator on 2015/8/23 0023.
 */
public class UserPo {
    @Expose
    private Integer id;

    @Expose
    private String username;

    @Expose
    private String type;

    @Expose
    private String phone;

    @Expose
    private String password;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
