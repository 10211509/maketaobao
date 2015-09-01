package nobugs.team.shopping.repo.mapper;

import java.util.ArrayList;
import java.util.List;

import nobugs.team.shopping.mvp.model.Product;
import nobugs.team.shopping.mvp.model.ProductType;
import nobugs.team.shopping.repo.api.entity.ProductListResult;
import nobugs.team.shopping.repo.entity.ProductPo;

/**
 * Created by xiayong on 2015/8/30.
 */
public class ProductListMapper implements IResultMapper<ProductListResult,List<Product>> {

    @Override
    public List<Product> map(ProductListResult productListResult) {
        List<Product> products = new ArrayList<>();
        for (ProductPo productPo : productListResult.getData()) {
            products.add(mapProduct(productPo));
        }
        return products;
    }
    private Product mapProduct(ProductPo productPo) {
        Product product = new Product();
        product.setName(productPo.getName());
        product.setId(productPo.getId());
       /* ProductType productType = new ProductType();
        productType.setId(productPo.getTypeid());
        product.setType(productType);*/
        return product;
    }
}
