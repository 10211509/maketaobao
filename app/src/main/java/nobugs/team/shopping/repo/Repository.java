package nobugs.team.shopping.repo;

import java.util.ArrayList;
import java.util.List;

import nobugs.team.shopping.mvp.model.Order;
import nobugs.team.shopping.mvp.model.ProductType;
import nobugs.team.shopping.mvp.model.Shop;
import nobugs.team.shopping.mvp.model.User;
import nobugs.team.shopping.repo.api.GetOrderListApi;
import nobugs.team.shopping.repo.api.GetShopListApi;
import nobugs.team.shopping.repo.api.GetTypeListApi;
import nobugs.team.shopping.repo.api.LoginApi;
import nobugs.team.shopping.repo.api.mock.GetOrderListApiMock;
import nobugs.team.shopping.repo.api.mock.GetShopListApiMock;
import nobugs.team.shopping.repo.api.mock.GetTypeListApiMock;
import nobugs.team.shopping.repo.api.retrofit.LoginApiImpl;
import nobugs.team.shopping.repo.api.retrofit.RetrofitAdapter;

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

    /** 类型缓存 */
    private List<ProductType> typeListCache;

    /** 用户信息缓存 */
    private User userCache;

    public User getUserCache() {
        return userCache;
    }

    private Repository() {
        this.adapter = new RetrofitAdapter();

        this.loginApi = new LoginApiImpl(adapter);
        this.getTypeListApi = new GetTypeListApiMock();
        this.getShopListApi = new GetShopListApiMock(); //测试数据
        this.getOrderListApi = new GetOrderListApiMock();//测试数据
    }

    public void login(String userName, String password, final RepoCallback.Get<User> callbackGet) {
        loginApi.login(userName, password, new LoginApi.Callback() {
            @Override
            public void onFinish(User user) {
                userCache = user;
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
                typeListCache = productTypes;
                callbackGet.onGotDataListSuccess(findMainTypeList(productTypes));
            }

            @Override
            public void onError(int errType, String errMsg) {
                callbackGet.onError(errType, errMsg);
            }
        });
    }

    public void getSubTypeList(final ProductType parent, final RepoCallback.GetList<ProductType> callbackGet) {
        if (typeListCache == null) {     //缓存为空，重新获取
            getTypeListApi.getTypeList(new GetTypeListApi.Callback() {
                @Override
                public void onFinish(List<ProductType> productTypes) {
                    typeListCache = productTypes;
                    callbackGet.onGotDataListSuccess(findSubTypeList(typeListCache, parent));
                }

                @Override
                public void onError(int errType, String errMsg) {
                    callbackGet.onError(errType, errMsg);
                }
            });
        } else {
            callbackGet.onGotDataListSuccess(findSubTypeList(typeListCache, parent));
        }
    }

    public void getShopList(final ProductType parent, final String keyword, final RepoCallback.GetList<Shop> callbackGet) {
        if (parent.getShops() == null) {
            getShopListApi.getShopList(parent, keyword, new GetShopListApi.Callback() {
                @Override
                public void onFinish(List<Shop> shops) {
                    parent.setShops(shops);
                    callbackGet.onGotDataListSuccess(shops);
                }

                @Override
                public void onError(int errType, String errMsg) {
                    callbackGet.onError(errType, errMsg);
                }
            });
        } else {
            callbackGet.onGotDataListSuccess(parent.getShops());    //TODO 是否需要更新ShopList
        }
    }

    public void getOrderList(User loginer, int everyPage, int currentPage, boolean isOver,final RepoCallback.GetList<Order> callbackGet){
     getOrderListApi.getOrderList(loginer, everyPage, currentPage, isOver, new GetOrderListApi.Callback() {
         @Override
         public void onFinish(List<Order> orders) {
             callbackGet.onGotDataListSuccess(orders);
         }

         @Override
         public void onError(int errType, String errMsg) {
             callbackGet.onError(errType,errMsg);
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
