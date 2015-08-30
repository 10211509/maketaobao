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
public class OrderDetailsPresenterImpl extends BasePresenter<OrderDetailsView> implements OrderDetailsPresenter {

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
        orderInteractor.updateState(order.getOrderid(),Order.State.delivered);
    }

    @Override
    public void updateToPayed() {
        orderInteractor.updateState(order.getOrderid(),Order.State.payed);
    }

    @Override
    public void updateToCollected() {
        orderInteractor.updateState(order.getOrderid(),Order.State.collected);
    }

    @Override
    public void cancelOrder() {
        orderInteractor.updateState(order.getOrderid(),Order.State.canceled);
    }
}
