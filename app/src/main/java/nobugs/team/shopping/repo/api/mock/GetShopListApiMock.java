package nobugs.team.shopping.repo.api.mock;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import nobugs.team.shopping.app.base.MyApplication;
import nobugs.team.shopping.mvp.model.ProductType;
import nobugs.team.shopping.mvp.model.Shop;
import nobugs.team.shopping.repo.api.GetShopListApi;
import nobugs.team.shopping.repo.api.GetTypeListApi;
import nobugs.team.shopping.repo.api.model.ShopListResult;
import nobugs.team.shopping.repo.api.model.TypeListResult;
import nobugs.team.shopping.repo.mapper.Mapper;
import nobugs.team.shopping.repo.mapper.ShopListMapper;
import nobugs.team.shopping.repo.mapper.TypeListMapper;
import nobugs.team.shopping.utils.CommonTools;

/**
 * Created by Administrator on 2015/8/23 0023.
 */
public class GetShopListApiMock implements GetShopListApi {

    private static final String JSON_TEST_FILEPATH = "shoplist.json";
    private List<Shop> mShops;

    private final Mapper mapper;

    public GetShopListApiMock() {
        this.mapper = new ShopListMapper();
        loadProductTypeFromJson();
    }

    private void loadProductTypeFromJson() {
        String jsonTest = null;
        try {
            jsonTest = CommonTools.loadJSONFromAsset(MyApplication.getInstance().getApplicationContext(), JSON_TEST_FILEPATH);
            Gson gson = new Gson();
            mShops = (List<Shop>) mapper.map(gson.fromJson(jsonTest, ShopListResult.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public List<Shop> getShopList() {
        return mShops;
    }

    @Override
    public void getShopList(Callback callback) {
        callback.onFinish(mShops);
    }
}
