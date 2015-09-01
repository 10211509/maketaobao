package nobugs.team.shopping.mvp.presenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.greenrobot.event.EventBus;
import nobugs.team.shopping.event.RemoteShopSelectEvent;
import nobugs.team.shopping.mvp.interactor.ShoppingCarInteractor;
import nobugs.team.shopping.mvp.interactor.ShoppingCarInteractorImpl;
import nobugs.team.shopping.mvp.model.Order;
import nobugs.team.shopping.mvp.view.ShoppingCarView;

/**
 * Created by xiayong on 2015/8/29.
 */
public class ShoppingCarPresenterImpl extends BasePresenter<ShoppingCarView> implements ShoppingCarPresenter,ShoppingCarInteractor.Callback {
    private ShoppingCarInteractor shoppingCarInteractor;
    private List<Order> orders;

    public ShoppingCarPresenterImpl(ShoppingCarView shoppingCarView) {
        super(shoppingCarView);
        shoppingCarInteractor = new ShoppingCarInteractorImpl();
        orders = new ArrayList<>();
    }


    @Override
    public void commitProduct() {
        //leave it empty
    }
    @Override
    public void onCreateView() {
        EventBus.getDefault().registerSticky(this);
    }

    public void onEventMainThread(RemoteShopSelectEvent event) {
    }
    @Override
    public void deleteProduct(int index) {
        if (index < 0 || index > orders.size()) {
            throw new IllegalArgumentException("The product index doesn't exist");
        }
        shoppingCarInteractor.deleteProduct(orders.get(index).getOrderid(), this);
    }

    @Override
    public void addProduct(Order order) {
        //pushed from service
        orders.add(order);
        //refresh UI
        getView().addOrder(orders);
    }

    @Override
    public void onDeleteSuccess(String id) {
        for (Order order:orders){
            if(order.getOrderid().equals(id))
                orders.remove(order);
        }
        getView().loadCar(orders);
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
