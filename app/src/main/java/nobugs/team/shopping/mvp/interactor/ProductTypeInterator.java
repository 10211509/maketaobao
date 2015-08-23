package nobugs.team.shopping.mvp.interactor;

import java.util.List;

import nobugs.team.shopping.repo.db.entity.ProductTypePo;

/**
 * Created by Administrator on 2015/8/16 0016.
 */
public interface ProductTypeInterator {

    void getMainProductType(Callback callback);

    void getSubProductType(int parentId, Callback callback);

    interface Callback {
        void onSuccess(List<ProductTypePo> types);

        void onNetWorkError();

        void onFailure();
    }
}
