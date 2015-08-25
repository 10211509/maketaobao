package nobugs.team.shopping.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.demievil.library.RefreshLayout;

import java.util.List;

import butterknife.Bind;
import nobugs.team.shopping.R;
import nobugs.team.shopping.app.base.BaseFragment;
import nobugs.team.shopping.mvp.model.Order;
import nobugs.team.shopping.mvp.presenter.OrderListPresenter;
import nobugs.team.shopping.mvp.presenter.OrderListPresenterImpl;
import nobugs.team.shopping.mvp.view.OrderListView;
import nobugs.team.shopping.ui.activity.OrderDetailsActivity;
import nobugs.team.shopping.ui.adapter.OrderListAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link OrderInprogressFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderInprogressFragment extends BaseFragment<OrderListPresenter> implements OrderListView,RefreshLayout.OnRefreshListener, RefreshLayout.OnLoadListener ,ListView.OnItemClickListener{

    @Bind(R.id.swipe_container)
    RefreshLayout mRefreshLayout;
    @Bind(R.id.list_order)
    ListView mListOrder;

    private View mFooter;
    private OrderListAdapter mOrderListAdapter;
//    private List<Order> fakeDate;

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
    protected OrderListPresenter initPresenter() {
        return new OrderListPresenterImpl(this);
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
        mListOrder.setOnItemClickListener(this);
    }

    @Override
    protected void initData() {
//        mOrderListAdapter = new OrderListAdapter(this.getActivity(),getFakeDate());
        getPresenter().showOrderInprogressList();
    }


    @Override
    public void navigateToOrderDetailsAvtivity() {
        startActivity(new Intent(this.getActivity(),OrderDetailsActivity.class));
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
       Object object = parent.getItemAtPosition(position);
        if(object instanceof Order){
            getPresenter().navigateToOrderDetailsActivity((Order) object);
        }
    }
}
