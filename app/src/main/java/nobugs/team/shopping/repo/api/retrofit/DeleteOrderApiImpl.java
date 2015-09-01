package nobugs.team.shopping.repo.api.retrofit;

import nobugs.team.shopping.repo.api.DeleteOrderApi;
import nobugs.team.shopping.repo.api.entity.EmptyResult;
import nobugs.team.shopping.repo.mapper.EmptyMapper;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by xiayong on 2015/8/26.
 */
public class DeleteOrderApiImpl extends BaseRetrofitHandler implements DeleteOrderApi {

    public DeleteOrderApiImpl(RetrofitAdapter adapter) {
        super(adapter);
        this.mapper = new EmptyMapper();
    }

    @Override
    public void deleteOrder(int orderId, final Callback callback) {
        getService().delOrder(orderId, new retrofit.Callback<EmptyResult>() {
            @Override
            public void success(EmptyResult result, Response response) {
                Integer code = (Integer) mapper.map(result);
                if (code != null) {
                    if (code == 0) {
                        callback.onFinish();
                    } else {
                        callback.onError(code, result.getCodeMsg());
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                callback.onError(getErrorType(error), error.getMessage());
            }
        });
    }
}
