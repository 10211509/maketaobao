package nobugs.team.shopping.mvp.presenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import nobugs.team.shopping.mvp.interactor.AdsBannerInterator;
import nobugs.team.shopping.mvp.interactor.AdsBannerInteratorImpl;
import nobugs.team.shopping.mvp.interactor.LoginInteractor;
import nobugs.team.shopping.mvp.interactor.LoginInteractorImpl;
import nobugs.team.shopping.mvp.view.IView;
import nobugs.team.shopping.mvp.view.LoginView;
import nobugs.team.shopping.mvp.view.MainPageView;

/**
 * Autor: wangyf on 2015/8/15 0015 19:39
 * Email: zgtjwyftc@gmail.com
 * Description:
 */
public class MainPagePresenterImpl extends BasePresenter<MainPageView> implements  MainPagePresenter{

    public static final int BANNER_TURN_PERIOD = 3000;

    private AdsBannerInterator adsBannerInterator;

    public MainPagePresenterImpl(MainPageView view) {
        setView(view);
        this.adsBannerInterator = new AdsBannerInteratorImpl();
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onStart() {
        getView().showEmptyBanner();

        adsBannerInterator.getAdsBanners(new AdsBannerInterator.Callback() {
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

    @Override
    public void onStop() {
        getView().stopRunAdsBanner();
    }

    @Override
    public void onDestroy() {

    }
}
