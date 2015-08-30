package nobugs.team.shopping.repo.api.retrofit;

import java.util.List;

import nobugs.team.shopping.mvp.model.ProductType;
import nobugs.team.shopping.repo.api.GetTypeListApi;
import nobugs.team.shopping.repo.api.model.TypeListResult;
import nobugs.team.shopping.repo.mapper.TypeListMapper;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Administrator on 2015/8/23 0023.
 */
public class GetTypeListApiImpl extends BaseRetrofitHandler implements GetTypeListApi {

   /* public static class Builder {
        private RetrofitAdapter adapter;
        private TypeListMapper modelMapper;
        private CacheType cacheType;
        private boolean cleanCache;

        public Builder(RetrofitAdapter adapter) {
            this.adapter = adapter;
            this.modelMapper = new TypeListMapper();
        }

        public Builder cacheType(CacheType cacheType) {
            this.cacheType = cacheType;
            return this;
        }

        public Builder cleanCache(boolean cleanCache) {
            this.cleanCache = cleanCache;
            return this;
        }

        public GetTypeListApiImpl build() {
            return new GetTypeListApiImpl(this);
        }
    }*/

    private List<ProductType> productTypeList;
//    private DaoHelper<UserPo> daoHelper;
   /* public GetTypeListApiImpl(Builder builder) {
        super(builder.adapter);
        this.modelMapper = builder.modelMapper;
        this.cacheType = builder.cacheType;
        this.cleanCache = builder.cleanCache;
    }*/

    public GetTypeListApiImpl(RetrofitAdapter adapter) {
        super(adapter);
        this.mapper = new TypeListMapper();
    }

    @Override
    public List<ProductType> getTypeList() {
        return (List<ProductType>) mapper.map(getService().getTypeList());
    }

    @Override
    public void getTypeList(final Callback callback) {
       /* switch (cacheType) {
            case MEMORY_ONLY:
                if (productTypeList != null)
                    callback.onFinish(productTypeList);
                break;
            case MEMORY_DISK:

                break;
            case DISK_ONLY:
                break;

        }*/
        getService().getTypeList(new retrofit.Callback<TypeListResult>() {
            @Override
            public void success(TypeListResult typeListResult, Response response) {
                callback.onFinish((List<ProductType>) mapper.map(typeListResult));
            }

            @Override
            public void failure(RetrofitError error) {
                callback.onError(getErrorType(error), error.getMessage());
            }
        });

    }
}
