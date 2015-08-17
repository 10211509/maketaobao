package nobugs.team.shopping.mvp.view;

import nobugs.team.shopping.db.entity.User;

/**
 * Created by xiayong on 2015/8/17.
 */
public interface CallOutView extends IView {
    void hangup();

    void showCalledSubscriber(User user);

    void showCallProgress();
}
