package nobugs.team.shopping.ui.fragment;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import nobugs.team.shopping.ui.interfaces.CountChangeListener;
import nobugs.team.shopping.ui.widget.CircleIndicator;
import nobugs.team.shopping.utils.Phrase;

/**
 * display the products that buyer has chosen
 */
public class ShoppingCarBuyerFragment extends BaseFragment<ShoppingCarPresenter> implements ShoppingCarView{


    @Bind(R.id.tv_shoppingcar_title)
    TextView tvShoppingcarTitle;
    @Bind(R.id.vPager)
    ViewPager vpContainer;
    @Bind(R.id.btn_delete)
    Button btnDelete;
    @Bind(R.id.btn_commit_shopping_cart)
    Button btnCommitPruduct;
    @Bind(R.id.linear_buyer_shoppingcar_container)
    LinearLayout linearBuyerShoppingcarContainer;
    @Bind(R.id.tv_who)
    TextView tvCommitTitle;
    @Bind(R.id.tv_commit_amount)
    TextView tvCommitAmount;
    @Bind(R.id.tv_totalprice)
    TextView tvCommitTotalprice;
    @Bind(R.id.layout_commit)
    View layoutCommit;

    @Bind(R.id.btn_sure)
    Button btnCommitSure;
    @Bind(R.id.circleIndicator)
    CircleIndicator circleIndicator;

    private ShoppingCarAdapter shoppingCarAdapter;
//    private int selectedPageIndex = 0;
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
        circleIndicator.setViewPager(vpContainer);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_shoppingcar_buyer;
    }

    @OnClick({R.id.btn_delete, R.id.btn_commit_shopping_cart,R.id.btn_sure})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_delete:
                if(shoppingCarAdapter == null || shoppingCarAdapter.getCount()<=0){
                    Toast.makeText(getActivity(),"购物车里暂时没有商品！",Toast.LENGTH_SHORT).show();
                    return;
                }
                getPresenter().deleteProduct(vpContainer.getCurrentItem());
                break;
            case R.id.btn_commit_shopping_cart:
                if(shoppingCarAdapter == null || shoppingCarAdapter.getCount()<=0){
                    Toast.makeText(getActivity(),"购物车里暂时没有商品！",Toast.LENGTH_SHORT).show();
                    return;
                }
                getPresenter().commitShoppingCart(vpContainer.getCurrentItem());
                break;
            case R.id.btn_sure:
                fragmentActionListener.onShoppingCartCommit();
//                layoutCommit.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void refreshViewPager(List<Order> orders) {
        shoppingCarAdapter.replaceOrders(orders);
        shoppingCarAdapter.notifyDataSetChanged();//refresh UI
        circleIndicator.recreateIndicators();
        if (orders.size() <= 1) {
            circleIndicator.setVisibility(View.INVISIBLE);
        } else {
            circleIndicator.setVisibility(View.VISIBLE);
        }
        if (orders == null || orders.size() <= 0) {
            linearBuyerShoppingcarContainer.setVisibility(View.INVISIBLE);
        } else {
            linearBuyerShoppingcarContainer.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showCommitView(int amount, double totalPrice) {
        layoutCommit.setVisibility(View.VISIBLE);
        tvCommitAmount.setText(Phrase.from(this.getActivity(), R.string.tv_commit_amout).put("amount", amount).format());
        tvCommitTitle.setText("购物车提交成功！");
        tvCommitTotalprice.setText(Phrase.from(this.getActivity(),R.string.tv_commit_totalprice).put("price",String.valueOf(totalPrice)).format());
    }

    /*@Override
    public void loadCar(List<Order> orders) {
        shoppingCarAdapter.replaceOrders(orders);
        shoppingCarAdapter.notifyDataSetChanged();//refresh UI
    }*/

    public interface FragmentActionListener {
        void onShoppingCartCommit();
    }
}
