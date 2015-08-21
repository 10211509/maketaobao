package nobugs.team.shopping.mvp.view;

import nobugs.team.shopping.db.entity.User;

/**
 * Created by xiayong on 2015/8/17.
 */
public interface VoipCallView extends IView {
    void hangup();

    void answer();

    void showCallInView(User user);

    void showCallOutView(User user);
}
