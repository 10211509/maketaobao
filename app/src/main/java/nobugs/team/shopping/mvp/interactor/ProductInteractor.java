package nobugs.team.shopping.mvp.interactor;

import java.util.List;

import nobugs.team.shopping.mvp.model.Product;
import nobugs.team.shopping.mvp.model.ProductType;
import nobugs.team.shopping.mvp.model.Shop;

/**
 * Created by xiayong on 2015/8/31.
 */
public interface ProductInteractor  {
    void getProducts(String shopid,Callback callback);

    void getProductUnit(TypeCallback callback);

    interface Callback {
        void onSuccess(List<Product> products);

        void onNetWorkError();

        void onFailure();
    }

    interface TypeCallback{
        void onTypeSuccess(List<String> productUnits);

        void onNetWorkError();

        void onFailure();
    }
}
