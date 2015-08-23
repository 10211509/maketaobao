package nobugs.team.shopping.mvp.view;

import nobugs.team.shopping.repo.db.entity.UserPo;

/**
 * Created by xiayong on 2015/8/17.
 */
public interface VoipCallView extends IView {
    void hangup();

    void answer();

    void showCallInView(UserPo userPo);

    void showCallOutView(UserPo userPo);

    void contactFailed();
}
