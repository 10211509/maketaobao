package nobugs.team.shopping.mvp.presenter;

import android.widget.Toast;

import de.greenrobot.event.EventBus;
import nobugs.team.shopping.R;
import nobugs.team.shopping.event.OrderSelectEvent;
import nobugs.team.shopping.mvp.interactor.OrderInteractor;
import nobugs.team.shopping.mvp.interactor.OrderInteractorImpl;
import nobugs.team.shopping.mvp.model.Order;
import nobugs.team.shopping.mvp.model.User;
import nobugs.team.shopping.mvp.view.OrderDetailsView;
import nobugs.team.shopping.repo.Repository;

/**
 * Created by xiayong on 2015/8/23.
 */
public class OrderDetailsPresenterImpl extends BasePresenter<OrderDetailsView> implements OrderDetailsPresenter,OrderInteractor.UpdateCallback {

    private Order order;
    private OrderInteractor orderInteractor;

    public OrderDetailsPresenterImpl(OrderDetailsView orderDetailsView) {
        super(orderDetailsView);
        orderInteractor = new OrderInteractorImpl();
    }

    @Override
    public void onCreate() {
        EventBus.getDefault().registerSticky(this);
    }
    public void onEventMainThread(OrderSelectEvent orderEvent) {
        order = orderEvent.getOrder();
        User loginer = Repository.getInstance().getLoginUser();
        if(!loginer.isSeller()){
            getView().showBuyerView(order);
        }else{
            getView().showSellerView(order);
        }

    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void updateToDelivered() {
        orderInteractor.updateOrderState(order.getOrderid(), Order.State.delivered, this);
    }

    @Override
    public void updateToPayed() {
        orderInteractor.updateOrderState(order.getOrderid(), Order.State.payed, this);
    }

    @Override
    public void updateToCollected() {
        orderInteractor.updateOrderState(order.getOrderid(), Order.State.collected, this);

    }

    @Override
    public void updateToAccept() {
        orderInteractor.updateOrderState(order.getOrderid(), Order.State.received, this);
    }

    @Override
    public void cancelOrder() {
        orderInteractor.updateOrderState(order.getOrderid(), Order.State.canceled, this);
    }

    @Override
    public void onOrderStateUpdateSuccess(Order.State newState) {
        Toast.makeText(getContext(),"订单状态修改成功",Toast.LENGTH_SHORT).show();
        order.setOrderState(newState);
//        getView().updateButtonState(Repository.getInstance().getLoginUser(),newState);
        User loginer = Repository.getInstance().getLoginUser();
        if(!loginer.isSeller()){
            getView().showBuyerView(order);
        }else{
            getView().showSellerView(order);
        }
    }

    @Override
    public void onNetWorkError() {
        Toast.makeText(getContext(),getContext().getString(R.string.network_failed),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure() {
        Toast.makeText(getContext(),getContext().getString(R.string.operation_failed),Toast.LENGTH_SHORT).show();

    }
}
