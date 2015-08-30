package nobugs.team.shopping.mvp.interactor;

import java.util.List;

import nobugs.team.shopping.mvp.model.ProductType;

/**
 * Created by Administrator on 2015/8/16 0016.
 */
public interface ProductTypeInteractor {

    void getMainProductType(Callback callback);

    void getSubProductType(ProductType parent, Callback callback);

    interface Callback {
        void onSuccess(List<ProductType> types);

        void onNetWorkError();

        void onFailure();
    }
}
