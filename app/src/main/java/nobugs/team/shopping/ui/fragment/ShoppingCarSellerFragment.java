package nobugs.team.shopping.ui.fragment;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import nobugs.team.shopping.ui.widget.CircleIndicator;
import nobugs.team.shopping.utils.CommonTools;
import nobugs.team.shopping.utils.Phrase;

public class ShoppingCarSellerFragment extends BaseFragment<ShoppingCarSellerPresenter> implements ShoppingCarSellerView {

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

    @Bind(R.id.btn_sure)
    Button btnCommitSure;

    @Bind(R.id.tv_who)
    TextView tvBuyerName;

    @Bind(R.id.tv_commit_amount)
    TextView tvAmount;

    @Bind(R.id.tv_totalprice)
    TextView tvTotalprice;

    @Bind(R.id.layout_commit)
    View layoutCommit;
    @Bind(R.id.circleIndicator)
    CircleIndicator circleIndicator;


    private ShoppingCarSellerAdapter shoppingCarSellerAdapter;
    private FragmentActionListener fragmentActionListener;

    public static ShoppingCarSellerFragment newInstance() {
        ShoppingCarSellerFragment fragment = new ShoppingCarSellerFragment();
        return fragment;
    }

    public ShoppingCarSellerFragment() {
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
    protected ShoppingCarSellerPresenterImpl initPresenter() {
        return new ShoppingCarSellerPresenterImpl(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_shoppingcar_seller;
    }

    @OnClick(R.id.btn_addorder)
    public void onAddOrderClick() {
        int selectedPageIndex = vpContainer.getCurrentItem();
        Order order = shoppingCarSellerAdapter.getOrder(selectedPageIndex);
        View currentView = shoppingCarSellerAdapter.getPrimaryItem();
        if (!TextUtils.isEmpty(order.getOrderid())) {
            Toast.makeText(this.getActivity(), getActivity().getString(R.string.tv_product_already_added), Toast.LENGTH_SHORT).show();
            return;
        }

        Spinner spName = (Spinner) currentView.findViewById(R.id.sp_name);
        EditText etAmount = (EditText) currentView.findViewById(R.id.et_product_number);
        Spinner spUnit = (Spinner) currentView.findViewById(R.id.sp_unit);
        EditText etTotalPrice = (EditText) currentView.findViewById(R.id.ed_total_price);
        String productName = spName.getSelectedItem().toString();
        String amount = etAmount.getText().toString();
        String unit = spUnit.getSelectedItem().toString();
        String totalPrice = etTotalPrice.getText().toString();
        if (TextUtils.isEmpty(productName)) {
            Toast.makeText(this.getActivity(), getActivity().getString(R.string.toast_product_name), Toast.LENGTH_SHORT).show();
            return;
        }
        if (!CommonTools.isIntegerNumber(amount) || Integer.valueOf(amount) < 0) {
            Toast.makeText(this.getActivity(), getActivity().getString(R.string.toast_product_amount), Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(unit)) {
            Toast.makeText(this.getActivity(), getActivity().getString(R.string.toast_product_unit), Toast.LENGTH_SHORT).show();
            return;
        }
        if (!CommonTools.isIntegerNumber(totalPrice) && !CommonTools.isFloatPointNumber(totalPrice)) {
            Toast.makeText(this.getActivity(), getActivity().getString(R.string.toast_product_price), Toast.LENGTH_SHORT).show();
            return;
        }
        order.getProduct().setName(productName);
        int productid = order.getProductIdByName(productName);
        order.getProduct().setId(productid);
        order.setProduct_count(Integer.valueOf(amount));
        order.getProduct().getType().setUnit(unit);
        order.setPrice(Double.valueOf(totalPrice));
        getPresenter().addOrder(order);
    }

    @OnClick(R.id.btn_deleteorder)
    public void onDelOrderClick() {
        getPresenter().deleteOrder(vpContainer.getCurrentItem());
    }

    @OnClick(R.id.btn_sure)
    public void onCommitSureClick() {
//        getPresenter().deleteOrder(selectedPageIndex);
        fragmentActionListener.onShoppingCartCommit();
    }


    @Override
    public void initViewPager(Shop shop) {
        linearContainer.setVisibility(View.VISIBLE);
        shoppingCarSellerAdapter = new ShoppingCarSellerAdapter(getActivity(), shop);
        circleIndicator.setViewPager(vpContainer);
        vpContainer.setAdapter(shoppingCarSellerAdapter);

    }

    @Override
    public void refreshViewPagerWhenDataSetChange(List<Order> orders) {
        shoppingCarSellerAdapter.replaceOrders(orders);
        shoppingCarSellerAdapter.notifyDataSetChanged();
        circleIndicator.recreateIndicators();
        if (orders.size() <= 0) {
            circleIndicator.setVisibility(View.INVISIBLE);
        } else {
            circleIndicator.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showCommitView(String name, int amount, double totalPrice) {
        layoutCommit.setVisibility(View.VISIBLE);
        tvAmount.setText(Phrase.from(this.getActivity(), R.string.tv_commit_amout).put("amount", amount).format());
        tvBuyerName.setText("买方成功提交购物车！");
        tvTotalprice.setText(Phrase.from(this.getActivity(), R.string.tv_commit_totalprice).put("price", String.valueOf(totalPrice)).format());
    }

    @Override
    public void showPagerLast() {
        //TODO 写法错误
       /* int lastIndex = vpContainer.getChildCount();
        vpContainer.setCurrentItem(lastIndex, true);*/
    }

    public interface FragmentActionListener {
        void onShoppingCartCommit();
    }
}
