package nobugs.team.shopping.mvp.interactor;

import nobugs.team.shopping.mvp.model.User;
import nobugs.team.shopping.repo.RepoCallback;
import nobugs.team.shopping.repo.Repository;

public class LoginInteractorImpl implements LoginInteractor {

    @Override
    public void login(final String username, final String password, final Callback callback) {
        Repository.getInstance().login(username, password, new RepoCallback.Get<User>(){
            @Override
            public void onGotDataSuccess(User user) {
                if (user != null) {
                    callback.onSuccess(user);
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
