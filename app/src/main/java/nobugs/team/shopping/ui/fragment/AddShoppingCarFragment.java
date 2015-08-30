package nobugs.team.shopping.ui.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nobugs.team.shopping.R;
import nobugs.team.shopping.app.base.BaseFragment;
import nobugs.team.shopping.mvp.model.Order;
import nobugs.team.shopping.mvp.model.Shop;
import nobugs.team.shopping.mvp.presenter.AddShoppingCarPresenter;
import nobugs.team.shopping.mvp.presenter.AddShoppingCarPresenterImpl;
import nobugs.team.shopping.mvp.presenter.ShoppingCarPresenter;
import nobugs.team.shopping.mvp.presenter.ShoppingCarPresenterImpl;
import nobugs.team.shopping.mvp.view.AddShoppingCarView;
import nobugs.team.shopping.mvp.view.ShoppingCarView;
import nobugs.team.shopping.ui.adapter.AddShoppingCarAdapter;
import nobugs.team.shopping.ui.adapter.ShoppingCarAdapter;

public class AddShoppingCarFragment extends BaseFragment<AddShoppingCarPresenter> implements AddShoppingCarView,ViewPager.OnPageChangeListener {


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
    protected AddShoppingCarPresenterImpl initPresenter() {
        return new AddShoppingCarPresenterImpl(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_add_shopping_car;
    }

    @OnClick({R.id.btn_delete, R.id.btn_commitproduct})
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
