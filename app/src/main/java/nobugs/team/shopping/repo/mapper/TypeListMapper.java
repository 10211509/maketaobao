package nobugs.team.shopping.repo.mapper;

import java.util.ArrayList;
import java.util.List;

import nobugs.team.shopping.mvp.model.ProductType;
import nobugs.team.shopping.repo.api.model.TypeListResult;
import nobugs.team.shopping.repo.api.model.TypePo;

/**
 * Created by Administrator on 2015/8/23 0023.
 */
public class TypeListMapper implements Mapper<TypeListResult, List<ProductType>>{

    @Override
    public List<ProductType> map(TypeListResult typeListResult) {
        List<ProductType> productTypes = new ArrayList<>();
        for (TypePo typePo : typeListResult.getData()) {
            productTypes.add(mapProduct(typePo));
        }
        return productTypes;
    }

    private ProductType mapProduct(TypePo typePo) {
        ProductType type = new ProductType();
        type.setId(typePo.getId());
        type.setParentId(typePo.getParentId());
        type.setImgUrl(typePo.getImageurl());
        type.setName(typePo.getTypename());
        return type;
    }


}
