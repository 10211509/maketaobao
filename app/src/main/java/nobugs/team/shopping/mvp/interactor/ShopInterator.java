package nobugs.team.shopping.mvp.interactor;

import java.util.List;

import nobugs.team.shopping.db.entity.ProductType;
import nobugs.team.shopping.db.entity.Shop;

/**
 * Created by Administrator on 2015/8/16 0016.
 */
public interface ShopInterator {

    void getShops(int typeId, Callback callback);

    interface Callback {
        void onSuccess(List<Shop> shops);

        void onNetWorkError();

        void onFailure();
    }
}
