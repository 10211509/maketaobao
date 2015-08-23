package nobugs.team.shopping.mvp.presenter;

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
        setView(orderDetailsView);
        orderInteractor = new OrderInteractorImpl();
    }

    @Override
    public void onCreate() {

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
