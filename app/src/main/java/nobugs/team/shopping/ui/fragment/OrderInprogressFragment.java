package nobugs.team.shopping.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.demievil.library.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import nobugs.team.shopping.R;
import nobugs.team.shopping.app.base.BaseFragment;
import nobugs.team.shopping.mvp.model.Order;
import nobugs.team.shopping.mvp.presenter.OrderInProgressPresenter;
import nobugs.team.shopping.mvp.presenter.OrderInProgressPresenterImpl;
import nobugs.team.shopping.mvp.view.OrderListView;
import nobugs.team.shopping.ui.adapter.OrderListAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link OrderInprogressFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderInprogressFragment extends BaseFragment<OrderInProgressPresenter> implements OrderListView,RefreshLayout.OnRefreshListener, RefreshLayout.OnLoadListener {

    @Bind(R.id.swipe_container)
    RefreshLayout mRefreshLayout;
    @Bind(R.id.list_order)
    ListView mListOrder;

    private View mFooter;
    private OrderListAdapter mOrderListAdapter;
    private List<Order> fakeDate;

    public static OrderInprogressFragment newInstance() {
        OrderInprogressFragment fragment = new OrderInprogressFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public OrderInprogressFragment() {
        // Required empty public constructor
    }


    @Override
    protected OrderInProgressPresenter initPresenter() {
        return new OrderInProgressPresenterImpl(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_order_inprogress;
    }

    @Override
    protected void initView() {
        mFooter = LayoutInflater.from(this.getActivity()).inflate(R.layout.refresh_layout_footer, null);
        mListOrder.addFooterView(mFooter);
        mRefreshLayout.setChildView(mListOrder);
        mOrderListAdapter = new OrderListAdapter(this.getActivity());
        mListOrder.setAdapter(mOrderListAdapter);
    }

    @Override
    protected void initData() {
//        mOrderListAdapter = new OrderListAdapter(this.getActivity(),getFakeDate());
        getPresenter().showOrderInprogressList();
    }


    @Override
    public void navigateToOrderDetailsAvtivity(Order order) {

    }

    @Override
    public void refreshOrderList(List<Order> orderList) {

    }

    @Override
    public void loadMoreOrders(List<Order> orderList) {

    }

    @Override
    public void showOrderList(List<Order> orderList) {
        mOrderListAdapter.setOrders(orderList);
        mOrderListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoad() {
        getPresenter().loadMoreOrder();
    }

    @Override
    public void onRefresh() {
        getPresenter().refreshOrderList();
    }
}
