package nobugs.team.shopping.mvp.presenter;

import nobugs.team.shopping.mvp.interactor.OrderInteractor;
import nobugs.team.shopping.mvp.interactor.OrderInteractorImpl;
import nobugs.team.shopping.mvp.view.OrderListView;

/**
 * Created by xiayong on 2015/8/23.
 */
public class OrderInProgressPresenterImpl extends BasePresenter<OrderListView> implements OrderInProgressPresenter {

    private OrderInteractor mOrderInteractor;

    public OrderInProgressPresenterImpl(OrderListView orderListView){
        setView(orderListView);
        this.mOrderInteractor = new OrderInteractorImpl();
    }
    @Override
    public void showOrderInprogressList() {

    }

    @Override
    public void navigateToOrderDetailsActivity() {

    }

    @Override
    public void refreshOrderList() {

    }

    @Override
    public void loadMoreOrder() {

    }
}
