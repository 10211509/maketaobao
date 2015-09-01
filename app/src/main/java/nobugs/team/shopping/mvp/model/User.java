package nobugs.team.shopping.mvp.model;

/**
 * Autor: wangyf on 2015/8/9 0009 00:56
 * Email: zgtjwyftc@gmail.com
 */
public class User {
    public enum Type {
        BUYER,//买家
        SELLER//卖家
    }

    private Long id;
    private String name;
    private String nickname;
    private String imgUrl;
    private String password;
    private String phone;////绑定的视频通话的号码 由SDK注册提供
    private Type type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public boolean isSeller(){
        return type != null && type == Type.SELLER;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
