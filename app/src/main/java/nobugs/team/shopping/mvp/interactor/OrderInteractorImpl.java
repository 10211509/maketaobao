package nobugs.team.shopping.mvp.interactor;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import nobugs.team.shopping.constant.AppConfig;
import nobugs.team.shopping.db.entity.Order;
import nobugs.team.shopping.db.entity.Shop;
import nobugs.team.shopping.db.entity.User;
import team.nobugs.library.request.GsonGetRequest;
import team.nobugs.library.request.phraser.HttpObject;
import team.nobugs.library.request.utils.OkVolleyUtils;
import team.nobugs.library.request.utils.RequestFactory;

/**
 * Created by xiayong on 2015/8/22.
 */
public class OrderInteractorImpl implements OrderInteractor {
    @Override
    public void getOrdersInProgress(User user, int pageCount, int curPage,final Callback callback) {
        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("everyPage", String.valueOf(pageCount)));
        params.add(new BasicNameValuePair("currentPage", String.valueOf(curPage)));
        params.add(new BasicNameValuePair("isOver", String.valueOf(1)));
        String id = user.isSeller()?"saleid" : "buyid";
        params.add(new BasicNameValuePair(id, user.getId().toString());

        final GsonGetRequest<HttpObject> getRequest = RequestFactory.createGetRequest(AppConfig.URL.LOGIN, params ,new com.android.volley.Response.Listener<HttpObject>() {
            @Override
            public void onResponse(HttpObject httpObjectObject) {
                // Deal with the DummyObject here
                if (httpObjectObject.isSuccessful()) {
                    callback.onOrderListSuccess();
                } else {
                    callback.onFailure();
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Deal with the error here
                callback.onNetWorkError();

            }
        });
        OkVolleyUtils.addRequest(getRequest, "login");
    }

    @Override
    public void getOrdersInFinished(User user, int pageCount, int curPage, Callback callback) {

    }
}
