package nobugs.team.shopping.mvp.interactor;

import com.android.volley.VolleyError;

import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import nobugs.team.shopping.constant.AppConfig;
import nobugs.team.shopping.mvp.interfaces.OnLoginFinishedListener;
import team.nobugs.library.request.GsonGetRequest;
import team.nobugs.library.request.phraser.HttpObject;
import team.nobugs.library.request.utils.OkVolleyUtils;
import team.nobugs.library.request.utils.RequestFactory;

public class LoginInteractorImpl implements LoginInteractor {

    @Override
    public void login(final String username, final String password, final OnLoginFinishedListener listener) {

        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("username",username));
        params.add(new BasicNameValuePair("password", password));
       final GsonGetRequest<HttpObject> getRequest = RequestFactory.createGetRequest(AppConfig.URL.LOGIN,new com.android.volley.Response.Listener<HttpObject>()
       {
           @Override
           public void onResponse(HttpObject httpObjectObject)
           {
               // Deal with the DummyObject here
              if( httpObjectObject.isSuccessful()){
                  listener.onSuccess();
              }else{
                  listener.onFailure();
              }

           }
       },new com.android.volley.Response.ErrorListener()
       {
           @Override
           public void onErrorResponse(VolleyError error)
           {
               // Deal with the error here
               listener.onNetWorkError();

           }
       });
        OkVolleyUtils.addRequest(getRequest,"login");
    }
}
