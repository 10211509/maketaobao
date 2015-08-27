package nobugs.team.shopping.mvp.interactor;

import com.android.volley.VolleyError;

import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import nobugs.team.shopping.constant.AppConfig;
import nobugs.team.shopping.mvp.model.Order;
import nobugs.team.shopping.mvp.model.Product;
import nobugs.team.shopping.mvp.model.ProductType;
import nobugs.team.shopping.mvp.model.User;
import nobugs.team.shopping.repo.RepoCallback;
import nobugs.team.shopping.repo.Repository;
import team.nobugs.library.request.GsonGetRequest;
import team.nobugs.library.request.phraser.HttpObject;
import team.nobugs.library.request.utils.OkVolleyUtils;
import team.nobugs.library.request.utils.RequestFactory;

/**
 * Created by xiayong on 2015/8/22.
 */
public class OrderInteractorImpl implements OrderInteractor {

    @Override
    public void getOrdersInProgress(User user, int pageCount, int curPage, final Callback callback) {
        Repository.getInstance().getOrderList(user,pageCount,curPage, false ,new RepoCallback.GetList<Order>() {
            @Override
            public void onGotDataListSuccess(List<Order> orders) {
                callback.onOrderListSuccess(orders);
            }

            @Override
            public void onError(int errType, String errMsg) {
                callback.onFailure();
            }
        });
    }

    @Override
    public void getOrdersInFinished(User user, int pageCount, int curPage,final Callback callback) {
        Repository.getInstance().getOrderList(user,pageCount,curPage, true ,new RepoCallback.GetList<Order>() {
            @Override
            public void onGotDataListSuccess(List<Order> orders) {
                callback.onOrderListSuccess(orders);
            }

            @Override
            public void onError(int errType, String errMsg) {
                callback.onFailure();
            }
        });
    }

    @Override
    public void updateState(String orderid, Order.State newState) {

    }
}
