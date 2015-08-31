package nobugs.team.shopping.mvp.presenter;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.greenrobot.event.EventBus;
import nobugs.team.shopping.R;
import nobugs.team.shopping.event.RemoteShopSelectEvent;
import nobugs.team.shopping.mvp.interactor.ProductInteractor;
import nobugs.team.shopping.mvp.interactor.ProductInteractorImpl;
import nobugs.team.shopping.mvp.interactor.ShoppingCarInteractor;
import nobugs.team.shopping.mvp.interactor.ShoppingCarInteractorImpl;
import nobugs.team.shopping.mvp.model.Order;
import nobugs.team.shopping.mvp.model.Product;
import nobugs.team.shopping.mvp.model.Shop;
import nobugs.team.shopping.mvp.view.ShoppingCarSellerView;

/**
 * Created by xiayong on 2015/8/30.
 */
public class ShoppingCarSellerPresenterImpl extends BasePresenter<ShoppingCarSellerView> implements ShoppingCarSellerPresenter, ShoppingCarInteractor.Callback,ProductInteractor.Callback {
    private Shop shop;
    private List<Order> orders;
    private ShoppingCarInteractor shoppingCarInteractor;
    private ProductInteractor productInteractor;

    public ShoppingCarSellerPresenterImpl(ShoppingCarSellerView addShoppingCarView) {
        super(addShoppingCarView);
        orders = new ArrayList<>();
        shoppingCarInteractor = new ShoppingCarInteractorImpl();
        productInteractor = new ProductInteractorImpl();
    }

    @Override
    public void onCreateView() {
        EventBus.getDefault().registerSticky(this);
    }

    public void onEventMainThread(RemoteShopSelectEvent event) {
        shop = event.getShop();
        //get product list by shop id
//        shoppingCarInteractor.
        productInteractor.getProducts(String.valueOf(shop.getId()), this);
        EventBus.getDefault().removeStickyEvent(event);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
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
        shoppingCarInteractor.deleteProduct(orders.get(index).getOrderid(), this);
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
    public void onSuccess(List<Product> products) {
        Shop shop = new Shop();
        shop.setProducts(products);
        getView().initViewPager(shop);
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
