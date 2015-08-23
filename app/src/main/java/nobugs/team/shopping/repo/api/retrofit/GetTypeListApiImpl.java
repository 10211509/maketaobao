package nobugs.team.shopping.repo.api.retrofit;

import java.util.List;

import nobugs.team.shopping.mvp.model.ProductType;
import nobugs.team.shopping.repo.api.GetTypeListApi;
import nobugs.team.shopping.repo.api.model.TypeListResult;
import nobugs.team.shopping.repo.mapper.Mapper;
import nobugs.team.shopping.repo.mapper.TypeListMapper;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Administrator on 2015/8/23 0023.
 */
public class GetTypeListApiImpl extends BaseRetrofitHandler implements GetTypeListApi {

    private final Mapper mapper;

    public GetTypeListApiImpl() {
        super();
        this.mapper = new TypeListMapper();
    }

    @Override
    public List<ProductType> getTypeList() {
        return baseApiService.getTypeList();
    }

    @Override
    public void getTypeList(final Callback callback) {
        baseApiService.getTypeList(new retrofit.Callback<TypeListResult>() {
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