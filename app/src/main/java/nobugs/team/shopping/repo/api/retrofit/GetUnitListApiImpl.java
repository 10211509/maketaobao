package nobugs.team.shopping.repo.api.retrofit;

import java.util.List;

import nobugs.team.shopping.mvp.model.ProductType;
import nobugs.team.shopping.repo.api.GetShopListApi;
import nobugs.team.shopping.repo.api.GetUnitListApi;
import nobugs.team.shopping.repo.api.entity.TypeListResult;
import nobugs.team.shopping.repo.api.entity.UnitListResult;
import nobugs.team.shopping.repo.mapper.UnitListMapper;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Administrator on 2015/8/23 0023.
 */
public class GetUnitListApiImpl extends BaseRetrofitHandler implements GetUnitListApi {

    public GetUnitListApiImpl(RetrofitAdapter adapter) {
        super(adapter);
        this.mapper = new UnitListMapper();
    }

    @Override
    public List<String> getUnitList() {
        return (List<String>) mapper.map(getService().getUnitList());
    }

    @Override
    public void getUnitList(final Callback callback) {
        getService().getUnitList(new retrofit.Callback<UnitListResult>() {
            @Override
            public void success(UnitListResult unitListResult, Response response) {
                callback.onFinish((List<String>) mapper.map(unitListResult));
            }

            @Override
            public void failure(RetrofitError error) {
                callback.onError(getErrorType(error), error.getMessage());
            }
        });
    }
}
