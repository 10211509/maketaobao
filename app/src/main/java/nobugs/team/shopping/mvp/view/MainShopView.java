package nobugs.team.shopping.mvp.view;

import android.support.annotation.NonNull;

import java.util.List;

import nobugs.team.shopping.repo.db.entity.ProductTypePo;
import nobugs.team.shopping.repo.db.entity.ShopPo;
import nobugs.team.shopping.repo.db.entity.UserPo;

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

    void showMainProductTypes(List<ProductTypePo> productTypePos);

    void showEmptyMainProductType();

    void showSubProductTypes(List<ProductTypePo> productTypePos);

    void showEmptySubProductType();

    void showShops(List<ShopPo> productTypes);

    void showEmptyShop();

    void navigateCallOut(@NonNull UserPo userPo);
}
