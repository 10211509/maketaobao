package nobugs.team.shopping.repo.api.retrofit;

import java.util.List;

import nobugs.team.shopping.mvp.model.ProductType;
import nobugs.team.shopping.mvp.model.Shop;
import nobugs.team.shopping.repo.api.GetShopListApi;
import nobugs.team.shopping.repo.api.entity.ShopListResult;
import nobugs.team.shopping.repo.mapper.ShopListMapper;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Administrator on 2015/8/23 0023.
 */
public class GetShopListApiImpl extends BaseRetrofitHandler implements GetShopListApi {

    public GetShopListApiImpl(RetrofitAdapter adapter) {
        super(adapter);
        this.mapper = new ShopListMapper();
    }

    @Override
    public List<Shop> getShopList(ProductType type, String keyword) {
        return (List<Shop>) mapper.map(getService().getShopList(type.getId() + "", keyword));
    }

    @Override
    public void getShopList(ProductType type, String keyword, final Callback callback) {
        getService().getShopList(type.getId() + "", keyword, new retrofit.Callback<ShopListResult>() {
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
