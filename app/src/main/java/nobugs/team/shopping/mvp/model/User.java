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

    private int id;
    private String name;
    private String password;
    private String phone;////绑定的视频通话的号码 由SDK注册提供
    private Type type;

    public User(){

    }

    public User(int id, String name, String password, String phone, Type type) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.phone = phone;
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

}
