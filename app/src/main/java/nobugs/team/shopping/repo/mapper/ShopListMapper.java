package nobugs.team.shopping.repo.mapper;

import java.util.ArrayList;
import java.util.List;

import nobugs.team.shopping.mvp.model.Shop;
import nobugs.team.shopping.repo.api.entity.ShopListResult;
import nobugs.team.shopping.repo.entity.ShopPo;

/**
 * Created by Administrator on 2015/8/23 0023.
 */
public class ShopListMapper implements IResultMapper<ShopListResult, List<Shop>> {
    ShopMapper mapper = new ShopMapper();

    @Override
    public List<Shop> map(ShopListResult shopListResult) {
        List<Shop> shops = new ArrayList<>();
        for (ShopPo shopPo : shopListResult.getData()) {
            shops.add(mapper.toModel(shopPo));
        }
        return shops;
    }

}
