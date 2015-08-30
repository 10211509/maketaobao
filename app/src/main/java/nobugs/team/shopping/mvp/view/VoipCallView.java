package nobugs.team.shopping.mvp.view;

import android.view.SurfaceView;
import android.view.ViewGroup;

import nobugs.team.shopping.mvp.model.User;

/**
 * Created by xiayong on 2015/8/17.
 */
public interface VoipCallView extends IView {
    void goBack();

    void showVideoView(User user);

    void showCallInView(User user);

    void showCallOutView(User user);

    void contactFailed();

    void showSilence(boolean silence, boolean clickable);

    void showChangeCamera(boolean isFront, boolean clickable);

    SurfaceView getRemoteCameraView();

    ViewGroup getLocalCameraViewHolder();

    void navigateToOrderListView();
}
