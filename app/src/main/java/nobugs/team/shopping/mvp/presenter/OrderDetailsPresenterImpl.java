package nobugs.team.shopping.mvp.presenter;

import de.greenrobot.event.EventBus;
import nobugs.team.shopping.event.OrderSelectEvent;
import nobugs.team.shopping.mvp.interactor.OrderInteractor;
import nobugs.team.shopping.mvp.interactor.OrderInteractorImpl;
import nobugs.team.shopping.mvp.model.Order;
import nobugs.team.shopping.mvp.view.OrderDetailsView;

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
        if(order.getBuyer().isSeller()){
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
        orderInteractor.updateOrderState(order.getOrderid(), Order.State.delivered,this);
    }

    @Override
    public void updateToPayed() {
        orderInteractor.updateOrderState(order.getOrderid(), Order.State.payed,this);
    }

    @Override
    public void updateToCollected() {
        orderInteractor.updateOrderState(order.getOrderid(), Order.State.collected,this);
    }

    @Override
    public void cancelOrder() {
        orderInteractor.updateOrderState(order.getOrderid(), Order.State.canceled,this);
    }

    @Override
    public void onOrderStateUpdateSuccess() {

    }

    @Override
    public void onNetWorkError() {

    }

    @Override
    public void onFailure() {

    }
}
