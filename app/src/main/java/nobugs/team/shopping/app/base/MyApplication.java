package nobugs.team.shopping.app.base;

import android.app.Application;

import com.hisun.phone.core.voice.util.Log4Util;

import nobugs.team.shopping.utils.CCPHelper;
import nobugs.team.shopping.utils.CommonTools;
import team.nobugs.library.request.utils.OkVolleyUtils;


public class MyApplication extends Application {

    private static MyApplication instance;//keep one instance of the Application
    @Override
    public void onCreate() {
        super.onCreate();
        OkVolleyUtils.init(this);
        instance = this;
    }
    public static MyApplication getInstance() {
        return instance;
    }
    public String getCCPUserAgent(){
        String ua = "Android;"
                + CommonTools.getOSVersion() + ";"
                + com.hisun.phone.core.voice.Build.SDK_VERSION + ";"
                + com.hisun.phone.core.voice.Build.LIBVERSION.FULL_VERSION + ";"
                + CommonTools.getVendor() + "-" +CommonTools.getDevice() + ";";

        ua = ua + CommonTools.getDevicNO(this)  + ";" + System.currentTimeMillis() + ";";

        Log4Util.d(CCPHelper.DEMO_TAG, "User_Agent : " + ua);
        return ua;
    }

}
