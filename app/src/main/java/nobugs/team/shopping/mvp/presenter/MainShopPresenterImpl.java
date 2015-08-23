package nobugs.team.shopping.mvp.presenter;

import android.support.annotation.NonNull;

import java.util.List;

import de.greenrobot.event.EventBus;
import nobugs.team.shopping.repo.db.entity.ProductTypePo;
import nobugs.team.shopping.repo.db.entity.ShopPo;
import nobugs.team.shopping.repo.db.entity.UserPo;
import nobugs.team.shopping.mvp.interactor.AdsBannerInterator;
import nobugs.team.shopping.mvp.interactor.AdsBannerInteratorImpl;
import nobugs.team.shopping.mvp.interactor.ProductTypeInterator;
import nobugs.team.shopping.mvp.interactor.ProductTypeInteratorImpl;
import nobugs.team.shopping.mvp.interactor.ShopInterator;
import nobugs.team.shopping.mvp.interactor.ShopInteratorImpl;
import nobugs.team.shopping.mvp.view.MainShopView;

/**
 * Autor: wangyf on 2015/8/15 0015 19:39
 * Email: zgtjwyftc@gmail.com
 * Description:
 */
public class MainShopPresenterImpl extends BasePresenter<MainShopView> implements MainShopPresenter {

    public static final int BANNER_TURN_PERIOD = 3000;

    private AdsBannerInterator mAdsBannerInterator;
    private ProductTypeInterator mProductTypeInterator;
    private ShopInterator mShopInterator;

    private int mFirstMainTypeId = -1;

    public MainShopPresenterImpl(MainShopView view) {
        setView(view);
        this.mAdsBannerInterator = new AdsBannerInteratorImpl();
        this.mProductTypeInterator = new ProductTypeInteratorImpl();
        this.mShopInterator = new ShopInteratorImpl();
    }

    private void showAdsBanner() {
        getView().showEmptyBanner();

        mAdsBannerInterator.getAdsBanners(new AdsBannerInterator.Callback() {
            @Override
            public void onSuccess(List<String> imgUrls) {
                getView().showAndRunAdsBanner(imgUrls, BANNER_TURN_PERIOD);
            }

            @Override
            public void onNetWorkError() {
                getView().showErrorBanner();
            }

            @Override
            public void onFailure() {
                getView().showErrorBanner();
            }
        });
    }

    private void showProductTypes() {
        getView().showEmptyMainProductType();

        getView().showEmptySubProductType();

        mProductTypeInterator.getMainProductType(new ProductTypeInterator.Callback() {
            @Override
            public void onSuccess(List<ProductTypePo> types) {
                if (types != null && types.size() > 0) {
                    getView().showMainProductTypes(types);

                    mFirstMainTypeId = types.get(0).getId();
                    if (mFirstMainTypeId >= 0) {
                        showSubProductTypes(mFirstMainTypeId);
                    }
                }
            }

            @Override
            public void onNetWorkError() {
                getView().showEmptyMainProductType();
            }

            @Override
            public void onFailure() {
                getView().showEmptyMainProductType();
            }
        });
    }


    private void showSubProductTypes(int typeId) {
        mProductTypeInterator.getSubProductType(typeId, new ProductTypeInterator.Callback() {
            @Override
            public void onSuccess(List<ProductTypePo> types) {
                getView().showSubProductTypes(types);
            }

            @Override
            public void onNetWorkError() {
                getView().showEmptySubProductType();
            }

            @Override
            public void onFailure() {
                getView().showEmptySubProductType();
            }
        });
    }

    private void showShops(int typeId) {
        getView().showEmptyShop();

        mShopInterator.getShops(typeId, new ShopInterator.Callback() {
            @Override
            public void onSuccess(List<ShopPo> shopPos) {
                getView().showShops(shopPos);
            }

            @Override
            public void onNetWorkError() {
                getView().showEmptyShop();
            }

            @Override
            public void onFailure() {
                getView().showEmptyShop();
            }
        });
    }


    @Override
    public void onCreate() {

    }

    @Override
    public void onStart() {

        showAdsBanner();

        showProductTypes();
    }

    @Override
    public void onStop() {
        getView().stopRunAdsBanner();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onSelectMainProductType(int typeId) {
        showSubProductTypes(typeId);
    }

    @Override
    public void onSelectSubProductType(int typeId) {
        showShops(typeId);
    }

    @Override
    public void onSelectShop(@NonNull ShopPo shopPo) {
        //start to call the seller
        UserPo userPo = new UserPo(1,"xiayong","12345","18010035906",2);
        shopPo.setUserPo(userPo);
        EventBus.getDefault().postSticky(shopPo.getUserPo());
        getView().navigateCallOut(shopPo.getUserPo());
    }
}
