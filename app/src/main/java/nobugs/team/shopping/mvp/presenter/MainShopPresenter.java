package nobugs.team.shopping.mvp.presenter;

import android.support.annotation.NonNull;

import nobugs.team.shopping.mvp.model.ProductType;
import nobugs.team.shopping.mvp.model.Shop;

/**
 * Autor: wangyf on 2015/8/15 0015 19:38
 * Email: zgtjwyftc@gmail.com
 * Description:
 */
public interface MainShopPresenter extends IPresenter{

//    void onShowAdsBanner();

    void onSelectMainProductType(ProductType mainType);

    void onSelectSubProductType(ProductType subType);

    void onSelectShop(@NonNull Shop shop);

}
