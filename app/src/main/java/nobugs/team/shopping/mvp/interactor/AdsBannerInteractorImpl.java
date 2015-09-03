package nobugs.team.shopping.mvp.interactor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/8/16 0016.
 */
public class AdsBannerInteractorImpl implements AdsBannerInteractor {

    @Override
    public void getAdsBanners(Callback callback) {
        List<String> banners = new ArrayList<>();
        banners.add("file:///android_asset/iv_banner_default.png");
        banners.add("file:///android_asset/iv_banner_default.png");
        banners.add("file:///android_asset/iv_banner_default.png");
//        banners.add("http://img.1985t.com/uploads/attaches/2014/11/26640-1qiLsv.jpg");
//        banners.add("http://image.tianjimedia.com/uploadImages/2015/019/02/2VKC65MJ2AG2_680x500.jpg");
//        banners.add("http://img.1985t.com/uploads/attaches/2015/07/42926-G7gDc9.jpg");

        callback.onSuccess(banners);
    }
}
