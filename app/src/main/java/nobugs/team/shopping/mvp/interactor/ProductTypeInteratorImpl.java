package nobugs.team.shopping.mvp.interactor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import nobugs.team.shopping.db.entity.ProductType;

/**
 * Created by Administrator on 2015/8/16 0016.
 */
public class ProductTypeInteratorImpl implements ProductTypeInterator {

    private final static String JSON_TEST = "[{\"id\":1,\"parentid\":0,\"name\":\"水果\",\"imgurl\":\"\"},{\"id\":2,\"parentid\":0,\"name\":\"蔬菜\",\"imgurl\":\"\"},{\"id\":3,\"parentid\":0,\"name\":\"生鲜\",\"imgurl\":\"\"},{\"id\":4,\"parentid\":0,\"name\":\"调料\",\"imgurl\":\"\"},{\"id\":5,\"parentid\":0,\"name\":\"米油\",\"imgurl\":\"\"},{\"id\":6,\"parentid\":1,\"name\":\"苹果\",\"imgurl\":\"\"},{\"id\":7,\"parentid\":1,\"name\":\"香蕉\",\"imgurl\":\"\"},{\"id\":8,\"parentid\":1,\"name\":\"菠萝\",\"imgurl\":\"\"},{\"id\":9,\"parentid\":1,\"name\":\"水蜜桃\",\"imgurl\":\"\"},{\"id\":10,\"parentid\":1,\"name\":\"西瓜\",\"imgurl\":\"\"},{\"id\":11,\"parentid\":1,\"name\":\"草莓\",\"imgurl\":\"\"},{\"id\":12,\"parentid\":1,\"name\":\"葡萄\",\"imgurl\":\"\"},{\"id\":13,\"parentid\":2,\"name\":\"辣椒\",\"imgurl\":\"\"},{\"id\":14,\"parentid\":2,\"name\":\"茄子\",\"imgurl\":\"\"},{\"id\":15,\"parentid\":2,\"name\":\"西兰花\",\"imgurl\":\"\"},{\"id\":16,\"parentid\":2,\"name\":\"芹菜\",\"imgurl\":\"\"},{\"id\":17,\"parentid\":2,\"name\":\"土豆\",\"imgurl\":\"\"},{\"id\":18,\"parentid\":3,\"name\":\"鸡腿\",\"imgurl\":\"\"},{\"id\":19,\"parentid\":3,\"name\":\"鸡心\",\"imgurl\":\"\"},{\"id\":20,\"parentid\":3,\"name\":\"海虾\",\"imgurl\":\"\"},{\"id\":21,\"parentid\":3,\"name\":\"牛腩\",\"imgurl\":\"\"},{\"id\":22,\"parentid\":3,\"name\":\"猪里脊\",\"imgurl\":\"\"},{\"id\":23,\"parentid\":4,\"name\":\"花椒\",\"imgurl\":\"\"},{\"id\":24,\"parentid\":4,\"name\":\"大料\",\"imgurl\":\"\"},{\"id\":25,\"parentid\":4,\"name\":\"酱油\",\"imgurl\":\"\"},{\"id\":26,\"parentid\":4,\"name\":\"醋\",\"imgurl\":\"\"},{\"id\":27,\"parentid\":5,\"name\":\"大米\",\"imgurl\":\"\"},{\"id\":28,\"parentid\":5,\"name\":\"白面\",\"imgurl\":\"\"},{\"id\":29,\"parentid\":5,\"name\":\"豆油\",\"imgurl\":\"\"},{\"id\":30,\"parentid\":5,\"name\":\"绿豆\",\"imgurl\":\"\"}]";
    private final static int MAIN_PRODUCT_PARENT_ID = 0;

    private ArrayList<ProductType> mProductTypes;

    public ProductTypeInteratorImpl() {
        loadProductTypeFromJson(JSON_TEST);
    }

    private void loadProductTypeFromJson(String jsonTest) {
        Gson gson = new Gson();
        mProductTypes = gson.fromJson(jsonTest, new TypeToken<ArrayList<ProductType>>(){}.getType());
    }

    private List<ProductType> findProductTypeByParentId(int parentid) {
        List<ProductType> result = new ArrayList<>();
        if (mProductTypes != null) {
            for (int i = 0; i < mProductTypes.size(); i++) {
                if (mProductTypes.get(i).getParentid().equals(parentid)) {
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
    public void getSubProductType(int parentid, Callback callback) {
        callback.onSuccess(findProductTypeByParentId(parentid));
    }
}
