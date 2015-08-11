package nobugs.team.shopping.mvp.interactor;

import android.os.Handler;
import android.text.TextUtils;

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

public class LoginInteractorImpl implements LoginInteractor {

    @Override
    public void login(final String username, final String password, final OnLoginFinishedListener listener) {
        // Mock login. I'm creating a handler to delay the answer a couple of seconds
        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("name",username));
        params.add(new BasicNameValuePair("password",password));
        OkHttpUtil.get(AppConfig.URL.LOGIN, params, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                listener.onNetWorkError();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if(response.isSuccessful()){
                    listener.onSuccess();
                }else{
                    listener.onFailure();
                }
            }
        });
    }
}
