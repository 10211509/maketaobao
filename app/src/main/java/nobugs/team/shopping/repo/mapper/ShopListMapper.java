package nobugs.team.shopping.repo.mapper;

import java.util.ArrayList;
import java.util.List;

import nobugs.team.shopping.mvp.model.Shop;
import nobugs.team.shopping.repo.api.entity.ShopAndSeller;
import nobugs.team.shopping.repo.api.entity.ShopListResult;

/**
 * Created by Administrator on 2015/8/23 0023.
 */
public class ShopListMapper implements IResultMapper<ShopListResult, List<Shop>> {
    ShopAndSellerMapper mapper = new ShopAndSellerMapper();

    @Override
    public List<Shop> map(ShopListResult shopListResult) {
        List<Shop> shops = new ArrayList<>();
        for (ShopAndSeller shopPo : shopListResult.getData()) {
            Shop shop = mapper.toModel(shopPo);
            shops.add(shop);
        }
        return shops;
    }

}
