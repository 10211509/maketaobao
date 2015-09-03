package nobugs.team.shopping.mvp.presenter;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import nobugs.team.shopping.event.CallBeginEvent;
import nobugs.team.shopping.event.RemoteOrderAddEvent;
import nobugs.team.shopping.event.RemoteOrderDelEvent;
import nobugs.team.shopping.im.IMSendHelper;
import nobugs.team.shopping.mvp.interactor.ShoppingCarInteractor;
import nobugs.team.shopping.mvp.interactor.ShoppingCarInteractorImpl;
import nobugs.team.shopping.mvp.model.Order;
import nobugs.team.shopping.mvp.model.User;
import nobugs.team.shopping.mvp.view.ShoppingCarView;

/**
 * Created by xiayong on 2015/8/29.
 */
public class ShoppingCarPresenterImpl extends BasePresenter<ShoppingCarView> implements ShoppingCarPresenter,ShoppingCarInteractor.Callback {
    private ShoppingCarInteractor shoppingCarInteractor;
    private List<Order> orders;
    private User mOwnUser;
    private User mPeerUser;

    public ShoppingCarPresenterImpl(ShoppingCarView shoppingCarView) {
        super(shoppingCarView);
        shoppingCarInteractor = new ShoppingCarInteractorImpl();
        orders = new ArrayList<>();
    }

    public void onEventMainThread(CallBeginEvent event) {
        mOwnUser = event.getMe();
        mPeerUser = event.getPeer();

        EventBus.getDefault().removeStickyEvent(event);
    }

    public void onEventMainThread(RemoteOrderAddEvent event) {
        Order orderAdd = event.getOrder();

        orders.add(orderAdd);
        getView().refreshViewPager(orders);

        EventBus.getDefault().removeStickyEvent(event);
    }

    public void onEventMainThread(RemoteOrderDelEvent event) {
        String orderDel = String.valueOf(event.getOrderId());

        for (Order order:orders){
            if(order.getOrderid().equals(orderDel))
                orders.remove(order);
        }
        getView().refreshViewPager(orders);

        EventBus.getDefault().removeStickyEvent(event);
    }

    @Override
    public void commitProduct() {
        //leave it empty
    }

    @Override
    public void onCreateView() {
        EventBus.getDefault().registerSticky(this);
    }

    @Override
    public void deleteProduct(int index) {
        if (index < 0 || index > orders.size()) {
            throw new IllegalArgumentException("The product index doesn't exist");
        }
        String orderId = orders.get(index).getOrderid();

        shoppingCarInteractor.deleteProduct(orderId, this);
    }

    @Override
    public void addProduct(Order order) {
        //pushed from service
        orders.add(order);
        //refresh UI
        getView().refreshViewPager(orders);
    }

    @Override
    public void onDeleteSuccess(String id) {
        for (Order order:orders){
            if(order.getOrderid().equals(id))
                orders.remove(order);
        }
        getView().refreshViewPager(orders);
        //tell the seller to refresh order list
        IMSendHelper.sendDelOrder(mOwnUser.getPhone(), mPeerUser.getPhone(), Integer.parseInt(id));
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onAddSuccess(Order order) {
        //
    }

    @Override
    public void onNetWorkError() {

    }

    @Override
    public void onFailure() {

    }
}
