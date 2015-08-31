package nobugs.team.shopping.mvp.interactor;

import java.util.List;

import nobugs.team.shopping.mvp.model.ProductType;
import nobugs.team.shopping.mvp.model.Shop;

/**
 * Created by Administrator on 2015/8/16 0016.
 */
public interface ShopInteractor {

    void getShops(ProductType productType, Callback callback);

    interface Callback {
        void onSuccess(List<Shop> shops);

        void onNetWorkError();

        void onFailure();
    }
}
