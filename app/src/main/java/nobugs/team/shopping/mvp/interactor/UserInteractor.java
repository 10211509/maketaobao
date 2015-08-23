package nobugs.team.shopping.mvp.interactor;

import nobugs.team.shopping.repo.db.entity.UserPo;

/**
 * Created by xiayong on 2015/8/17.
 */
public interface UserInteractor {
    interface Callback {
        void onGetUser(UserPo userPo);
    }
}
