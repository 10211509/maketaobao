package nobugs.team.shopping.ui.fragment;

import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.LinearLayout;
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
import nobugs.team.shopping.utils.Phrase;

public class ShoppingCarSellerFragment extends BaseFragment<ShoppingCarSellerPresenter> implements ShoppingCarSellerView, ViewPager.OnPageChangeListener {

    @Bind(R.id.tv_product_index)
    TextView tvProductIndex;

    @Bind(R.id.vPager_seller)
    ViewPager vpContainer;

    @Bind(R.id.btn_addorder)
    Button btnAddproduct;

    @Bind(R.id.btn_deleteorder)
    Button btnDeleteproduct;

    @Bind(R.id.tv_shoppingcar_title)
    TextView tvShoppingcarTitle;

    @Bind(R.id.linear_container)
    LinearLayout linearContainer;

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
        /////////TODO 测试数据
        order.getProduct().setName("苹果");
        order.setProduct_count(3);
        order.getProduct().getType().setUnit("框");
        order.setPrice(300);
        /////////////////
        if (TextUtils.isEmpty(order.getProduct().getName())) {
            Toast.makeText(this.getActivity(), getActivity().getString(R.string.toast_product_name), Toast.LENGTH_SHORT).show();
            return;
        }
        if (order.getProduct_count() <= 0) {
            Toast.makeText(this.getActivity(), getActivity().getString(R.string.toast_product_amount), Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(order.getProduct().getType().getUnit())) {
            Toast.makeText(this.getActivity(), getActivity().getString(R.string.toast_product_unit), Toast.LENGTH_SHORT).show();
            return;
        }
        if (order.getPrice() <= 0) {
            Toast.makeText(this.getActivity(), getActivity().getString(R.string.toast_product_price), Toast.LENGTH_SHORT).show();
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
        linearContainer.setVisibility(View.VISIBLE);
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

    @Override
    public void showPagerLast() {
        int lastIndex = vpContainer.getChildCount();
        vpContainer.setCurrentItem(lastIndex);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        selectedPageIndex = position;
        boolean enable = shoppingCarSellerAdapter.orderSuccessfulAdded(selectedPageIndex);
        //if the order successfully added,then should not add it again!
        btnAddproduct.setEnabled(!enable);
        CharSequence charSequence = Phrase.from(this.getActivity(), R.string.tv_shopping_car_number).put("number", shoppingCarSellerAdapter.getCount()).put("index", selectedPageIndex).format();
        tvProductIndex.setText(charSequence);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
