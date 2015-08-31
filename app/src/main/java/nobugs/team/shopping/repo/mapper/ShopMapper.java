package nobugs.team.shopping.repo.mapper;

import nobugs.team.shopping.mvp.model.Shop;
import nobugs.team.shopping.repo.entity.ShopPo;

/**
 * Created by wangyf on 2015/8/30 0030.
 */
public class ShopMapper implements IModelMapper<Shop, ShopPo> {

    @Override
    public ShopPo fromModel(Shop orderPo) {
        return null;
    }

    @Override
    public Shop toModel(ShopPo shopPo) {
        Shop shop = new Shop();
        shop.setId(shopPo.getId());
        shop.setOwnerId(shopPo.getUserid());
        shop.setName(shopPo.getName());
        shop.setIntroduction(shopPo.getIntroduction());
        return shop;
    }
}
