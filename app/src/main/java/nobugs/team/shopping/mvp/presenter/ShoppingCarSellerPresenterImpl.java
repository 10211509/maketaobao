package nobugs.team.shopping.mvp.presenter;

import android.text.TextUtils;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import de.greenrobot.event.EventBus;
import nobugs.team.shopping.R;
import nobugs.team.shopping.event.CallBeginEvent;
import nobugs.team.shopping.event.RemoteOrderDelEvent;
import nobugs.team.shopping.im.IMSendHelper;
import nobugs.team.shopping.mvp.interactor.OrderInteractor;
import nobugs.team.shopping.mvp.interactor.OrderInteractorImpl;
import nobugs.team.shopping.mvp.interactor.ProductInteractor;
import nobugs.team.shopping.mvp.interactor.ProductInteractorImpl;
import nobugs.team.shopping.mvp.model.Order;
import nobugs.team.shopping.mvp.model.Product;
import nobugs.team.shopping.mvp.model.Shop;
import nobugs.team.shopping.mvp.model.User;
import nobugs.team.shopping.mvp.view.ShoppingCarSellerView;

/**
 * Created by xiayong on 2015/8/30.
 */
public class ShoppingCarSellerPresenterImpl extends BasePresenter<ShoppingCarSellerView> implements ShoppingCarSellerPresenter,/* ShoppingCarInteractor.Callback, */ProductInteractor.Callback, OrderInteractor.AddCallback, OrderInteractor.DeleteCallback {
    private static final String TAG = "ShoppingCarSeller";
    private Shop shop;
    private CopyOnWriteArrayList<Order> orders;
    //    private ShoppingCarInteractor shoppingCarInteractor;
    private ProductInteractor productInteractor;
    private OrderInteractor orderInteractor;
    private User mOwnUser;
    private User mPeerUser;

    public ShoppingCarSellerPresenterImpl(ShoppingCarSellerView addShoppingCarView) {
        super(addShoppingCarView);
        orders = new CopyOnWriteArrayList<>();
//        shoppingCarInteractor = new ShoppingCarInteractorImpl();
        productInteractor = new ProductInteractorImpl();
        orderInteractor = new OrderInteractorImpl();
    }

    @Override
    public void onCreateView() {
        EventBus.getDefault().registerSticky(this);
    }

    public void onEventMainThread(CallBeginEvent event) {
        shop = event.getShop();
        mOwnUser = event.getMe();
        mPeerUser = event.getPeer();

        //get product list by shop id
        productInteractor.getProducts(String.valueOf(shop.getId()), this);
        EventBus.getDefault().removeStickyEvent(event);
    }

    public void onEventMainThread(RemoteOrderDelEvent event) {
        int orderDel = event.getOrderId();

        //TODO 买家删除订单，处理UI
        Toast.makeText(getContext(), "买家删除了订单，id:" + orderDel, Toast.LENGTH_SHORT).show();

        EventBus.getDefault().removeStickyEvent(event);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void addOrder(Order order) {
        orders.add(order);
        order.setSeller(mOwnUser);
        order.setBuyer(mPeerUser);
        order.setOrderState(1);

        orderInteractor.addOrder(order, this);
    }

    @Override
    public void deleteOrder(int index) {
        if (index < 0 || index >= orders.size()) {
            Toast.makeText(getContext(), "该商品尚未添加至购物车！", Toast.LENGTH_SHORT).show();
            return;
        }
        String orderId = orders.get(index).getOrderid();
        if (TextUtils.isEmpty(orderId)) {
            //the order has not been added into the database
            orders.remove(index);
            getView().refreshViewPagerWhenDataSetChange(orders);
            return;
        }
        orderInteractor.removeOrder(orderId, this);
    }

    @Override
    public void onAddOrderSuccess(Order order) {
        Toast.makeText(getContext(), "添加成功", Toast.LENGTH_SHORT).show();
        IMSendHelper.sendAddOrder(mOwnUser.getPhone(), mPeerUser.getPhone(), order);
        getView().refreshViewPagerWhenDataSetChange(orders);
//        getView().showPagerLast();
    }

    @Override
    public void onDeleteSuccess(String orderId) {
        Toast.makeText(getContext(), "删除成功", Toast.LENGTH_SHORT).show();
        IMSendHelper.sendDelOrder(mOwnUser.getPhone(), mPeerUser.getPhone(), Integer.parseInt(orderId));
        for (Order order : orders) {
            if (order.getOrderid() != null && order.getOrderid().equals(orderId))
                orders.remove(order);
        }
        getView().refreshViewPagerWhenDataSetChange(orders);
    }

   /* @Override
    public void onAddSuccess(List<Order> order) {
        Toast.makeText(getContext(),"添加成功",Toast.LENGTH_SHORT).show();
        getView().refreshViewPagerWhenDataSetChange(orders);
    }*/

    @Override
    public void onSuccess(List<Product> products) {
        Shop shop = new Shop();
        shop.setProducts(products);
        getView().initViewPager(shop);
    }

    @Override
    public void onNetWorkError() {
        Toast.makeText(getContext(), getContext().getString(R.string.network_failed), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure() {
        Toast.makeText(getContext(), getContext().getString(R.string.operation_failed), Toast.LENGTH_SHORT).show();
    }


}
