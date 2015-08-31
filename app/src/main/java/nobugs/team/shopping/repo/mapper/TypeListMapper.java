package nobugs.team.shopping.repo.mapper;

import java.util.ArrayList;
import java.util.List;

import nobugs.team.shopping.mvp.model.ProductType;
import nobugs.team.shopping.repo.api.entity.TypeListResult;
import nobugs.team.shopping.repo.entity.TypePo;

/**
 * Created by Administrator on 2015/8/23 0023.
 */
public class TypeListMapper implements IResultMapper<TypeListResult, List<ProductType>> {

    TypeMapper mapper = new TypeMapper();

    @Override
    public List<ProductType> map(TypeListResult typeListResult) {
        List<ProductType> productTypes = new ArrayList<>();
        for (TypePo typePo : typeListResult.getData()) {
            productTypes.add(mapper.toModel(typePo));
        }
        return productTypes;
    }

}
