package nobugs.team.shopping.repo.api.retrofit;

import java.util.List;

import nobugs.team.shopping.constant.AppConfig;
import nobugs.team.shopping.mvp.model.ProductType;
import nobugs.team.shopping.mvp.model.Shop;
import nobugs.team.shopping.repo.api.model.ShopListResult;
import nobugs.team.shopping.repo.api.model.TypeListResult;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Administrator on 2015/8/23 0023.
 */
public interface BaseApiService {
    @GET(AppConfig.URL.GET_TYPE_LIST)
    void getTypeList(Callback<TypeListResult> callback);

    @GET(AppConfig.URL.GET_TYPE_LIST)
    List<ProductType> getTypeList();

    @GET(AppConfig.URL.GET_SHOP_LIST)
    void getShopList(@Query("typeid") String typeId, @Query("keyword") String keyword, Callback<ShopListResult> callback);

    @GET(AppConfig.URL.GET_SHOP_LIST)
    List<Shop> getShopList(@Query("typeid") String typeId, @Query("keyword") String keyword);
}
