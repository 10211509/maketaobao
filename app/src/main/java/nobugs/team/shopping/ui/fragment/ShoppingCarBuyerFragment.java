package nobugs.team.shopping.ui.fragment;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import nobugs.team.shopping.R;
import nobugs.team.shopping.app.base.BaseFragment;
import nobugs.team.shopping.mvp.model.Order;
import nobugs.team.shopping.mvp.presenter.ShoppingCarPresenter;
import nobugs.team.shopping.mvp.presenter.ShoppingCarPresenterImpl;
import nobugs.team.shopping.mvp.view.ShoppingCarView;
import nobugs.team.shopping.ui.adapter.ShoppingCarAdapter;

/**
 * display the products that buyer has chosen
 */
public class ShoppingCarBuyerFragment extends BaseFragment<ShoppingCarPresenter> implements ShoppingCarView,ViewPager.OnPageChangeListener {


    @Bind(R.id.tv_shoppingcar_title)
    TextView tvShoppingcarTitle;
    @Bind(R.id.vPager)
    ViewPager vpContainer;
    @Bind(R.id.btn_delete)
    Button btnDelete;
    @Bind(R.id.btn_commitproduct)
    Button btnCommitPruduct;

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

    @OnClick({R.id.btn_delete,R.id.btn_commitproduct})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_delete:
                getPresenter().deleteProduct(selectedPageIndex);
                break;
            case R.id.btn_commitproduct:
//                getPresenter().commitProduct();
                fragmentActionListener.onFragmentChange(btnCommitPruduct);
                break;
        }
    }
    @Override
    public void addOrder(List<Order> orders) {
        shoppingCarAdapter.replaceOrders(orders);
        shoppingCarAdapter.notifyDataSetChanged();//refresh UI
    }

    @Override
    public void loadCar(List<Order> orders) {
        shoppingCarAdapter.replaceOrders(orders);
        shoppingCarAdapter.notifyDataSetChanged();//refresh UI
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //empty
    }

    @Override
    public void onPageSelected(int position) {
        selectedPageIndex = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //empty
    }

    public interface FragmentActionListener{
        void onFragmentChange(View view);
    }
}
