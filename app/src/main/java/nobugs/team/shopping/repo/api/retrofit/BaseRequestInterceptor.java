package nobugs.team.shopping.repo.api.retrofit;

import android.util.Log;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Date;

import retrofit.RequestInterceptor;

/**
 * Interceptor to add the auth key, and generate the hash for every request
 * The data is exposed in the constructor
 *
 * @author glomadrian
 */
public class BaseRequestInterceptor implements RequestInterceptor {

    private static final int SIGNUM = 1;
    private static final int BYTES = 1;

    private String publicKey;
    private String privateKey;

    public BaseRequestInterceptor(String publicKey, String privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    @Override
    public void intercept(RequestFacade request) {
        String timeStamp = generateTimestamp();
//        request.addEncodedQueryParam(ApiUtils.PARAM_TIMESTAMP, timeStamp);
//        request.addEncodedQueryParam(ApiUtils.PARAM_KEY, publicKey);
//        request.addEncodedQueryParam(ApiUtils.PARAM_HASH, generateMarvelHash(timeStamp, privateKey, publicKey));
    }

    private String generateTimestamp() {
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        return String.valueOf(timestamp.getTime());
    }


    private String generateMarvelHash(String timeStamp, String privateKey, String publicKey) {

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            String marvelHash = timeStamp + privateKey + publicKey;
            byte[] bytes = marvelHash.getBytes();
            return new BigInteger(SIGNUM, messageDigest.digest(bytes)).toString(BYTES);

        } catch (NoSuchAlgorithmException e) {
            Log.e("BaseRequestInterceptor", " Hash type not found");
            return "invalid";
        }
    }

}
