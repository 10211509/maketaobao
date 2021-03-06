package nobugs.team.shopping.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import nobugs.team.shopping.R;
import nobugs.team.shopping.mvp.model.Order;
import nobugs.team.shopping.mvp.model.Product;
import nobugs.team.shopping.mvp.model.ProductType;
import nobugs.team.shopping.mvp.model.Shop;
import nobugs.team.shopping.ui.interfaces.CountChangeListener;
import nobugs.team.shopping.utils.CommonTools;

/**
 * Created by xiayong on 2015/8/29.
 */
public class ShoppingCarSellerAdapter extends PagerAdapter {
    private List<Order> orders;
    private Shop shop;
    private Context context;
    private CountChangeListener countChangeListener;
    private View currentView;
    private List<String> productTypes;

    public ShoppingCarSellerAdapter(Context context, Shop shop,List<String> productTypes) {
        this.context = context;
        this.orders = new ArrayList<>();
        this.shop = shop;
        this.productTypes = productTypes;
        orders.add(createEmptyOrder());
    }

    public void setCountChangeListener(CountChangeListener countChangeListener) {
        this.countChangeListener = countChangeListener;
    }

    public void addOrder(Order order) {
        if (order != null) {
            //we don't expect to add null into the list
            orders.add(order);
            if (countChangeListener != null) {
                countChangeListener.onCountChange(orders.size());
            }
        }
    }

    public void replaceOrders(List<Order> orders) {
        if (orders != null) {
            //we don't expect to add null into the list
            this.orders = new ArrayList<>(orders);
            this.orders.add(createEmptyOrder());
            if (countChangeListener != null) {
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
        if (isEmpeyOrder(order)) {
            etAmount.setText("");
            etTotalPrice.setText("");
        } else {
            etAmount.setText(String.valueOf(order.getProduct_count()));
            etTotalPrice.setText(String.valueOf(order.getPrice()));
        }
        initProductUnit(spUnit, order);

        //order added successfully,disable all input view
        boolean orderNotAdded = TextUtils.isEmpty(order.getOrderid());
        spName.setEnabled(orderNotAdded);
        etAmount.setEnabled(orderNotAdded);
        spUnit.setEnabled(orderNotAdded);
        etTotalPrice.setEnabled(orderNotAdded);

    ((ViewPager)collection).addView(container, 0);

    return container;
}

    private void initProductUnit(Spinner spUnit, Order currentOrder) {
        String selectedUnit = currentOrder.getProduct().getType().getUnit();
        int position = 0;
        for (int i = 0; i < productTypes.size(); i++) {
            if (selectedUnit.equals(productTypes.get(i))) {
                position = i;
            }
        }
        ArrayAdapter<String> spProductUnit = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, productTypes);
        spUnit.setAdapter(spProductUnit);

        if (!isEmpeyOrder(currentOrder)) {
            spUnit.setSelection(position, true);
        }
//      /*  if(position == 0){
//        currentOrder.getProduct().getType().setUnit(spProductUnit.getItem(position));
//        }*/
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
        if (!isEmpeyOrder(currentOrder)) {
            //recover to the pre state
            spName.setSelection(position, true);
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

    public boolean isEmpeyOrder(Order order) {
        return order == null || TextUtils.isEmpty(order.getProduct().getType().getUnit()) || TextUtils.isEmpty(order.getProduct().getName());
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        currentView = (View) object;
    }

    public View getPrimaryItem() {
        return currentView;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
