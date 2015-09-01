package nobugs.team.shopping.repo.api;

import java.util.List;

import nobugs.team.shopping.mvp.model.Product;

/**
 * Created by xiayong on 2015/8/30.
 */
public interface GetProductListApi {
    void getProductList(int shopId, Callback callback);

    interface Callback {

        void onFinish(List<Product> shops);

        void onError(int errType, String errMsg);
    }
}
