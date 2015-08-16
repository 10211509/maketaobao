package nobugs.team.shopping.mvp.view;

import java.util.List;

/**
 * Autor: wangyf on 2015/8/15 0015 20:56
 * Email: zgtjwyftc@gmail.com
 * Description:
 */
public interface MainPageView extends IView {

    void showAndRunAdsBanner(List<Object> imgRes, int period);

    void showEmptyBanner();

    void showErrorBanner();

    void stopRunAdsBanner();
}
