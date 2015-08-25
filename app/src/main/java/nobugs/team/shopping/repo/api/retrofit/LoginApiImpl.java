package nobugs.team.shopping.repo.api.retrofit;

import java.util.List;

import nobugs.team.shopping.mvp.model.ProductType;
import nobugs.team.shopping.mvp.model.Shop;
import nobugs.team.shopping.mvp.model.User;
import nobugs.team.shopping.repo.api.GetShopListApi;
import nobugs.team.shopping.repo.api.LoginApi;
import nobugs.team.shopping.repo.api.model.LoginResult;
import nobugs.team.shopping.repo.api.model.ShopListResult;
import nobugs.team.shopping.repo.mapper.LoginMapper;
import nobugs.team.shopping.repo.mapper.Mapper;
import nobugs.team.shopping.repo.mapper.ShopListMapper;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Administrator on 2015/8/23 0023.
 */
public class LoginApiImpl extends BaseRetrofitHandler implements LoginApi {

    public LoginApiImpl(RetrofitAdapter adapter) {
        super(adapter);
        this.mapper = new LoginMapper();
    }

    @Override
    public User login(String userName, String password) {
        return (User) mapper.map(getService().login(userName, password));
    }

    @Override
    public void login(String userName, String password, final Callback callback) {
        getService().login(userName, password, new retrofit.Callback<LoginResult>() {
            @Override
            public void success(LoginResult loginResult, Response response) {
                callback.onFinish((User) mapper.map(loginResult));
            }

            @Override
            public void failure(RetrofitError error) {
                callback.onError(getErrorType(error), error.getMessage());
            }
        });
    }
}
