package nobugs.team.shopping.repo.mapper;

import java.util.ArrayList;
import java.util.List;

import nobugs.team.shopping.mvp.model.Product;
import nobugs.team.shopping.mvp.model.ProductType;
import nobugs.team.shopping.repo.api.model.ProductListResult;
import nobugs.team.shopping.repo.model.ProductPo;
import nobugs.team.shopping.repo.model.TypePo;

/**
 * Created by xiayong on 2015/8/30.
 */
public class ProductListMapper implements Mapper<ProductListResult,List<Product>> {

    @Override
    public List<Product> map(ProductListResult productListResult) {
        List<Product> products = new ArrayList<>();
        for (ProductPo productPo : productListResult.getData()) {
            products.add(mapProduct(productPo));
        }
        return products;
    }
    private Product mapProduct(ProductPo productPo) {
        Product type = new Product();
        type.setName(productPo.getName());
        type.setId(productPo.getId());
        ProductType productType = new ProductType();
        productType.setId(productPo.getTypeid());
        type.setType(productType);
        return type;
    }
}
