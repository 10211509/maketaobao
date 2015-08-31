package nobugs.team.shopping.repo.mapper;

import nobugs.team.shopping.mvp.model.ProductType;
import nobugs.team.shopping.repo.entity.TypePo;

/**
 * Created by wangyf on 2015/8/30 0030.
 */
public class TypeMapper implements IModelMapper<ProductType, TypePo> {

    @Override
    public TypePo fromModel(ProductType productType) {
        return null;
    }

    @Override
    public ProductType toModel(TypePo typePo) {
        ProductType type = new ProductType();
        type.setId(typePo.getId());
        type.setParentId(typePo.getParentId());
        type.setImgUrl(typePo.getImageurl());
        type.setName(typePo.getTypename());
        type.setUnit(typePo.getUnit());
        return type;
    }
}
