package nobugs.team.shopping.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nobugs.team.shopping.R;
import nobugs.team.shopping.app.base.BaseActivity;
import nobugs.team.shopping.mvp.model.Order;
import nobugs.team.shopping.mvp.presenter.OrderDetailsPresenter;
import nobugs.team.shopping.mvp.presenter.OrderDetailsPresenterImpl;
import nobugs.team.shopping.mvp.view.OrderDetailsView;
import nobugs.team.shopping.utils.Phrase;

public class OrderDetailsActivity extends BaseActivity<OrderDetailsPresenter> implements OrderDetailsView {


    @Bind(R.id.tv_orderid)
    TextView tvOrderid;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_amount)
    TextView tvAmount;
    @Bind(R.id.tv_price)
    TextView tvPrice;
    @Bind(R.id.tv_place_time)
    TextView tvPlaceTime;
    @Bind(R.id.btn_delivered)
    Button btnDelivered;
    @Bind(R.id.btn_payed)
    Button btnPayed;
    @Bind(R.id.btn_collected)
    Button btnCollected;
    @Bind(R.id.btn_canceled)
    Button btnCanceled;

    @Override
    protected OrderDetailsPresenter initPresenter() {
        return new OrderDetailsPresenterImpl(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_order_list;
    }

    @Override
    public void showBuyerView(Order order) {
        updateOrderView(order);

        btnPayed.setVisibility(View.VISIBLE);
        btnCanceled.setVisibility(View.VISIBLE);
        btnDelivered.setVisibility(View.GONE);
        btnCollected.setVisibility(View.GONE);

        if(order.getOrderState() == Order.State.payed){
            btnPayed.setEnabled(true);
            btnCanceled.setEnabled(true);
        }else{
            btnPayed.setEnabled(false);
            btnCanceled.setEnabled(false);
        }

    }

    @Override
    public void showSellerView(Order order) {
        updateOrderView(order);

        btnPayed.setVisibility(View.GONE);
        btnCanceled.setVisibility(View.GONE);
        btnDelivered.setVisibility(View.VISIBLE);
        btnCollected.setVisibility(View.VISIBLE);

        if(order.getOrderState() == Order.State.payed){
            btnDelivered.setEnabled(false);
            btnCollected.setEnabled(true);
        }else if(order.getOrderState() == Order.State.collected){
            btnDelivered.setEnabled(true);
            btnCollected.setEnabled(false);
        }else{
            btnDelivered.setEnabled(false);
            btnCollected.setEnabled(false);
        }

    }
    private void updateOrderView(Order order){
        tvOrderid.setText(Phrase.from(this, R.string.order_id).put("orderid",order.getOrderid()).format());
        tvName.setText(Phrase.from(this,R.string.order_name).put("name", order.getProduct().getName()).format());
        tvAmount.setText(Phrase.from(this,R.string.order_amount).put("amount",order.getProduct_count()).format());
        tvPrice.setText(Phrase.from(this,R.string.order_price).put("price",String.valueOf(order.getPrice())).format());
        tvPlaceTime.setText(Phrase.from(this,R.string.order_place_time).put("place_time",order.getPlace_time()).format());
    }
    @OnClick({R.id.btn_delivered,R.id.btn_payed,R.id.btn_collected,R.id.btn_canceled})
    void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_payed:
                getPresenter().updateToPayed();
                break;
            case R.id.btn_collected:
                getPresenter().updateToCollected();
                break;
            case R.id.btn_delivered:
                getPresenter().updateToDelivered();
                break;
            case R.id.btn_canceled:
                getPresenter().cancelOrder();
                break;
        }
    }
}
