package nobugs.team.shopping.repo.mapper;

import nobugs.team.shopping.mvp.model.Shop;
import nobugs.team.shopping.mvp.model.User;
import nobugs.team.shopping.repo.api.entity.ShopAndSeller;

/**
 * Created by wangyf on 2015/8/30 0030.
 */
public class ShopAndSellerMapper implements IModelMapper<Shop, ShopAndSeller> {

    @Override
    public ShopAndSeller fromModel(Shop orderPo) {
        return null;
    }

    @Override
    public Shop toModel(ShopAndSeller shopAndSeller) {
        User seller = new User();
        seller.setId(Long.valueOf(shopAndSeller.getUserid()));
        seller.setPhone(shopAndSeller.getPhone());
        seller.setNickname(shopAndSeller.getUsername());
        seller.setType(User.Type.SELLER);

        Shop shop = new Shop();
        shop.setId(shopAndSeller.getId());
        shop.setOwnerId(shopAndSeller.getUserid());
        shop.setName(shopAndSeller.getName());
        shop.setIntroduction(shopAndSeller.getIntroduction());
        shop.setOwner(seller);

        return shop;
    }
}
