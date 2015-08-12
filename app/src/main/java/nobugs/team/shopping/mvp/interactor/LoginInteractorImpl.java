package nobugs.team.shopping.mvp.interactor;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;

import com.android.volley.VolleyError;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import nobugs.team.shopping.app.base.AppConfig;
import nobugs.team.shopping.mvp.interfaces.OnLoginFinishedListener;
import nobugs.team.shopping.utils.OkHttpUtil;
import team.nobugs.library.request.GsonGetRequest;
import team.nobugs.library.request.phraser.HttpObject;
import team.nobugs.library.request.utils.OkVolleyUtils;
import team.nobugs.library.request.utils.RequestFactory;

public class LoginInteractorImpl implements LoginInteractor {

    @Override
    public void login(final String username, final String password, final OnLoginFinishedListener listener) {

        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("name",username));
        params.add(new BasicNameValuePair("password",password));
       final GsonGetRequest<HttpObject> getRequest = RequestFactory.createGetRequest("http://suyun.58.com/api/guest/order/detail",new com.android.volley.Response.Listener<HttpObject>()
       {
           @Override
           public void onResponse(HttpObject httpObjectObject)
           {
               // Deal with the DummyObject here

           }
       },new com.android.volley.Response.ErrorListener()
       {
           @Override
           public void onErrorResponse(VolleyError error)
           {
               // Deal with the error here

           }
       });
        OkVolleyUtils.addRequest(getRequest,"hhhh");
       /* OkHttpUtil.get(AppConfig.URL.LOGIN, params, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Boolean b = Thread.currentThread() == Looper.getMainLooper().getThread();
                listener.onNetWorkError();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                Boolean b = Thread.currentThread() == Looper.getMainLooper().getThread();

                if(response.isSuccessful()){
                    //TODO 将数据插入数据库
                    listener.onSuccess();
                }else{
                    listener.onFailure();
                }
            }
        });*/
    }
}
