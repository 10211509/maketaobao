package nobugs.team.shopping.mvp.presenter;

import java.util.List;

import nobugs.team.shopping.mvp.interactor.OrderInteractor;
import nobugs.team.shopping.mvp.interactor.OrderInteractorImpl;
import nobugs.team.shopping.mvp.model.Order;
import nobugs.team.shopping.mvp.model.User;
import nobugs.team.shopping.mvp.view.OrderListView;

/**
 * Created by xiayong on 2015/8/23.
 */
public class OrderInProgressPresenterImpl extends BasePresenter<OrderListView> implements OrderInProgressPresenter,OrderInteractor.Callback {

    private OrderInteractor mOrderInteractor;

    public OrderInProgressPresenterImpl(OrderListView orderListView){
        setView(orderListView);
        this.mOrderInteractor = new OrderInteractorImpl();
    }
    @Override
    public void showOrderInprogressList() {
        User user = new User();
        mOrderInteractor.getOrdersInProgress(user,5,1,this);
    }

    @Override
    public void navigateToOrderDetailsActivity(Order order) {
        getView().navigateToOrderDetailsAvtivity();
    }

    @Override
    public void refreshOrderList() {

    }

    @Override
    public void loadMoreOrder() {

    }

    @Override
    public void onNetWorkError() {

    }

    @Override
    public void onFailure() {

    }

    @Override
    public void onOrderListSuccess(List<Order> orderPoList) {
        getView().showOrderList(orderPoList);
    }

    @Override
    public void onStateUpdateSuccess(Order.State newState) {

    }
}
