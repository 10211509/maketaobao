package nobugs.team.shopping.mvp.interactor;

import java.util.List;

import nobugs.team.shopping.mvp.model.Product;
import nobugs.team.shopping.repo.RepoCallback;
import nobugs.team.shopping.repo.Repository;

/**
 * Created by xiayong on 2015/8/31.
 */
public class ProductInteractorImpl implements ProductInteractor {
    @Override
    public void getProducts(String shopId, final Callback callback) {
        Repository.getInstance().getProductList(Integer.parseInt(shopId),new RepoCallback.GetList<Product>(){

            @Override
            public void onGotDataListSuccess(List<Product> products) {
                callback.onSuccess(products);
            }

            @Override
            public void onError(int errType, String errMsg) {

            }
        });
    }
}
