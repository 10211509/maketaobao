package nobugs.team.shopping.event;

import nobugs.team.shopping.mvp.model.User;

/**
 * Created by xiayong on 2015/8/25.
 */
public class UserLoginEvent implements Event {
    private User myInfo;
    public UserLoginEvent(User Info){
        this.myInfo = myInfo;
    }

    public User getMyInfo() {
        return myInfo;
    }

    public void setMyInfo(User myInfo) {
        this.myInfo = myInfo;
    }
}
