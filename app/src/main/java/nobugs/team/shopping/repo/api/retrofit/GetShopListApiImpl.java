package nobugs.team.shopping.repo.api.retrofit;

import java.util.List;

import nobugs.team.shopping.mvp.model.ProductType;
import nobugs.team.shopping.mvp.model.Shop;
import nobugs.team.shopping.repo.api.GetShopListApi;
import nobugs.team.shopping.repo.api.GetTypeListApi;
import nobugs.team.shopping.repo.api.model.ShopListResult;
import nobugs.team.shopping.repo.api.model.TypeListResult;
import nobugs.team.shopping.repo.mapper.Mapper;
import nobugs.team.shopping.repo.mapper.ShopListMapper;
import nobugs.team.shopping.repo.mapper.TypeListMapper;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Administrator on 2015/8/23 0023.
 */
public class GetShopListApiImpl extends BaseRetrofitHandler implements GetShopListApi {

    private final Mapper mapper;

    public GetShopListApiImpl() {
        super();
        this.mapper = new ShopListMapper();
    }

    @Override
    public List<Shop> getShopList(ProductType type, String keyword) {
        return baseApiService.getShopList(type.getId() + "", keyword);
    }

    @Override
    public void getShopList(ProductType type, String keyword, final Callback callback) {
        baseApiService.getShopList(type.getId() + "", keyword, new retrofit.Callback<ShopListResult>() {
            @Override
            public void success(ShopListResult shopListResult, Response response) {
                callback.onFinish((List<Shop>) mapper.map(shopListResult));
            }

            @Override
            public void failure(RetrofitError error) {
                callback.onError(getErrorType(error), error.getMessage());
            }
        });
    }
}
