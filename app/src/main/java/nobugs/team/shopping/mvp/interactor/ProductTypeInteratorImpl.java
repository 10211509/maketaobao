package nobugs.team.shopping.mvp.interactor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import nobugs.team.shopping.R;
import nobugs.team.shopping.db.entity.ProductType;

/**
 * Created by Administrator on 2015/8/16 0016.
 */
public class ProductTypeInteratorImpl implements ProductTypeInterator {

    private final static String JSON_TEST = "[{\"id\":1,\"parentId\":0,\"typename\":\"水果\",\"imageurl\":\"\"},{\"id\":2,\"parentId\":0,\"typename\":\"蔬菜\",\"imageurl\":\"\"},{\"id\":3,\"parentId\":0,\"typename\":\"生鲜\",\"imageurl\":\"\"},{\"id\":4,\"parentId\":0,\"typename\":\"调料\",\"imageurl\":\"\"},{\"id\":5,\"parentId\":0,\"typename\":\"米油\",\"imageurl\":\"\"},{\"id\":6,\"parentId\":1,\"typename\":\"苹果\",\"imageurl\":\"\"},{\"id\":7,\"parentId\":1,\"typename\":\"香蕉\",\"imageurl\":\"\"},{\"id\":8,\"parentId\":1,\"typename\":\"菠萝\",\"imageurl\":\"\"},{\"id\":9,\"parentId\":1,\"typename\":\"水蜜桃\",\"imageurl\":\"\"},{\"id\":10,\"parentId\":1,\"typename\":\"西瓜\",\"imageurl\":\"\"},{\"id\":11,\"parentId\":1,\"typename\":\"草莓\",\"imageurl\":\"\"},{\"id\":12,\"parentId\":1,\"typename\":\"葡萄\",\"imageurl\":\"\"},{\"id\":13,\"parentId\":2,\"typename\":\"辣椒\",\"imageurl\":\"\"},{\"id\":14,\"parentId\":2,\"typename\":\"茄子\",\"imageurl\":\"\"},{\"id\":15,\"parentId\":2,\"typename\":\"西兰花\",\"imageurl\":\"\"},{\"id\":16,\"parentId\":2,\"typename\":\"芹菜\",\"imageurl\":\"\"},{\"id\":17,\"parentId\":2,\"typename\":\"土豆\",\"imageurl\":\"\"},{\"id\":18,\"parentId\":3,\"typename\":\"鸡腿\",\"imageurl\":\"\"},{\"id\":19,\"parentId\":3,\"typename\":\"鸡心\",\"imageurl\":\"\"},{\"id\":20,\"parentId\":3,\"typename\":\"海虾\",\"imageurl\":\"\"},{\"id\":21,\"parentId\":3,\"typename\":\"牛腩\",\"imageurl\":\"\"},{\"id\":22,\"parentId\":3,\"typename\":\"猪里脊\",\"imageurl\":\"\"},{\"id\":23,\"parentId\":4,\"typename\":\"花椒\",\"imageurl\":\"\"},{\"id\":24,\"parentId\":4,\"typename\":\"大料\",\"imageurl\":\"\"},{\"id\":25,\"parentId\":4,\"typename\":\"酱油\",\"imageurl\":\"\"},{\"id\":26,\"parentId\":4,\"typename\":\"醋\",\"imageurl\":\"\"},{\"id\":27,\"parentId\":5,\"typename\":\"大米\",\"imageurl\":\"\"},{\"id\":28,\"parentId\":5,\"typename\":\"白面\",\"imageurl\":\"\"},{\"id\":29,\"parentId\":5,\"typename\":\"豆油\",\"imageurl\":\"\"},{\"id\":30,\"parentId\":5,\"typename\":\"绿豆\",\"imageurl\":\"\"}]";
    private final static int MAIN_PRODUCT_PARENT_ID = 0;

    private List<ProductType> mProductTypes;

    private void loadProductTypeFromJson(String jsonTest) {
        Gson gson = new Gson();
        mProductTypes = gson.fromJson(jsonTest, mProductTypes.getClass());
    }

    private List<ProductType> findProductTypeByParentId(int parentId) {
        List<ProductType> result = new ArrayList<>();
        if (mProductTypes != null) {
            for (int i = 0; i < mProductTypes.size() ; i++) {
                if (mProductTypes.get(i).getParentid().equals(parentId)){
                    result.add(mProductTypes.get(i));
                }
            }
        }
        return result;
    }

    @Override
    public void getMainProductType(Callback callback) {
        callback.onSuccess(findProductTypeByParentId(MAIN_PRODUCT_PARENT_ID));
    }

    @Override
    public void getSubProductType(int parentId, Callback callback) {
        callback.onSuccess(findProductTypeByParentId(parentId));
    }
}
