package nobugs.team.shopping.repo.api;

import java.util.List;

import nobugs.team.shopping.mvp.model.ProductType;
import nobugs.team.shopping.mvp.model.Shop;

/**
 * Created by Administrator on 2015/8/23 0023.
 */
public interface GetShopListApi {
    List<Shop> getShopList();

    void getShopList(Callback callback);

    interface Callback {

        void onFinish(List<Shop> shops);

        void onError(int errType, String errMsg);
    }
}
