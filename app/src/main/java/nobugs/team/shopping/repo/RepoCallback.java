package nobugs.team.shopping.repo;

import java.util.List;

/**
 * Created by Administrator on 2015/8/23 0023.
 */
public interface RepoCallback<DATA> {
    interface Get<DATA> {
        void onGotDataSuccess(DATA data);

        void onError(int errType, String errMsg);
    }
    interface GetList<DATA> {
        void onGotDataListSuccess(List<DATA> dataList);

        void onError(int errType, String errMsg);
    }
    interface Add<DATA> {
        void onAddDataSuccess(int id);

        void onError(int errType, String errMsg);
    }
    interface AddList<DATA> {
        void onAddDataListSuccess(int[] ids);

        void onError(List<DATA> addFailed, int errType, String errMsg);
    }
    interface Update<DATA> {
        void onUpateDataSuccess();

        void onError(int errType, String errMsg);
    }
    interface UpdateList<DATA> {
        void onUpateDataListSuccess();

        void onError(List<DATA> updateFailed, int errType, String errMsg);
    }
    interface Remove<DATA> {
        void onRemoveDataSuccess();

        void onError(int errType, String errMsg);
    }
    interface RemoveList<DATA> {
        void onRemoveDataListSuccess();

        void onError(List<DATA> removeFailed, int errType, String errMsg);
    }
}
