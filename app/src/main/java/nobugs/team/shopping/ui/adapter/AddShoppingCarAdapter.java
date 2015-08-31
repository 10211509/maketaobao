package nobugs.team.shopping.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Collections;
import java.util.List;

import nobugs.team.shopping.R;
import nobugs.team.shopping.mvp.model.Order;
import nobugs.team.shopping.mvp.model.Shop;

/**
 * Created by xiayong on 2015/8/29.
 */
public class AddShoppingCarAdapter extends PagerAdapter {
    private List<Order> orders;
    private Shop shop;
    private Context context;
    public AddShoppingCarAdapter(Context context,Shop shop) {
        this.orders = Collections.emptyList();
        this.shop = shop;
        this.context = context;
    }

    public void addOrder(Order order){
        if(order != null){
            //we don't expect to add null into the list
            orders.add(order);
        }
    }
    public void addOrders(List<Order> orders){
        if(orders != null){
            //we don't expect to add null into the list
            this.orders = orders;
        }
    }
    public void deleteOrder(String orderid){
        for (Order order:orders){
            if(order.getOrderid().equals(orderid))
                orders.remove(order);
        }
    }
    @Override
    public int getCount() {
        return orders.size()+1;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(View view, int position, Object object) {
        ((ViewPager) view).removeView((View) object);
    }

    @Override
    public Object instantiateItem(View view, int position)                                //实例化Item
    {
        LayoutInflater inflater =
                (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View container = inflater.inflate(R.layout.viewpager_item_shoppingcar_seller, null);
        Spinner spName = (Spinner) container.findViewById(R.id.sp_name);
        EditText etAmount = (EditText) container.findViewById(R.id.et_product_number);
        Spinner spUnit = (Spinner) container.findViewById(R.id.sp_unit);
        EditText etTotalPrice = (EditText) container.findViewById(R.id.ed_total_price);

        if(position >= orders.size()){
            initProductName(spName,shop,"");
            etAmount.setText("");
            initProductUnit(spUnit, "");
            etTotalPrice.setText("0");
        }else {
            Order order = orders.get(position);
            initProductName(spName,shop,order.getProduct().getName());
            etAmount.setText(String.valueOf(order.getProduct_count()));
            initProductUnit(spUnit, order.getProduct().getType().getUnit());
            etTotalPrice.setText(String.valueOf(order.getPrice()));
        }

        return container;
    }

    private void initProductUnit(Spinner spUnit, String selectedUnit) {
        String [] productUnit = context.getResources().getStringArray(R.array.product_unit);
        ArrayAdapter<String> spProductUnit = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,productUnit);
        spUnit.setAdapter(spProductUnit);
        int position = 0;
        for(int i=0;i<productUnit.length;i++){
            if(selectedUnit.equals(productUnit[i])){
                position = i;
            }
        }
        spUnit.setSelection(position, true);
    }

    private void initProductName(Spinner spName, Shop shop, String selectedProductName) {
        int position = 0;
        List<String> productNames = Collections.emptyList();
        for (int i=0,count = shop.getProducts().size();i<count;i++){
            String productName = shop.getProducts().get(i).getName();
            if (productName.equals(selectedProductName)){
                position = i;
            }
            productNames.add(productName);
        }
        ArrayAdapter<String> spNameAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,productNames);
        spName.setAdapter(spNameAdapter);
        spName.setSelection(position,true);
    }
}
