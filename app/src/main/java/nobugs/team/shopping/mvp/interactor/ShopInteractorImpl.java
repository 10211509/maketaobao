package nobugs.team.shopping.mvp.interactor;

import java.util.List;

import nobugs.team.shopping.mvp.model.ProductType;
import nobugs.team.shopping.mvp.model.Shop;
import nobugs.team.shopping.repo.RepoCallback;
import nobugs.team.shopping.repo.Repository;

/**
 * Created by Administrator on 2015/8/16 0016.
 */
public class ShopInteractorImpl implements ShopInteractor {


    @Override
    public void getShops(ProductType type, final Callback callback) {
        Repository.getInstance().getShopList(type, null, new RepoCallback.GetList<Shop>() {
            @Override
            public void onGotDataListSuccess(List<Shop> shops) {
                callback.onSuccess(shops);
            }
            @Override
            public void onError(int errType, String errMsg) {
                callback.onFailure();
            }
        });
//        callback.onSuccess(findShopByTypeId(typeId));
    }

    @Override
    public void getProducts(String shopid,final Callback callback) {

    }
}
