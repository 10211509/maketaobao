package nobugs.team.shopping.mvp.presenter;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import nobugs.team.shopping.R;
import nobugs.team.shopping.event.OrderSelectEvent;
import nobugs.team.shopping.mvp.interactor.OrderInteractor;
import nobugs.team.shopping.mvp.interactor.OrderInteractorImpl;
import nobugs.team.shopping.mvp.model.Order;
import nobugs.team.shopping.mvp.model.User;
import nobugs.team.shopping.mvp.view.OrderListView;
import nobugs.team.shopping.repo.Repository;

/**
 * Created by xiayong on 2015/8/23.
 */
public class OrderListPresenterImpl extends BasePresenter<OrderListView> implements OrderListPresenter,OrderInteractor.Callback ,OrderInteractor.GetListCallback {

    private OrderInteractor mOrderInteractor;
    private List<Order> orders;
    public OrderListPresenterImpl(OrderListView orderListView){
        super(orderListView);
        this.mOrderInteractor = new OrderInteractorImpl();
        orders = new ArrayList<>();
    }

    @Override
    public void showOrderInprogressList() {
        User loginer = Repository.getInstance().getLoginUser();
        mOrderInteractor.getOrdersInProgress(loginer, 5, 1, this);
    }

    @Override
    public void showOrderFinishList() {
        User loginer = Repository.getInstance().getLoginUser();
        mOrderInteractor.getOrdersInFinished(loginer, 5, 1, this);
    }

    @Override
    public void navigateToOrderDetailsActivity(Order order) {
        EventBus.getDefault().postSticky(new OrderSelectEvent(order));
        getView().navigateToOrderDetailsAvtivity();
    }

    @Override
    public void refreshOrderList(boolean finished) {
        orders.clear();
        if(finished){
            showOrderFinishList();
        }else{
            showOrderInprogressList();
        }
    }

    @Override
    public void loadMoreOrder(boolean finished) {
        User loginer = Repository.getInstance().getLoginUser();
        double currentIndex = ((double)orders.size())/5;
        if(finished){
            mOrderInteractor.getOrdersInFinished(loginer, 5, (int)Math.ceil(currentIndex)+1, this);
        }else{
            mOrderInteractor.getOrdersInProgress(loginer, 5, (int)Math.ceil(currentIndex)+1, this);
        }
    }

    @Override
    public void onNetWorkError() {
        Toast.makeText(getContext(),getActivity().getString(R.string.network_failed),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure() {
        Toast.makeText(getContext(),getActivity().getString(R.string.operation_failed),Toast.LENGTH_SHORT).show();
    }

   /* @Override
    public void onOrderListSuccess(List<Order> orderPoList) {
        getView().showOrderList(orderPoList);
    }
*/
   /* @Override
    public void onStateUpdateSuccess(Order.State newState) {

    }*/

    @Override
    public void onGetOrderListSuccess(List<Order> orderList) {
        if(orderList == null || orderList.size()<=0){
            Toast.makeText(getContext(),getActivity().getString(R.string.toast_no_orders),Toast.LENGTH_SHORT).show();
            getView().stopLoading();
            getView().stopRefreshing();
            return;
        }
        orders.addAll(orderList);
        getView().showOrderList(orders);
    }
}
