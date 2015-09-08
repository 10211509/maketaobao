package nobugs.team.shopping.repo.api;

import java.util.List;

import nobugs.team.shopping.mvp.model.ProductType;

/**
 * Created by Administrator on 2015/8/23 0023.
 */
public interface GetUnitListApi {
    List<String> getUnitList();

    void getUnitList(Callback callback);

    interface Callback {

        void onFinish(List<String> unitList);

        void onError(int errType, String errMsg);
    }
}
