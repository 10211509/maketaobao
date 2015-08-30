package nobugs.team.shopping.repo.api.mock;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import nobugs.team.shopping.app.base.MyApplication;
import nobugs.team.shopping.mvp.model.ProductType;
import nobugs.team.shopping.repo.api.GetTypeListApi;
import nobugs.team.shopping.repo.api.model.TypeListResult;
import nobugs.team.shopping.repo.mapper.IResultMapper;
import nobugs.team.shopping.repo.mapper.TypeListMapper;
import nobugs.team.shopping.utils.CommonTools;

/**
 * Created by Administrator on 2015/8/23 0023.
 */
public class GetTypeListApiMock implements GetTypeListApi {

    private static final String JSON_TEST_FILEPATH = "typelist.json";
    private List<ProductType> mProductTypes;

    private final IResultMapper mapper;

    public GetTypeListApiMock() {
        this.mapper = new TypeListMapper();
        loadProductTypeFromJson();
    }

    private void loadProductTypeFromJson() {
        String jsonTest = null;
        try {
            jsonTest = CommonTools.loadJSONFromAsset(MyApplication.getInstance().getApplicationContext(), JSON_TEST_FILEPATH);
            Gson gson = new Gson();
            mProductTypes = (List<ProductType>) mapper.map(gson.fromJson(jsonTest, TypeListResult.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<ProductType> getTypeList() {
        return mProductTypes;
    }

    @Override
    public void getTypeList(Callback callback) {
        callback.onFinish(mProductTypes);
    }
}
