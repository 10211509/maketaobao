package nobugs.team.shopping.mvp.presenter;

/**
 * Created by xiayong on 2015/8/23.
 */
public interface OrderInProgressPresenter extends IPresenter {
    void showOrderInprogressList();
    void navigateToOrderDetailsActivity();
    void refreshOrderList();
    void loadMoreOrder();
}
