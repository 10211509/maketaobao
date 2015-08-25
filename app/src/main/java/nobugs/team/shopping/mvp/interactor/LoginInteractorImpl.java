package nobugs.team.shopping.mvp.interactor;

import com.android.volley.VolleyError;

import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import nobugs.team.shopping.constant.AppConfig;
import nobugs.team.shopping.mvp.model.User;
import nobugs.team.shopping.repo.RepoCallback;
import nobugs.team.shopping.repo.Repository;
import nobugs.team.shopping.utils.CommonTools;
import team.nobugs.library.request.GsonGetRequest;
import team.nobugs.library.request.phraser.HttpObject;
import team.nobugs.library.request.utils.OkVolleyUtils;
import team.nobugs.library.request.utils.RequestFactory;

public class LoginInteractorImpl implements LoginInteractor {


    @Override
    public void login(final String username, final String password, final Callback callback) {
        Repository.getInstance().login(username, password, new RepoCallback.Get<User>(){
            @Override
            public void onGotDataSuccess(User user) {
                if (user != null) {
                    callback.onSuccess();
                } else {
                    callback.onFailure();
                }
            }
            @Override
            public void onError(int errType, String errMsg) {
                callback.onNetWorkError();
            }
        });
    }

}
