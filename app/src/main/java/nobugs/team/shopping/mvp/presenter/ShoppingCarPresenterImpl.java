package nobugs.team.shopping.mvp.presenter;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import nobugs.team.shopping.event.CallBeginEvent;
import nobugs.team.shopping.event.RemoteOrderAddEvent;
import nobugs.team.shopping.event.RemoteOrderDelEvent;
import nobugs.team.shopping.im.IMSendHelper;
import nobugs.team.shopping.mvp.interactor.OrderInteractor;
import nobugs.team.shopping.mvp.interactor.OrderInteractorImpl;
import nobugs.team.shopping.mvp.model.Order;
import nobugs.team.shopping.mvp.model.User;
import nobugs.team.shopping.mvp.view.ShoppingCarView;

/**
 * Created by xiayong on 2015/8/29.
 */
public class ShoppingCarPresenterImpl extends BasePresenter<ShoppingCarView> implements ShoppingCarPresenter, OrderInteractor.DeleteCallback {
    private OrderInteractor orderInteractor;
    private List<Order> orders;
    private User mOwnUser;
    private User mPeerUser;

    public ShoppingCarPresenterImpl(ShoppingCarView shoppingCarView) {
        super(shoppingCarView);
        orderInteractor = new OrderInteractorImpl();
        orders = new ArrayList<>();
    }

    public void onEventMainThread(CallBeginEvent event) {
        mOwnUser = event.getMe();
        mPeerUser = event.getPeer();

        EventBus.getDefault().removeStickyEvent(event);
    }

    public void onEventMainThread(RemoteOrderAddEvent event) {
        Order orderAdd = event.getOrder();
        Toast.makeText(getContext(), "卖家添加了订单，id:" + orderAdd.getOrderid(), Toast.LENGTH_SHORT).show();

        orders.add(orderAdd);
        getView().refreshViewPager(orders);

        EventBus.getDefault().removeStickyEvent(event);
    }

    public void onEventMainThread(RemoteOrderDelEvent event) {
        String orderDel = String.valueOf(event.getOrderId());

        Toast.makeText(getContext(), "卖家删除了订单，id:" + orderDel, Toast.LENGTH_SHORT).show();
        for (Order order : orders) {
            if (order.getOrderid() != null && order.getOrderid().equals(orderDel))
                orders.remove(order);
        }
        getView().refreshViewPager(orders);

        EventBus.getDefault().removeStickyEvent(event);
    }



    private void commitShoppingCart() {
        /*if (index < 0 || index > orders.size()) {
            Toast.makeText(getContext(), "请选择正确的商品提交", Toast.LENGTH_SHORT).show();
        }
        Order order = orders.get(index);*/


        int productTotal = 0;
        double priceTotal = 0;

        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i) != null) {
                productTotal += orders.get(i).getProduct_count();
                priceTotal += orders.get(i).getPrice();
            }
        }
        getView().showCommitView(productTotal, priceTotal);
        // 将提交购物车推送给卖家
        IMSendHelper.sendShoppingCartCommit(mOwnUser.getPhone(), mPeerUser.getPhone(), productTotal, priceTotal);
    }

    @Override
    public void onCreateView() {
        EventBus.getDefault().registerSticky(this);
    }

    @Override
    public void deleteProduct(int index) {
        if (index < 0 || index > orders.size()) {
//            throw new IllegalArgumentException("The product index doesn't exist");
            Toast.makeText(getContext(), "请选择正确的商品提交", Toast.LENGTH_SHORT).show();
        }
        String orderId = orders.get(index).getOrderid();

        orderInteractor.removeOrder(orderId, this);
    }

    @Override
    public void addProduct(Order order) {
        //pushed from service
        orders.add(order);
        //refresh UI
        getView().refreshViewPager(orders);
    }

    @Override
    public void onConfirmBtnClick() {
        StringBuilder orderIds = new StringBuilder();
        for(Order order : orders){
            orderIds.append(order.getOrderid()).append(",");
        }
        orderInteractor.updateOrdersState(orderIds.toString(), null, new OrderInteractor.UpdateCallback() {
            @Override
            public void onOrderStateUpdateSuccess(Order.State newState) {
                commitShoppingCart();
            }

            @Override
            public void onNetWorkError() {

            }

            @Override
            public void onFailure() {

            }
        });
    }

    @Override
    public void onDeleteSuccess(String id) {
       List<Order> orderList = new ArrayList<>();
        for (Order order : orders) {
            if (!order.getOrderid().equals(id))
                orderList.add(order);
        }
        Toast.makeText(getContext(), "删除订单成功" , Toast.LENGTH_SHORT).show();

        orders = orderList;
        getView().refreshViewPager(orders);
        //tell the seller to refresh order list
        IMSendHelper.sendDelOrder(mOwnUser.getPhone(), mPeerUser.getPhone(), Integer.parseInt(id));
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void onNetWorkError() {

    }

    @Override
    public void onFailure() {

    }
}
