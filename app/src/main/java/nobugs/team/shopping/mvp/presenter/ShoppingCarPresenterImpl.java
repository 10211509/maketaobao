package nobugs.team.shopping.mvp.presenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        //TODO hang up the phone
    }

    @Override
    public void deleteProduct(int index) {
        if (index < 0 || index > orders.size()) {
            throw new IllegalArgumentException("The product index doesn't exist");
        }
        shoppingCarInteractor.deleteProduct(orders.get(index).getOrderid(),this);
    }

    @Override
    public void addProduct(Order order) {
        //pushed from service
        orders.add(order);
        //refresh UI
        getView().addOrder(order);
    }

    @Override
    public void onDeleteSuccess(String id) {

    }

    @Override
    public void onAddSuccess(Order order) {

    }

    @Override
    public void onNetWorkError() {

    }

    @Override
    public void onFailure() {

    }
}
