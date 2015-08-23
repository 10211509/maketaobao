package nobugs.team.shopping.mvp.view;

import android.support.annotation.NonNull;

import java.util.List;

import nobugs.team.shopping.mvp.model.ProductType;
import nobugs.team.shopping.mvp.model.Shop;
import nobugs.team.shopping.mvp.model.User;

/**
 * Autor: wangyf on 2015/8/15 0015 20:56
 * Email: zgtjwyftc@gmail.com
 * Description:
 */
public interface MainShopView extends IView {

    void showAndRunAdsBanner(List<String> imgUrls, int period);

    void showEmptyBanner();

    void showErrorBanner();

    void stopRunAdsBanner();

    void showMainProductTypes(List<ProductType> productTypes);

    void showEmptyMainProductType();

    void showSubProductTypes(List<ProductType> productTypes);

    void showEmptySubProductType();

    void showShops(List<Shop> shops);

    void showEmptyShop();

    void navigateCallOut(@NonNull User user);
}
