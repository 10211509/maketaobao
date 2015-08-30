package nobugs.team.shopping.mvp.presenter;

/**
 * Created by xiayong on 2015/8/17.
 */
public interface VoipCallPresenter extends IPresenter {
    void onUIHangupCall();

    void onUIAnswerCall();

    void onUIChangeSilence();

    void onUIChangeCamera();

    void onUIExit();
}
