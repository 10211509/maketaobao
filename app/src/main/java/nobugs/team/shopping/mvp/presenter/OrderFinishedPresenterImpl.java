package nobugs.team.shopping.mvp.presenter;

import nobugs.team.shopping.db.entity.Order;
import nobugs.team.shopping.mvp.view.OrderListView;

/**
 * Created by xiayong on 2015/8/22.
 */
public class OrderFinishedPresenterImpl extends BasePresenter<OrderListView> implements OrderFinishedPresenter {


    public OrderFinishedPresenterImpl(OrderListView orderListView){
        setView(orderListView);
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
