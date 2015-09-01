package nobugs.team.shopping.repo.api.retrofit;

import nobugs.team.shopping.repo.api.UpdateOrderApi;
import nobugs.team.shopping.repo.api.entity.EmptyResult;
import nobugs.team.shopping.repo.entity.OrderPo;
import nobugs.team.shopping.repo.mapper.EmptyMapper;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by xiayong on 2015/8/26.
 */
public class UpdateOrderApiImpl extends BaseRetrofitHandler implements UpdateOrderApi {

    public UpdateOrderApiImpl(RetrofitAdapter adapter) {
        super(adapter);
        this.mapper = new EmptyMapper();
    }

    @Override
    public void updateOrder(OrderPo orderPo, final Callback callback) {
        getService().updateOrder(orderPo.getId(), orderPo.getState(), new retrofit.Callback<EmptyResult>() {
            @Override
            public void success(EmptyResult result, Response response) {
                Integer code = (Integer) mapper.map(result);
                if (code != null) {
                    if (code == 0){
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
