package nobugs.team.shopping.repo;

import java.util.ArrayList;
import java.util.List;

import nobugs.team.shopping.mvp.model.Order;
import nobugs.team.shopping.mvp.model.Product;
import nobugs.team.shopping.mvp.model.ProductType;
import nobugs.team.shopping.mvp.model.Shop;
import nobugs.team.shopping.mvp.model.User;
import nobugs.team.shopping.repo.api.AddOrderApi;
import nobugs.team.shopping.repo.api.DeleteOrderApi;
import nobugs.team.shopping.repo.api.GetOrderApi;
import nobugs.team.shopping.repo.api.GetOrderListApi;
import nobugs.team.shopping.repo.api.GetProductListApi;
import nobugs.team.shopping.repo.api.GetShopListApi;
import nobugs.team.shopping.repo.api.GetTypeListApi;
import nobugs.team.shopping.repo.api.LoginApi;
import nobugs.team.shopping.repo.api.UpdateOrderApi;
import nobugs.team.shopping.repo.api.retrofit.AddOrderApiImpl;
import nobugs.team.shopping.repo.api.retrofit.DeleteOrderApiImpl;
import nobugs.team.shopping.repo.api.retrofit.GetOrderApiImpl;
import nobugs.team.shopping.repo.api.retrofit.GetOrderListApiImpl;
import nobugs.team.shopping.repo.api.retrofit.GetProductListApiImpl;
import nobugs.team.shopping.repo.api.retrofit.GetShopListApiImpl;
import nobugs.team.shopping.repo.api.retrofit.GetTypeListApiImpl;
import nobugs.team.shopping.repo.api.retrofit.LoginApiImpl;
import nobugs.team.shopping.repo.api.retrofit.RetrofitAdapter;
import nobugs.team.shopping.repo.api.retrofit.UpdateOrderApiImpl;
import nobugs.team.shopping.repo.mapper.OrderMapper;

/**
 * Created by Administrator on 2015/8/23 0023.
 */
public class Repository {

    private static Repository mInstance;

    public static Repository getInstance() {
        if (mInstance == null) {
            mInstance = new Repository();
        }
        return mInstance;
    }

    private final static int MAIN_PRODUCT_PARENT_ID = 0;

    private RetrofitAdapter adapter;

    public RetrofitAdapter getAdapter() {
        return adapter;
    }


    private LoginApi loginApi;
    private GetTypeListApi getTypeListApi;
    private GetShopListApi getShopListApi;
    private GetOrderListApi getOrderListApi;
    private GetProductListApi getProductListApi;
    private GetOrderApi getOrderApi;
    private AddOrderApi addOrderApi;
    private DeleteOrderApi deleteOrderApi;
    private UpdateOrderApi updateOrderApi;

    /** 类型缓存 */
//    private List<ProductType> typeListCache;

    /**
     * 用户信息缓存
     */
//    private User userCache;

    /*public User getUserCache() {
        return userCache;
    }*/
    private Repository() {
        this.adapter = new RetrofitAdapter();

        this.loginApi = new LoginApiImpl(adapter);
        this.getTypeListApi = new GetTypeListApiImpl(adapter);
        this.getShopListApi = new GetShopListApiImpl(adapter);
        this.getOrderListApi = new GetOrderListApiImpl(adapter);
        this.getProductListApi = new GetProductListApiImpl(adapter);
        this.getOrderApi = new GetOrderApiImpl(adapter);
        this.addOrderApi = new AddOrderApiImpl(adapter);
        this.deleteOrderApi = new DeleteOrderApiImpl(adapter);
        this.updateOrderApi = new UpdateOrderApiImpl(adapter);
    }

    public User getLoginUser() {
        return this.loginApi.getUser();
    }

    public void login(String userName, String password, final RepoCallback.Get<User> callbackGet) {
        loginApi.login(userName, password, new LoginApi.Callback() {
            @Override
            public void onFinish(User user) {
                callbackGet.onGotDataSuccess(user);
            }

            @Override
            public void onError(int errType, String errMsg) {
                callbackGet.onError(errType, errMsg);
            }
        });
    }

    public void getMainTypeList(final RepoCallback.GetList<ProductType> callbackGet) {
        getTypeListApi.getTypeList(new GetTypeListApi.Callback() {
            @Override
            public void onFinish(List<ProductType> productTypes) {
                callbackGet.onGotDataListSuccess(findMainTypeList(productTypes));
            }

            @Override
            public void onError(int errType, String errMsg) {
                callbackGet.onError(errType, errMsg);
            }
        });
    }

    public void getSubTypeList(final ProductType parent, final RepoCallback.GetList<ProductType> callbackGet) {
        getTypeListApi.getTypeList(new GetTypeListApi.Callback() {
            @Override
            public void onFinish(List<ProductType> productTypes) {
                callbackGet.onGotDataListSuccess(findSubTypeList(productTypes, parent));
            }

            @Override
            public void onError(int errType, String errMsg) {
                callbackGet.onError(errType, errMsg);
            }
        });
    }

