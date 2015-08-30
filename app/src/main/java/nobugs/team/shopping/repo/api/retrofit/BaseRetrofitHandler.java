package nobugs.team.shopping.repo.api.retrofit;

import java.io.IOException;

import nobugs.team.shopping.constant.ErrorConsts;
import nobugs.team.shopping.repo.mapper.Mapper;
import retrofit.RetrofitError;

/**
 * Created by Administrator on 2015/8/23 0023.
 */
public class BaseRetrofitHandler {
    private RetrofitAdapter adapter;
    protected Mapper mapper;
//    protected CacheType cacheType = CacheType.NONE;
//    protected boolean cleanCache = false;

    /*public enum CacheType{
        MEMORY_ONLY,
        MEMORY_DISK,
        DISK_ONLY,
        NONE,
    }*/
    public BaseRetrofitHandler(RetrofitAdapter adapter) {
        this.adapter = adapter;
    }


    public ApiService getService(){
        return adapter.getBaseApiService();
    }

    protected int getErrorType(RetrofitError error) {
        switch (error.getKind()) {
            /** An {@link IOException} occurred while communicating to the server. */
            case NETWORK:
                return ErrorConsts.Http.NETWORK;
            /** An exception was thrown while (de)serializing a body. */
            case CONVERSION:
                return ErrorConsts.Http.DECODER;
            /** A non-200 HTTP status code was received from the server. */
            case HTTP:
                return ErrorConsts.Http.HTTPERROR;
            /**
             * An internal error occurred while attempting to execute a request. It is best practice to
             * re-throw this exception so your application crashes.
             */
            case UNEXPECTED:
                return ErrorConsts.Http.UNEXPECTED;
        }
        return ErrorConsts.Http.UNKNOWN;
    }
}
