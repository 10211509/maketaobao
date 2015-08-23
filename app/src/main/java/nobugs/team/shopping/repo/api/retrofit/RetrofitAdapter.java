package nobugs.team.shopping.repo.api.retrofit;

import android.util.Log;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Date;

import nobugs.team.shopping.constant.AppConfig;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Interceptor to add the auth key, and generate the hash for every request
 * The data is exposed in the constructor
 *
 * @author glomadrian
 */
public class RetrofitAdapter {
    private RestAdapter restAdapter;
    private ApiService baseApiService;


    public RetrofitAdapter() {
        initDefaultAdapter();
    }

    public void initDefaultAdapter() {
        this.restAdapter = new RestAdapter.Builder()
                .setEndpoint(AppConfig.URL.WEB_HOST)
                .build();
        this.baseApiService = restAdapter.create(ApiService.class);
    }

    public RestAdapter getRestAdapter() {
        return restAdapter;
    }

    public void setRestAdapter(RestAdapter restAdapter) {
        this.restAdapter = restAdapter;
    }

    public ApiService getBaseApiService() {
        return baseApiService;
    }

    public void setBaseApiService(ApiService baseApiService) {
        this.baseApiService = baseApiService;
    }
}