    public void getProductList(int shopId, final RepoCallback.GetList<Product> callbackGet) {
        getProductListApi.getProductList(shopId, new GetProductListApi.Callback() {

            @Override
            public void onFinish(List<Product> shops) {
                callbackGet.onGotDataListSuccess(shops);
            }

            @Override
            public void onError(int errType, String errMsg) {
                callbackGet.onError(errType, errMsg);
            }
        });
    }

    public void getShopList(final ProductType parent, final String keyword, final RepoCallback.GetList<Shop> callbackGet) {
//        if (parent.getShops() == null) {
        //TODO use Map<ProductType,List<Shop>> to cache the data
        getShopListApi.getShopList(parent, keyword, new GetShopListApi.Callback() {
            @Override
            public void onFinish(List<Shop> shops) {
//                    parent.setShops(shops);
                callbackGet.onGotDataListSuccess(shops);
            }

            @Override
            public void onError(int errType, String errMsg) {
                callbackGet.onError(errType, errMsg);
            }
        });
       /* } else {
            callbackGet.onGotDataListSuccess(parent.getShops());    //TODO 是否需要更新ShopList
        }*/
    }

    public void getOrderListBuyer(int buyerId, int everyPage, int currentPage, boolean isOver, final RepoCallback.GetList<Order> callbackGet) {
        getOrderListApi.getOrderListBuyer(buyerId, everyPage, currentPage, isOver, new GetOrderListApi.Callback() {
            @Override
            public void onFinish(List<Order> orders) {
                callbackGet.onGotDataListSuccess(orders);
            }

            @Override
            public void onError(int errType, String errMsg) {
                callbackGet.onError(errType, errMsg);
            }
        });
    }

    public void getOrderListSeller(int sellerId, int everyPage, int currentPage, boolean isOver, final RepoCallback.GetList<Order> callbackGet) {
        getOrderListApi.getOrderListSeller(sellerId, everyPage, currentPage, isOver, new GetOrderListApi.Callback() {
            @Override
            public void onFinish(List<Order> orders) {

                callbackGet.onGotDataListSuccess(orders);
            }

            @Override
            public void onError(int errType, String errMsg) {
                callbackGet.onError(errType, errMsg);
            }
        });
    }

    public void getOrder(int orderId, final RepoCallback.Get<Order> callbackGet) {
        getOrderApi.getOrder(orderId, new GetOrderApi.Callback() {
            @Override
            public void onFinish(Order order) {
                callbackGet.onGotDataSuccess(order);
            }

            @Override
            public void onError(int errType, String errMsg) {
                callbackGet.onError(errType, errMsg);
            }
        });
    }

    public void addOrder(Order order, final RepoCallback.Add<Order> callbackGet) {
        OrderMapper mapper = new OrderMapper();
        addOrderApi.addOrder(mapper.fromModel(order), new AddOrderApi.Callback() {
            @Override
            public void onFinish(int orderId) {
                callbackGet.onAddDataSuccess(orderId);
            }

            @Override
            public void onError(int errType, String errMsg) {
                callbackGet.onError(errType, errMsg);
            }
        });
    }

    public void removeOrder(int orderId, final RepoCallback.Remove<Order> callbackGet) {
        deleteOrderApi.deleteOrder(orderId, new DeleteOrderApi.Callback() {
            @Override
            public void onFinish() {
                callbackGet.onRemoveDataSuccess();
            }

            @Override
            public void onError(int errType, String errMsg) {
                callbackGet.onError(errType, errMsg);
            }
        });
    }

    public void updateOrder(Order order, final RepoCallback.Update<Order> callbackGet) {
        OrderMapper mapper = new OrderMapper();
        updateOrderApi.updateOrder(mapper.fromModel(order), new UpdateOrderApi.Callback() {
            @Override
            public void onFinish() {
                callbackGet.onUpateDataSuccess();
            }

            @Override
            public void onError(int errType, String errMsg) {
                callbackGet.onError(errType, errMsg);
            }
        });
    }

    private List<ProductType> findMainTypeList(List<ProductType> productTypes) { //TODO 直接转成树状存储
        if (productTypes == null || productTypes.isEmpty()) {
            return null;
        }
        List<ProductType> result = new ArrayList<>();
        for (ProductType product : productTypes) {
            if (product.getParentId() == MAIN_PRODUCT_PARENT_ID) {
                result.add(product);
            }
        }
        return result;
    }

    private List<ProductType> findSubTypeList(List<ProductType> productTypes, ProductType parent) {
        if (productTypes == null || productTypes.isEmpty() || parent == null) {
            return null;
        }
        if (parent.getSubTypes() != null) {
            return parent.getSubTypes();
        }
        List<ProductType> result = new ArrayList<>();
        for (ProductType product : productTypes) {
            if (product.getParentId() == parent.getId()) {
                result.add(product);
            }
        }
        parent.setSubTypes(result); //增加结果到缓存，下会直接返回

        return result;
    }

}
