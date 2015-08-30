package nobugs.team.shopping.repo.api.mock;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import nobugs.team.shopping.app.base.MyApplication;
import nobugs.team.shopping.mvp.model.Product;
import nobugs.team.shopping.repo.api.GetProductListApi;
import nobugs.team.shopping.repo.api.model.ProductListResult;
import nobugs.team.shopping.repo.mapper.IResultMapper;
import nobugs.team.shopping.repo.mapper.ProductListMapper;
import nobugs.team.shopping.utils.CommonTools;

/**
 * Created by xiayong on 2015/8/30.
 */
public class GetProductListApiMock implements GetProductListApi {

    private static final String JSON_TEST_FILEPATH = "productlist_shop.json";

    private List<Product> products;
    private final IResultMapper mapper;

    public GetProductListApiMock(){
        mapper = new ProductListMapper();

    }
    private void loadJsonFromAsset() {
        String jsonTest = null;
        try {
            jsonTest = CommonTools.loadJSONFromAsset(MyApplication.getInstance().getApplicationContext(), JSON_TEST_FILEPATH);
            Gson gson = new Gson();
            products = (List<Product>) mapper.map(gson.fromJson(jsonTest, ProductListResult.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void getProductList(String shopid, Callback callback) {
        callback.onFinish(products);
    }
}
