package nobugs.team.shopping.ui.adapter;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nobugs.team.shopping.R;
import nobugs.team.shopping.mvp.model.Order;
import nobugs.team.shopping.utils.Phrase;

/**
 * Created by xiayong on 2015/8/23.
 */
public class OrderListAdapter extends BaseAdapter {

    private List<Order> orders;
    private Context context;

    public OrderListAdapter(Context context,List<Order> orders){
        this.context = context;
        this.orders = orders;
    }

    public OrderListAdapter(Context context){
        this.context = context;
        this.orders = Collections.emptyList();
    }

    public void setOrders(List<Order> orders){
        this.orders = orders;
    }
    @Override
    public int getCount() {
        return orders.size();
    }

    @Override
    public Object getItem(int position) {
        return orders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_order_list, null);
            viewHolder.tvOrderId = (TextView) convertView.findViewById(R.id.tv_orderid);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tvAmount = (TextView) convertView.findViewById(R.id.tv_amount);
            viewHolder.tvPrice = (TextView) convertView.findViewById(R.id.tv_price);
            viewHolder.tvPlaceTime = (TextView) convertView.findViewById(R.id.tv_place_time);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Order order = orders.get(position);
        viewHolder.tvOrderId.setText(Phrase.from(context,R.string.order_id).put("orderid",order.getOrderid()).format());
        viewHolder.tvName.setText(Phrase.from(context,R.string.order_name).put("name", order.getProduct().getName()).format());
        viewHolder.tvAmount.setText(Phrase.from(context,R.string.order_amount).put("amount",order.getProduct_count()).format());
        viewHolder.tvPrice.setText(Phrase.from(context,R.string.order_price).put("price",String.valueOf(order.getPrice())).format());
        viewHolder.tvPlaceTime.setText(Phrase.from(context,R.string.order_place_time).put("place_time",order.getPlace_time()).format());
        return convertView;
    }

    class ViewHolder {
        public TextView tvOrderId;
        public TextView tvName;
        public TextView tvAmount;
        public TextView tvPrice;
        public TextView tvPlaceTime;
    }

}