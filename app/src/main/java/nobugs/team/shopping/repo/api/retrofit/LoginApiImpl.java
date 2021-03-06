package nobugs.team.shopping.repo.api.retrofit;

import nobugs.team.shopping.mvp.model.User;
import nobugs.team.shopping.repo.api.LoginApi;
import nobugs.team.shopping.repo.db.helper.DaoHelper;
import nobugs.team.shopping.repo.db.helper.UserHelper;
import nobugs.team.shopping.repo.api.entity.LoginResult;
import nobugs.team.shopping.repo.mapper.LoginMapper;
import nobugs.team.shopping.repo.entity.UserPo;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Administrator on 2015/8/23 0023.
 */
public class LoginApiImpl extends BaseRetrofitHandler implements LoginApi {

    private User userCache;//用户缓存
    private DaoHelper<UserPo> daoHelper;
    public LoginApiImpl(RetrofitAdapter adapter) {
        super(adapter);
        this.mapper = new LoginMapper();
        this.daoHelper = new UserHelper();
    }

    @Override
    public User login(String userName, String password) {
        return (User) mapper.map(getService().login(userName, password));
    }

    @Override
    public User getUser(){
        if(userCache != null){
            return userCache;
        }
        return ((LoginMapper)mapper).modelMapper.toModel(((UserHelper)daoHelper).selectUser());
    }
    @Override
    public void login(String userName, String password, final Callback callback) {
        getService().login(userName, password, new retrofit.Callback<LoginResult>() {
            @Override
            public void success(LoginResult loginResult, Response response) {
                //cache the info
                if (loginResult.getData() != null && loginResult.getData().size() > 0){
                    loginResult.getData().get(0);
                    userCache = (User) mapper.map(loginResult);
                    ((UserHelper)daoHelper).clearAllAndInsert(loginResult.getData().get(0));
//                List<UserPo> userPos = daoHelper.loadAll();
                    callback.onFinish(userCache);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                callback.onError(getErrorType(error), error.getMessage());
            }
        });
    }
}
