package nobugs.team.shopping.ui.fragment;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.OnClick;
import nobugs.team.shopping.R;
import nobugs.team.shopping.app.base.BaseFragment;
import nobugs.team.shopping.mvp.model.Order;
import nobugs.team.shopping.mvp.model.Shop;
import nobugs.team.shopping.mvp.presenter.ShoppingCarSellerPresenter;
import nobugs.team.shopping.mvp.presenter.ShoppingCarSellerPresenterImpl;
import nobugs.team.shopping.mvp.view.ShoppingCarSellerView;
import nobugs.team.shopping.ui.adapter.AddShoppingCarAdapter;

public class AddShoppingCarFragment extends BaseFragment<ShoppingCarSellerPresenter> implements ShoppingCarSellerView,ViewPager.OnPageChangeListener {


    @Bind(R.id.tv_product_index)
    TextView tvProductIndex;
    @Bind(R.id.vPager)
    ViewPager vpContainer;
    @Bind(R.id.btn_addproduct)
    Button btnAddproduct;
    @Bind(R.id.btn_deleteproduct)
    Button btnDeleteproduct;

    private AddShoppingCarAdapter addShoppingCarAdapter;
    private int selectedPageIndex = 0;
    public static AddShoppingCarFragment newInstance() {
        AddShoppingCarFragment fragment = new AddShoppingCarFragment();
        return fragment;
    }

    public AddShoppingCarFragment() {
        // Required empty public constructor
    }

    @Override
    protected ShoppingCarSellerPresenterImpl initPresenter() {
        return new ShoppingCarSellerPresenterImpl(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_add_shopping_car;
    }

    @OnClick({R.id.btn_deleteproduct, R.id.btn_addproduct})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_addproduct:
//                View view  = vpContainer.getChildAt(se)
                getPresenter().addOrder(null);
                break;
            case R.id.btn_deleteproduct:
                getPresenter().deleteOrder(selectedPageIndex);
                break;
        }
    }



    @Override
    public void initViewPager(Shop shop) {
        addShoppingCarAdapter = new AddShoppingCarAdapter(getActivity(),shop);
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
