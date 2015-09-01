package nobugs.team.shopping.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import nobugs.team.shopping.R;
import nobugs.team.shopping.app.base.BaseFragment;
import nobugs.team.shopping.mvp.model.Order;
import nobugs.team.shopping.mvp.model.Shop;
import nobugs.team.shopping.mvp.presenter.ShoppingCarSellerPresenter;
import nobugs.team.shopping.mvp.presenter.ShoppingCarSellerPresenterImpl;
import nobugs.team.shopping.mvp.view.ShoppingCarSellerView;
import nobugs.team.shopping.ui.adapter.ShoppingCarSellerAdapter;

public class ShoppingCarSellerFragment extends BaseFragment<ShoppingCarSellerPresenter> implements ShoppingCarSellerView, ViewPager.OnPageChangeListener {

    @Bind(R.id.tv_product_index)
    TextView tvProductIndex;

    @Bind(R.id.vPager)
    ViewPager vpContainer;

    @Bind(R.id.btn_addorder)
    Button btnAddproduct;

    @Bind(R.id.btn_deleteorder)
    Button btnDeleteproduct;

    private ShoppingCarSellerAdapter shoppingCarSellerAdapter;
    private int selectedPageIndex = 0;

    public static ShoppingCarSellerFragment newInstance() {
        ShoppingCarSellerFragment fragment = new ShoppingCarSellerFragment();
        return fragment;
    }

    public ShoppingCarSellerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {

        }
    }

    @Override
    protected ShoppingCarSellerPresenterImpl initPresenter() {
        return new ShoppingCarSellerPresenterImpl(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_shoppingcar_seller;
    }

    @OnClick(R.id.btn_addorder)
    public void onAddOrderClick() {
        Order order = shoppingCarSellerAdapter.getOrder(selectedPageIndex);
//        View currentPage = vpContainer.get
        if(TextUtils.isEmpty(order.getProduct().getName())){
            Toast.makeText(this.getActivity(),getActivity().getString(R.string.toast_product_name),Toast.LENGTH_SHORT).show();
            return;
        }
        if(order.getProduct_count() <= 0){
            Toast.makeText(this.getActivity(),getActivity().getString(R.string.toast_product_amount),Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(order.getProduct().getType().getUnit())){
            Toast.makeText(this.getActivity(),getActivity().getString(R.string.toast_product_unit),Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(order.getProduct().getType().getUnit())){
            Toast.makeText(this.getActivity(),getActivity().getString(R.string.toast_product_unit),Toast.LENGTH_SHORT).show();
            return;
        }
        if(order.getPrice()<=0){
            Toast.makeText(this.getActivity(),getActivity().getString(R.string.toast_product_price),Toast.LENGTH_SHORT).show();
            return;
        }
        getPresenter().addOrder(order);
    }

    @OnClick(R.id.btn_deleteorder)
    public void onDelOrderClick() {
        getPresenter().deleteOrder(selectedPageIndex);
    }

    @Override
    public void initViewPager(Shop shop) {
        shoppingCarSellerAdapter = new ShoppingCarSellerAdapter(getActivity(), shop);
        if (vpContainer != null) {
            vpContainer.setAdapter(shoppingCarSellerAdapter);
        }

    }

    @Override
    public void refreshViewPagerWhenDataSetChange(List<Order> orders) {
        shoppingCarSellerAdapter.replaceOrders(orders);
        shoppingCarSellerAdapter.notifyDataSetChanged();
    }

   /* @Override
    public void addItemToViewPager(Order order) {
        //TODO
        shoppingCarSellerAdapter.addEmptyOrder();
        shoppingCarSellerAdapter.notifyDataSetChanged();
    }

    @Override
    public void deleteItemOfViewPager(String orderid) {
        shoppingCarSellerAdapter.deleteOrder(orderid);
        shoppingCarSellerAdapter.notifyDataSetChanged();
    }*/

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        selectedPageIndex = position;
        boolean enable = shoppingCarSellerAdapter.orderSuccessfulAdded(selectedPageIndex);
        //if the order successfully added,then should not add it again!
        btnAddproduct.setEnabled(!enable);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
