package nobugs.team.shopping.mvp.presenter;

import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import de.greenrobot.event.EventBus;
import nobugs.team.shopping.R;
import nobugs.team.shopping.event.SelectShopEvent;
import nobugs.team.shopping.mvp.interactor.ShoppingCarInteractor;
import nobugs.team.shopping.mvp.interactor.ShoppingCarInteractorImpl;
import nobugs.team.shopping.mvp.model.Order;
import nobugs.team.shopping.mvp.model.Shop;
import nobugs.team.shopping.mvp.view.ShoppingCarSellerView;

/**
 * Created by xiayong on 2015/8/30.
 */
public class ShoppingCarSellerPresenterImpl extends BasePresenter<ShoppingCarSellerView> implements ShoppingCarSellerPresenter, ShoppingCarInteractor.Callback {
    private Shop shop;
    private List<Order> orders;
    private ShoppingCarInteractor shoppingCarInteractor;

    public ShoppingCarSellerPresenterImpl(ShoppingCarSellerView addShoppingCarView) {
        super(addShoppingCarView);
        orders = Collections.emptyList();
        shoppingCarInteractor = new ShoppingCarInteractorImpl();
    }

    @Override
    public void onCreate() {
        EventBus.getDefault().registerSticky(this);
    }

    public void onEventMainThread(SelectShopEvent event) {
        shop = event.getShop();
        getView().initViewPager(shop);
    }

    @Override
    public void addOrder(Order order) {

        shoppingCarInteractor.addProduct(order, this);
    }

    @Override
    public void deleteOrder(int index) {
        if(index < 0 || index >= orders.size()){
            Toast.makeText(getContext(),"不能删除该商品！",Toast.LENGTH_SHORT).show();
            return;
        }
        shoppingCarInteractor.deleteProduct(orders.get(index).getOrderid(),this);
    }


    @Override
    public void onDeleteSuccess(String id) {
        for (Order order:orders){
            if(order.getOrderid().equals(id))
                orders.remove(order);
        }
        getView().deleteItemOfViewPager(id);
    }

    @Override
    public void onAddSuccess(Order order) {
        orders.add(order);
        getView().addItemToViewPager(order);
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
