package nobugs.team.shopping.mvp.interactor;

import java.util.List;

import nobugs.team.shopping.repo.db.entity.ShopPo;

/**
 * Created by Administrator on 2015/8/16 0016.
 */
public interface ShopInterator {

    void getShops(int typeId, Callback callback);

    interface Callback {
        void onSuccess(List<ShopPo> shopPos);

        void onNetWorkError();

        void onFailure();
    }
}
