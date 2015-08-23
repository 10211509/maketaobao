package nobugs.team.shopping.mvp.model;

/**
 * Autor: wangyf on 2015/8/9 0009 00:56
 * Email: zgtjwyftc@gmail.com
 */
public class User {
    private int id;
    private String name;
    private String password;
    private String phone;////绑定的视频通话的号码 由SDK注册提供
    private Type type;

    public enum Type{
        buyer,//买家
        seller//卖家
    }

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

    public boolean isSeller(){
        return type == Type.seller;
    }

}
