package nobugs.team.shopping.mvp.interactor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import nobugs.team.shopping.repo.db.entity.ShopPo;

/**
 * Created by Administrator on 2015/8/16 0016.
 */
public class ShopInteratorImpl implements ShopInterator {

    private final static String JSON_TEST = "[{\"id\":1,\"name\":\"武汉水果生鲜店\",\"userid\":2,\"introduction\":\"批发水果，蔬菜，生鲜和油盐酱醋\"},{\"id\":2,\"name\":\"北京MM店\",\"userid\":3,\"introduction\":\"批发各种MM，熟女，素人\"},{\"id\":3,\"name\":\"上海约炮店\",\"userid\":4,\"introduction\":\"快来这里约炮哟\"},{\"id\":4,\"name\":\"四川约炮店\",\"userid\":4,\"introduction\":\"快来这里约炮哟\"},{\"id\":5,\"name\":\"南京约炮店\",\"userid\":4,\"introduction\":\"快来这里约炮哟\"}]";

    private List<ShopPo> findShopByTypeId(int typeId) {
        Gson gson = new Gson();
        ArrayList<ShopPo> result = gson.fromJson(JSON_TEST, new TypeToken<ArrayList<ShopPo>>(){}.getType());
        return result;
    }

    @Override
    public void getShops(int typeId, Callback callback) {
        callback.onSuccess(findShopByTypeId(typeId));
    }
}
