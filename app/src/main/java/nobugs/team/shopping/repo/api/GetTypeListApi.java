package nobugs.team.shopping.repo.api;

import java.util.List;

import nobugs.team.shopping.mvp.model.ProductType;

/**
 * Created by Administrator on 2015/8/23 0023.
 */
public interface GetTypeListApi {
    List<ProductType> getTypeList();

    void getTypeList(Callback callback);

    interface Callback {

        void onFinish(List<ProductType> productTypes);

        void onError(int errType, String errMsg);
    }
}
