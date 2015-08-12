package nobugs.team.shopping.app.base;

import android.app.Application;

import team.nobugs.library.request.utils.OkVolleyUtils;


public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        OkVolleyUtils.init(this);
    }
}
