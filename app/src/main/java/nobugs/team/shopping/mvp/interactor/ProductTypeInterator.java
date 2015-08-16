package nobugs.team.shopping.mvp.interactor;

import java.util.List;

import nobugs.team.shopping.db.entity.ProductType;

/**
 * Created by Administrator on 2015/8/16 0016.
 */
public interface ProductTypeInterator {

    void getMainProductType(Callback callback);

    void getSubProductType(int parentId, Callback callback);

    interface Callback {
        void onSuccess(List<ProductType> types);

        void onNetWorkError();

        void onFailure();
    }
}
