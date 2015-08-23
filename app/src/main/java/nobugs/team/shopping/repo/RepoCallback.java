package nobugs.team.shopping.repo;

import java.util.List;

/**
 * Created by Administrator on 2015/8/23 0023.
 */
public interface RepoCallback<DATA> {
    interface Get<DATA> {
        void onGotDataSuccess(List<DATA> datas);

        void onError(int errType, String errMsg);
    }

    interface Add<DATA> {
        void onAddDataSuccess();

        void onError(List<DATA> addFailed, int errType, String errMsg);
    }

    interface Update<DATA> {
        void onUpateDataSuccess();

        void onError(List<DATA> updateFailed, int errType, String errMsg);
    }

    interface Remove<DATA> {
        void onRemoveDataSuccess();

        void onError(List<DATA> removeFailed, int errType, String errMsg);
    }
}
