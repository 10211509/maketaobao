package nobugs.team.shopping.repo.api.retrofit;

import java.io.IOException;

import nobugs.team.shopping.constant.AppConfig;
import nobugs.team.shopping.constant.ErrorConsts;
import nobugs.team.shopping.repo.Repository;
import nobugs.team.shopping.repo.mapper.Mapper;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

/**
 * Created by Administrator on 2015/8/23 0023.
 */
public class BaseRetrofitHandler {
    private RetrofitAdapter adapter;
    protected Mapper mapper;

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
