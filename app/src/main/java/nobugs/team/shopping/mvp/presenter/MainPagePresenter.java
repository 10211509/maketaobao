package nobugs.team.shopping.mvp.presenter;

import android.support.annotation.NonNull;

import nobugs.team.shopping.db.entity.Shop;

/**
 * Autor: wangyf on 2015/8/15 0015 19:38
 * Email: zgtjwyftc@gmail.com
 * Description:
 */
public interface MainPagePresenter extends IPresenter{

//    void onShowAdsBanner();

    void onSelectMainProductType(int typeId);

    void onSelectSubProductType(int typeId);

    void onSelectShop(@NonNull Shop shop);

}
