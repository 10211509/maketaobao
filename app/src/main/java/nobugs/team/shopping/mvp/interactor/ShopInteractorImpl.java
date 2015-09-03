package nobugs.team.shopping.mvp.interactor;

import java.util.ArrayList;
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
    public void getShops(ProductType type, final String keyword, final Callback callback) {
        Repository.getInstance().getShopList(type, null, new RepoCallback.GetList<Shop>() {
            @Override
            public void onGotDataListSuccess(List<Shop> shops) {
                if (keyword != null) {
                    shops = doFilter(shops, keyword);
                }
                callback.onSuccess(shops);
            }

            @Override
            public void onError(int errType, String errMsg) {
                callback.onFailure();
            }
        });
//        callback.onSuccess(findShopByTypeId(typeId));
    }

    private List<Shop> doFilter(List<Shop> shops, String keyword) {
        List<Shop> result = new ArrayList<>();
        for (Shop shop : shops) {
            if (shop != null && shop.getName().contains(keyword)) {
                result.add(shop);
            }
        }
        return result;
    }

}
