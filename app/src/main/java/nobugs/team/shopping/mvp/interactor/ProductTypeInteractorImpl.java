package nobugs.team.shopping.mvp.interactor;

import java.util.List;

import nobugs.team.shopping.mvp.model.ProductType;
import nobugs.team.shopping.repo.RepoCallback;
import nobugs.team.shopping.repo.Repository;

/**
 * Created by Administrator on 2015/8/16 0016.
 */
public class ProductTypeInteractorImpl implements ProductTypeInteractor {


    @Override
    public void getMainProductType(final Callback callback) {
        Repository.getInstance().getMainTypeList(new RepoCallback.GetList<ProductType>() {
            @Override
            public void onGotDataListSuccess(List<ProductType> productTypes) {
                callback.onSuccess(productTypes);
            }

            @Override
            public void onError(int errType, String errMsg) {
                callback.onFailure();
            }
        });
    }

    @Override
    public void getSubProductType(ProductType parent, final Callback callback) {
        Repository.getInstance().getSubTypeList(parent, new RepoCallback.GetList<ProductType>() {
            @Override
            public void onGotDataListSuccess(List<ProductType> productTypes) {
                callback.onSuccess(productTypes);
            }

            @Override
            public void onError(int errType, String errMsg) {
                callback.onFailure();
            }
        });
    }
}
