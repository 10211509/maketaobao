package nobugs.team.shopping.mvp.interactor;

import java.util.List;

/**
 * Created by Administrator on 2015/8/16 0016.
 */
public interface AdsBannerInterator {
    void getAdsBanners(Callback callback);

    interface Callback {
        void onSuccess(List<String> urlOrIds);

        void onNetWorkError();

        void onFailure();
    }
}
