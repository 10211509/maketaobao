package nobugs.team.shopping.repo.mapper;

import java.util.ArrayList;
import java.util.List;

import nobugs.team.shopping.mvp.model.Shop;
import nobugs.team.shopping.repo.api.model.ShopListResult;
import nobugs.team.shopping.repo.api.model.ShopPo;

/**
 * Created by Administrator on 2015/8/23 0023.
 */
public class ShopListMapper implements Mapper<ShopListResult, List<Shop>>{

    @Override
    public List<Shop> map(ShopListResult shopListResult) {
        List<Shop> shops = new ArrayList<>();
        for (ShopPo shopPo : shopListResult.getData()) {
            shops.add(mapShop(shopPo));
        }
        return shops;
    }

    private Shop mapShop(ShopPo shopPo) {
        Shop shop = new Shop();
        shop.setId(shopPo.getId());
        shop.setOwnerId(shopPo.getUserid());
        shop.setName(shopPo.getName());
        shop.setIntroduction(shopPo.getIntroduction());
        return shop;
    }


}
