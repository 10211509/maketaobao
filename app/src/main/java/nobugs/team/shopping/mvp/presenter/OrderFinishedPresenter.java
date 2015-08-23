package nobugs.team.shopping.mvp.presenter;

/**
 * Created by xiayong on 2015/8/22.
 */
public interface OrderFinishedPresenter extends IPresenter {
    void showOrderInprogressList();
    void navigateToOrderDetailsActivity();
    void refreshOrderList();
    void loadMoreOrder();
}
