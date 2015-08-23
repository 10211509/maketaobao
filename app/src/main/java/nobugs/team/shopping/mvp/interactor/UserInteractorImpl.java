package nobugs.team.shopping.mvp.interactor;

import nobugs.team.shopping.mvp.model.User;

/**
 * Created by xiayong on 2015/8/17.
 */
public class UserInteractorImpl implements UserInteractor {
    private User user;

    public void getUser(User user){
        this.user = user;
    }
}
