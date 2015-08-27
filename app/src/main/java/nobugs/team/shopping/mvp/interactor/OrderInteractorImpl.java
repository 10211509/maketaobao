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
    /*@Override
    public void getOrdersInProgress(User user, int pageCount, int curPage,final Callback callback) {
        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("everyPage", String.valueOf(pageCount)));
        params.add(new BasicNameValuePair("currentPage", String.valueOf(curPage)));
        params.add(new BasicNameValuePair("isOver", String.valueOf(1)));
        String id = user.isSeller()?"saleid" : "buyid";
        params.add(new BasicNameValuePair(id, String.valueOf(user.getId())));

        final GsonGetRequest<HttpObject> getRequest = RequestFactory.createGetRequest(AppConfig.URL.WEB_HOST+AppConfig.URL.LOGIN, params ,new com.android.volley.Response.Listener<HttpObject>() {
            @Override
            public void onResponse(HttpObject httpObjectObject) {
                // Deal with the DummyObject here
                if (httpObjectObject.isSuccessful()|| true) {
                    //TODO
                    callback.onOrderListSuccess(getFakeDate());
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
    }*/

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
    public void getOrdersInFinished(User user, int pageCount, int curPage, Callback callback) {

    }

    @Override
    public void updateState(String orderid, Order.State newState) {

    }


    /*public List<Order> getFakeDate() {
        List<Order> fakeDate = new ArrayList<>();
        for(int i=0;i<10;i++){
            Product product = new Product(i,"苹果"+i,null);
            fakeDate.add(new Order(1,product,i+2,5.0,null,null,"2015/8/23"));
        }
        return fakeDate;
    }*/
}
