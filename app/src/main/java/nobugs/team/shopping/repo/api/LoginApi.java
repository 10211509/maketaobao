package nobugs.team.shopping.repo.api;

import java.util.List;

import nobugs.team.shopping.mvp.model.ProductType;
import nobugs.team.shopping.mvp.model.User;

/**
 * Created by Administrator on 2015/8/23 0023.
 */
public interface LoginApi {
    User login(String userName, String password);

    void login(String userName, String password, Callback callback);

    interface Callback {

        void onFinish(User user);

        void onError(int errType, String errMsg);
    }
}
