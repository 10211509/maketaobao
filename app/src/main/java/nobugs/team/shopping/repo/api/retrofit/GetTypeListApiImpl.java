package nobugs.team.shopping.repo.api.retrofit;

import java.util.List;

import nobugs.team.shopping.mvp.model.ProductType;
import nobugs.team.shopping.repo.api.GetTypeListApi;
import nobugs.team.shopping.repo.api.entity.TypeListResult;
import nobugs.team.shopping.repo.mapper.TypeListMapper;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Administrator on 2015/8/23 0023.
 */
public class GetTypeListApiImpl extends BaseRetrofitHandler implements GetTypeListApi {

    public GetTypeListApiImpl(RetrofitAdapter adapter) {
        super(adapter);
        this.mapper = new TypeListMapper();
    }

    @Override
    public List<ProductType> getTypeList() {
        return (List<ProductType>) mapper.map(getService().getTypeList());
    }

    @Override
    public void getTypeList(final Callback callback) {
        getService().getTypeList(new retrofit.Callback<TypeListResult>() {
            @Override
            public void success(TypeListResult typeListResult, Response response) {
                callback.onFinish((List<ProductType>) mapper.map(typeListResult));
            }

            @Override
            public void failure(RetrofitError error) {
                callback.onError(getErrorType(error), error.getMessage());
            }
        });

    }
}
