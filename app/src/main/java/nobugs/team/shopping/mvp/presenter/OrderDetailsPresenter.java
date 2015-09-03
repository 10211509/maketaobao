package nobugs.team.shopping.mvp.presenter;

/**
 * Created by xiayong on 2015/8/23.
 */
public interface OrderDetailsPresenter extends IPresenter {
    void updateToDelivered();
    void updateToPayed();
    void updateToCollected();
    void updateToAccept();
    void cancelOrder();
}
