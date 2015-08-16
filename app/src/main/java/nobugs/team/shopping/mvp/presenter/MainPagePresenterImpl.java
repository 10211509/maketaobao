package nobugs.team.shopping.mvp.presenter;

import java.util.List;

import nobugs.team.shopping.db.entity.ProductType;
import nobugs.team.shopping.mvp.interactor.AdsBannerInterator;
import nobugs.team.shopping.mvp.interactor.AdsBannerInteratorImpl;
import nobugs.team.shopping.mvp.interactor.ProductTypeInterator;
import nobugs.team.shopping.mvp.interactor.ProductTypeInteratorImpl;
import nobugs.team.shopping.mvp.view.MainPageView;

/**
 * Autor: wangyf on 2015/8/15 0015 19:39
 * Email: zgtjwyftc@gmail.com
 * Description:
 */
public class MainPagePresenterImpl extends BasePresenter<MainPageView> implements  MainPagePresenter{

    public static final int BANNER_TURN_PERIOD = 3000;

    private AdsBannerInterator mAdsBannerInterator;
    private ProductTypeInterator mProductTypeInterator;

    public MainPagePresenterImpl(MainPageView view) {
        setView(view);
        this.mAdsBannerInterator = new AdsBannerInteratorImpl();
        this.mProductTypeInterator = new ProductTypeInteratorImpl();
    }

    private void showAdsBanner(){
        mAdsBannerInterator.getAdsBanners(new AdsBannerInterator.Callback() {
            @Override
            public void onSuccess(List<Object> urlOrIds) {
                getView().showAndRunAdsBanner(urlOrIds, BANNER_TURN_PERIOD);
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


    private void showSubProductType() {
        mProductTypeInterator.getMainProductType(new ProductTypeInterator.Callback() {
            @Override
            public void onSuccess(List<ProductType> types) {

            }
            @Override
            public void onNetWorkError() {

            }
            @Override
            public void onFailure() {

            }
        });
    }

    private void showMainProductType() {

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onStart() {
        getView().showEmptyBanner();

        showAdsBanner();

        showMainProductType();

        showSubProductType();
    }

    @Override
    public void onStop() {
        getView().stopRunAdsBanner();
    }

    @Override
    public void onDestroy() {

    }
}
