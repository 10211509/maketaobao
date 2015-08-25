package nobugs.team.shopping.mvp.presenter;

import android.support.annotation.NonNull;

import java.util.List;

import de.greenrobot.event.EventBus;
import nobugs.team.shopping.event.SelectShopEvent;
import nobugs.team.shopping.mvp.model.ProductType;
import nobugs.team.shopping.mvp.interactor.AdsBannerInterator;
import nobugs.team.shopping.mvp.interactor.AdsBannerInteratorImpl;
import nobugs.team.shopping.mvp.interactor.ProductTypeInterator;
import nobugs.team.shopping.mvp.interactor.ProductTypeInteratorImpl;
import nobugs.team.shopping.mvp.interactor.ShopInterator;
import nobugs.team.shopping.mvp.interactor.ShopInteratorImpl;
import nobugs.team.shopping.mvp.model.Shop;
import nobugs.team.shopping.mvp.model.User;
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

//        getView().showEmptySubProductType();
//        getView().showEmptyShop();

        mProductTypeInterator.getMainProductType(new ProductTypeInterator.Callback() {
            @Override
            public void onSuccess(List<ProductType> types) {
                if (types != null && types.size() > 0) {
                    getView().showMainProductTypes(types);

                    ProductType firstMainType = types.get(0);
                    if (firstMainType != null) {
                        showSubProductTypes(firstMainType);
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


    private void showSubProductTypes(ProductType mainType) {
        getView().showEmptySubProductType();

//        getView().showEmptyShop();

        mProductTypeInterator.getSubProductType(mainType, new ProductTypeInterator.Callback() {
            @Override
            public void onSuccess(List<ProductType> types) {
                if (types != null && types.size() > 0) {
                    getView().showSubProductTypes(types);

                    ProductType firstSubType = types.get(0);
                    if (firstSubType != null) {
                        showShops(firstSubType);
                    }
                }
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

    private void showShops(ProductType subType) {
        getView().showEmptyShop();

        mShopInterator.getShops(subType, new ShopInterator.Callback() {
            @Override
            public void onSuccess(List<Shop> shops) {
                getView().showShops(shops);
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

        //TODO
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
    public void onSelectMainProductType(ProductType mainType) {
        showSubProductTypes(mainType);
    }

    @Override
    public void onSelectSubProductType(ProductType subType) {
        showShops(subType);
    }

    @Override
    public void onSelectShop(@NonNull Shop shop) {
        //start to call the SELLER
        User user = new User(1,"xiayong","12345","18010035906", User.Type.SELLER);
        shop.setOwner(user);

        EventBus.getDefault().postSticky(new SelectShopEvent(shop));
        getView().navigateCallOut(shop.getOwner());
    }
}
