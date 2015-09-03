package nobugs.team.shopping.repo.api.retrofit;

import java.util.Map;

import nobugs.team.shopping.constant.AppConfig;
import nobugs.team.shopping.repo.api.entity.EmptyResult;
import nobugs.team.shopping.repo.api.entity.LoginResult;
import nobugs.team.shopping.repo.api.entity.OrderListResult;
import nobugs.team.shopping.repo.api.entity.ProductListResult;
import nobugs.team.shopping.repo.api.entity.ShopListResult;
import nobugs.team.shopping.repo.api.entity.TypeListResult;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;
import retrofit.http.QueryMap;

/**
 * Created by Administrator on 2015/8/23 0023.
 */
public interface ApiService {
    @GET(AppConfig.URL.LOGIN)
    void login(@Query("username") String userName, @Query("password") String password, Callback<LoginResult> callback);

    @GET(AppConfig.URL.LOGIN)
    LoginResult login(@Query("username") String userName, @Query("password") String password);

    @GET(AppConfig.URL.GET_TYPE_LIST)
    void getTypeList(Callback<TypeListResult> callback);

    @GET(AppConfig.URL.GET_TYPE_LIST)
    TypeListResult getTypeList();

    @GET(AppConfig.URL.GET_SHOP_LIST)
    void getShopList(@Query("typeid") int typeId, @Query("keyword") String keyword, Callback<ShopListResult> callback);

    @GET(AppConfig.URL.GET_SHOP_LIST)
    ShopListResult getShopList(@Query("typeid") int typeId, @Query("keyword") String keyword);

    @GET(AppConfig.URL.GET_PRODUCT_LIST)
    void getProductList(@Query("shopid") int shopId, Callback<ProductListResult> callback);

    @GET(AppConfig.URL.GET_PRODUCT_LIST)
    ProductListResult getProductList(@Query("shopid") int shopId);

    @GET(AppConfig.URL.GET_ORDER_LIST)
    void getOrderListBuyer(@Query("buyid") int buyerId, @Query("everyPage") int everyPage, @Query("currentPage") int currentPage, @Query("isOver") int isOver, Callback<OrderListResult> callback);

    @GET(AppConfig.URL.GET_ORDER_LIST)
    void getOrderListSeller(@Query("saleid") int sellerId, @Query("everyPage") int everyPage, @Query("currentPage") int currentPage, @Query("isOver") int isOver, Callback<OrderListResult> callback);

    @GET(AppConfig.URL.GET_ORDER_LIST)
    OrderListResult getOrderList(@Query("buyid") int buyerId, @Query("saleid") int sellerId, @Query("everyPage") int everyPage, @Query("currentPage") int currentPage, @Query("isOver") int isOver);

    @GET(AppConfig.URL.GET_ORDER)
    void getOrder(@Query("id") int orderId, Callback<OrderListResult> callback);

    @GET(AppConfig.URL.GET_ORDER)
    OrderListResult getOrder(@Query("id") int orderId);

    @GET(AppConfig.URL.ADD_ORDER)
    void addOrder(/*@QueryMap String orderId,*/ @QueryMap Map<String, String> order, Callback<OrderListResult> callback);

    @GET(AppConfig.URL.ADD_ORDER)
    OrderListResult addOrder(/*@QueryMap String orderId*/@QueryMap Map<String, String> order);

    @GET(AppConfig.URL.DEL_ORDER)
    void delOrder(@Query("id") int orderId, Callback<EmptyResult> callback);

    @GET(AppConfig.URL.DEL_ORDER)
    EmptyResult delOrder(@Query("id") int orderId);

    @GET(AppConfig.URL.UPDATE_ORDER)
    void updateOrder(@Query("id") int orderId, @Query("state") int orderStat, Callback<EmptyResult> callback);

    @GET(AppConfig.URL.UPDATE_ORDER)
    EmptyResult updateOrder(@Query("id") int orderId, @Query("state") int orderStat);

}
