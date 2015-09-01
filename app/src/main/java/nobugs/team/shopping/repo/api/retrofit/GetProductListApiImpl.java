package nobugs.team.shopping.repo.api.retrofit;

import java.util.List;

import nobugs.team.shopping.mvp.model.Product;
import nobugs.team.shopping.repo.api.GetProductListApi;
import nobugs.team.shopping.repo.api.entity.ProductListResult;
import nobugs.team.shopping.repo.mapper.ProductListMapper;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Administrator on 2015/8/23 0023.
 */
public class GetProductListApiImpl extends BaseRetrofitHandler implements GetProductListApi {

    public GetProductListApiImpl(RetrofitAdapter adapter) {
        super(adapter);
        this.mapper = new ProductListMapper();
    }

//    @Override
//    public List<Product> getProductList(int shopId) {
//        return (List<Product>) mapper.map(getService().getProductList(shopId));
//    }

    @Override
    public void getProductList(int shopId, final Callback callback) {
        getService().getProductList(shopId, new retrofit.Callback<ProductListResult>() {
            @Override
            public void success(ProductListResult shopListResult, Response response) {
                callback.onFinish((List<Product>) mapper.map(shopListResult));
            }

            @Override
            public void failure(RetrofitError error) {
                callback.onError(getErrorType(error), error.getMessage());
            }
        });
    }
}
