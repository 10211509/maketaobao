package nobugs.team.shopping.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.OnClick;
import nobugs.team.shopping.R;
import nobugs.team.shopping.app.base.BaseFragment;
import nobugs.team.shopping.mvp.model.Order;
import nobugs.team.shopping.mvp.model.Product;
import nobugs.team.shopping.mvp.model.Shop;
import nobugs.team.shopping.mvp.presenter.ShoppingCarSellerPresenter;
import nobugs.team.shopping.mvp.presenter.ShoppingCarSellerPresenterImpl;
import nobugs.team.shopping.mvp.view.ShoppingCarSellerView;
import nobugs.team.shopping.ui.adapter.AddShoppingCarAdapter;

public class ShoppingCarSellerFragment extends BaseFragment<ShoppingCarSellerPresenter> implements ShoppingCarSellerView, ViewPager.OnPageChangeListener {

    @Bind(R.id.tv_product_index)
    TextView tvProductIndex;

    @Bind(R.id.vPager)
    ViewPager vpContainer;

    @Bind(R.id.btn_addorder)
    Button btnAddproduct;

    @Bind(R.id.btn_deleteorder)
    Button btnDeleteproduct;

    private AddShoppingCarAdapter addShoppingCarAdapter;
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
        Product product = new Product();
        Order order = new Order();
        order.setProduct_count(5);
        order.setPrice(3000);
        getPresenter().addOrder(order);
    }

    @OnClick(R.id.btn_deleteorder)
    public void onDelOrderClick() {
        getPresenter().deleteOrder(selectedPageIndex);
    }

    @Override
    public void initViewPager(Shop shop) {
        addShoppingCarAdapter = new AddShoppingCarAdapter(getActivity(), shop);
        vpContainer.setAdapter(addShoppingCarAdapter);

    }

    @Override
    public void addItemToViewPager(Order order) {
        addShoppingCarAdapter.addOrder(order);
        addShoppingCarAdapter.notifyDataSetChanged();
    }

    @Override
    public void deleteItemOfViewPager(String orderid) {
        addShoppingCarAdapter.deleteOrder(orderid);
        addShoppingCarAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        selectedPageIndex = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
