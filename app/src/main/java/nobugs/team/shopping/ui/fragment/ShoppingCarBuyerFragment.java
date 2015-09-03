package nobugs.team.shopping.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nobugs.team.shopping.R;
import nobugs.team.shopping.app.base.BaseFragment;
import nobugs.team.shopping.mvp.model.Order;
import nobugs.team.shopping.mvp.presenter.ShoppingCarPresenter;
import nobugs.team.shopping.mvp.presenter.ShoppingCarPresenterImpl;
import nobugs.team.shopping.mvp.view.ShoppingCarView;
import nobugs.team.shopping.ui.adapter.ShoppingCarAdapter;
import nobugs.team.shopping.utils.Phrase;

/**
 * display the products that buyer has chosen
 */
public class ShoppingCarBuyerFragment extends BaseFragment<ShoppingCarPresenter> implements ShoppingCarView, ViewPager.OnPageChangeListener {


    @Bind(R.id.tv_shoppingcar_title)
    TextView tvShoppingcarTitle;
    @Bind(R.id.vPager)
    ViewPager vpContainer;
    @Bind(R.id.btn_delete)
    Button btnDelete;
    @Bind(R.id.btn_commitproduct)
    Button btnCommitPruduct;
    @Bind(R.id.tv_buyer_product_index)
    TextView tvBuyerProductIndex;
    @Bind(R.id.linear_buyer_shoppingcar_container)
    LinearLayout linearBuyerShoppingcarContainer;

    private ShoppingCarAdapter shoppingCarAdapter;
    private int selectedPageIndex = 0;
    private FragmentActionListener fragmentActionListener;

    public static ShoppingCarBuyerFragment newInstance() {
        ShoppingCarBuyerFragment fragment = new ShoppingCarBuyerFragment();
        return fragment;
    }

    public ShoppingCarBuyerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fragmentActionListener = (FragmentActionListener) activity;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected ShoppingCarPresenter initPresenter() {
        return new ShoppingCarPresenterImpl(this);
    }

    @Override
    protected void initView() {
        List<Order> orders = new ArrayList<>();
        shoppingCarAdapter = new ShoppingCarAdapter(orders);
        vpContainer.setAdapter(shoppingCarAdapter);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_shoppingcar_buyer;
    }

    @OnClick({R.id.btn_delete, R.id.btn_commitproduct})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_delete:
                getPresenter().deleteProduct(selectedPageIndex);
                break;
            case R.id.btn_commitproduct:
                fragmentActionListener.onFragmentChange(btnCommitPruduct);
                break;
        }
    }

    @Override
    public void refreshViewPager(List<Order> orders) {
        shoppingCarAdapter.replaceOrders(orders);
        shoppingCarAdapter.notifyDataSetChanged();//refresh UI
        if (orders == null || orders.size() <=0 ){
            linearBuyerShoppingcarContainer.setVisibility(View.INVISIBLE);
        }else{
            linearBuyerShoppingcarContainer.setVisibility(View.VISIBLE);
        }
    }

    /*@Override
    public void loadCar(List<Order> orders) {
        shoppingCarAdapter.replaceOrders(orders);
        shoppingCarAdapter.notifyDataSetChanged();//refresh UI
    }*/

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //empty
    }

    @Override
    public void onPageSelected(int position) {
        selectedPageIndex = position;
        //if the order successfully added,then should not add it again!
        CharSequence charSequence = Phrase.from(this.getActivity(), R.string.tv_shopping_car_number).put("number", shoppingCarAdapter.getCount()).put("index", selectedPageIndex).format();
        tvBuyerProductIndex.setText(charSequence);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //empty
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    public interface FragmentActionListener {
        void onFragmentChange(View view);
    }
}
