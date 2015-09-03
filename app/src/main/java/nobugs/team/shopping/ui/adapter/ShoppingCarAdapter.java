package nobugs.team.shopping.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import nobugs.team.shopping.R;
import nobugs.team.shopping.mvp.model.Order;
import nobugs.team.shopping.utils.Phrase;

/**
 * Created by xiayong on 2015/8/29.
 */
public class ShoppingCarAdapter extends PagerAdapter {
    private List<Order> orders;

    public ShoppingCarAdapter(List<Order> orders) {
        this.orders = orders;
    }

    public void addOrder(Order order) {
        if (order != null) {
            //we don't expect to add null into the list
            orders.add(order);
        }
    }

    public void replaceOrders(List<Order> orders) {
        if (orders != null) {
            //we don't expect to add null into the list
            this.orders = orders;
        }
    }

    @Override
    public int getCount() {
        return orders.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

  /*  public boolean orderSuccessfulAdded(int index) {
        if (index < 0 || index >= orders.size()) {
            throw new IndexOutOfBoundsException("the order is not added into the ViewPager");
        }
        return !TextUtils.isEmpty(orders.get(index).getOrderid());
    }*/

    @Override
    public void destroyItem(View view, int position, Object object) {
        ((ViewPager) view).removeView((View) object);
    }

    @Override
    public Object instantiateItem(View view, int position)                                //实例化Item
    {
        LayoutInflater inflater =
                (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View container = inflater.inflate(R.layout.viewpager_item_shoppingcar_buyer, null);
        TextView tvName = (TextView) container.findViewById(R.id.tv_name);
        TextView tvAmount = (TextView) container.findViewById(R.id.tv_number);
        TextView tvUnit = (TextView) container.findViewById(R.id.tv_unit);
        TextView tvTotalPrice = (TextView) container.findViewById(R.id.tv_total_price);

        Order order = orders.get(position);
        tvName.setText(Phrase.from(container, R.string.tv_product_name).put("name", order.getProduct().getName()).format());
        tvAmount.setText(Phrase.from(container, R.string.tv_number).put("number", order.getProduct_count()).format());
        tvUnit.setText(order.getProduct().getType().getUnit());
        tvTotalPrice.setText(Phrase.from(container, R.string.tv_total_price).put("price", String.valueOf(order.getPrice())).format());
        ((ViewPager) view).addView(container, 0);
        return container;
    }
}
