package nobugs.team.shopping.mvp.view;

import java.util.List;

import nobugs.team.shopping.db.entity.ProductType;
import nobugs.team.shopping.db.entity.Shop;

/**
 * Autor: wangyf on 2015/8/15 0015 20:56
 * Email: zgtjwyftc@gmail.com
 * Description:
 */
public interface MainPageView extends IView {

    void showAndRunAdsBanner(List<String> imgUrls, int period);

    void showEmptyBanner();

    void showErrorBanner();

    void stopRunAdsBanner();

    void showMainProductTypes(List<ProductType> productTypes);

    void showEmptyMainProductType();

    void showSubProductTypes(List<ProductType> productTypes);

    void showEmptySubProductType();

    void showShops(List<Shop> productTypes);

    void showEmptyShop();
}
