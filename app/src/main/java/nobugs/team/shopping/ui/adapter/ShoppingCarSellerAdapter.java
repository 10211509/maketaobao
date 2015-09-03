package nobugs.team.shopping.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import nobugs.team.shopping.R;
import nobugs.team.shopping.mvp.model.Order;
import nobugs.team.shopping.mvp.model.Product;
import nobugs.team.shopping.mvp.model.ProductType;
import nobugs.team.shopping.mvp.model.Shop;
import nobugs.team.shopping.ui.interfaces.CountChangeListener;

/**
 * Created by xiayong on 2015/8/29.
 */
public class ShoppingCarSellerAdapter extends PagerAdapter {
    private List<Order> orders;
    private Shop shop;
    private Context context;
    private CountChangeListener countChangeListener;
    public ShoppingCarSellerAdapter(Context context, Shop shop) {
        this.context = context;
        this.orders = new ArrayList<>();
        this.shop = shop;
        orders.add(createEmptyOrder());
    }

    public void setCountChangeListener(CountChangeListener countChangeListener){
        this.countChangeListener = countChangeListener;
    }
    public void addOrder(Order order) {
        if (order != null) {
            //we don't expect to add null into the list
            orders.add(order);
            if(countChangeListener != null){
                countChangeListener.onCountChange(orders.size());
            }
        }
    }

    public void replaceOrders(List<Order> orders) {
        if (orders != null) {
            //we don't expect to add null into the list
            this.orders = orders;
            this.orders.add(createEmptyOrder());
            if(countChangeListener != null){
                countChangeListener.onCountChange(orders.size());
            }
        }
    }

    public Order getOrder(int index) {
        if (index < 0 || index >= orders.size()) {
            throw new IndexOutOfBoundsException("the order is not added into the ViewPager");
        }
        return orders.get(index);
    }

    public boolean orderSuccessfulAdded(int index) {
        if (index < 0 || index >= orders.size()) {
            throw new IndexOutOfBoundsException("the order is not added into the ViewPager");
        }
        return !TextUtils.isEmpty(orders.get(index).getOrderid());
    }

    public void addEmptyOrder() {
        addOrder(createEmptyOrder());
    }

    public void deleteOrder(String orderid) {
        for (Order order : orders) {
            if (order.getOrderid().equals(orderid))
                orders.remove(order);
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

    @Override
    public void destroyItem(View view, int position, Object object) {
        ((ViewPager) view).removeView((View) object);
    }

    @Override
    public Object instantiateItem(View collection, final int position)                                //实例化Item
    {
        LayoutInflater inflater =
                (LayoutInflater) collection.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View container = inflater.inflate(R.layout.viewpager_item_shoppingcar_seller, null);
        Spinner spName = (Spinner) container.findViewById(R.id.sp_name);
        EditText etAmount = (EditText) container.findViewById(R.id.et_product_number);
        Spinner spUnit = (Spinner) container.findViewById(R.id.sp_unit);
        EditText etTotalPrice = (EditText) container.findViewById(R.id.ed_total_price);


        final Order order = orders.get(position);


        initProductName(spName, shop, order);
        etAmount.setText(String.valueOf(order.getProduct_count()));
        initProductUnit(spUnit, order);
        etTotalPrice.setText(String.valueOf(order.getPrice()));

        spName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int index, long id) {
//                orders.get(position).setPrice(123);
                String name = ((TextView)view).getText().toString();
                orders.get(position).getProduct().setName(name);
                orders.get(position).getProduct().setId(shop.getProducts().get(index).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int index, long id) {
                String unit = ((TextView)view).getText().toString();
                orders.get(position).getProduct().getType().setUnit(unit);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        etAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //leave it empty
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//leave it empty
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(TextUtils.isEmpty(s)){
                    orders.get(position).setProduct_count(0);
                    return;
                }
                orders.get(position).setProduct_count(Integer.valueOf(s.toString()));
            }
        });
        etTotalPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//leave it empty
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//leave it empty
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(TextUtils.isEmpty(s)){
                    orders.get(position).setPrice(0.0);
                    return;
                }
                orders.get(position).setPrice(Double.valueOf(s.toString()));
            }
        });
        ((ViewPager) collection).addView(container, 0);
        return container;
    }

    private void initProductUnit(Spinner spUnit, Order currentOrder) {
        String[] productUnit = context.getResources().getStringArray(R.array.product_unit);
        String selectedUnit = currentOrder.getProduct().getType().getUnit();
        ArrayAdapter<String> spProductUnit = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, productUnit);
        spUnit.setAdapter(spProductUnit);
        int position = 0;
        for (int i = 0; i < productUnit.length; i++) {
            if (selectedUnit.equals(productUnit[i])) {
                position = i;
            }
        }
        spUnit.setSelection(position, true);
        if(position == 0){
            currentOrder.getProduct().getType().setUnit(spProductUnit.getItem(position));
        }
    }

    private void initProductName(Spinner spName, Shop shop, Order currentOrder) {
        int position = 0;
        String selectedProductName = currentOrder.getProduct().getName();
        List<String> productNames = new ArrayList<>();
        for (int i = 0, count = shop.getProducts().size(); i < count; i++) {
            String productName = shop.getProducts().get(i).getName();
            if (productName.equals(selectedProductName)) {
                position = i;
            }
            productNames.add(productName);
        }
        ArrayAdapter<String> spNameAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, productNames);
        spName.setAdapter(spNameAdapter);
        spName.setSelection(position, true);
        if(position == 0){
            currentOrder.getProduct().setName(shop.getProducts().get(position).getName());
            currentOrder.getProduct().setId(shop.getProducts().get(position).getId());
        }
    }

    public Order createEmptyOrder() {
        Order order = new Order();
        order.setShop(shop);
        ProductType productType = new ProductType();
        productType.setUnit("");
        Product product = new Product(0, "", productType);
        order.setProduct(product);
        order.setPrice(0);
        return order;
    }
}
