package nobugs.team.shopping.repo;

import java.util.ArrayList;
import java.util.List;

import nobugs.team.shopping.mvp.model.ProductType;
import nobugs.team.shopping.mvp.model.Shop;
import nobugs.team.shopping.repo.api.GetShopListApi;
import nobugs.team.shopping.repo.api.GetTypeListApi;
import nobugs.team.shopping.repo.api.mock.GetShopListApiMock;
import nobugs.team.shopping.repo.api.mock.GetTypeListApiMock;

/**
 * Created by Administrator on 2015/8/23 0023.
 */
public class Repository {

    private static Repository mInstance;

    public static Repository getInstance(){
        if (mInstance == null){
            mInstance = new Repository();
        }
        return mInstance;
    }

    private final static int MAIN_PRODUCT_PARENT_ID = 0;

    private GetTypeListApi getTypeListApi;
    private GetShopListApi getShopListApi;

    /**
     * 类型缓存
     */
    private List<ProductType> typeListCache;


    private Repository() {
        this.getTypeListApi = new GetTypeListApiMock(); //测试数据
        this.getShopListApi = new GetShopListApiMock(); //测试数据
    }

    public void getMainTypeList(final RepoCallback.Get<ProductType> callbackGet) {
        getTypeListApi.getTypeList(new GetTypeListApi.Callback() {
            @Override
            public void onFinish(List<ProductType> productTypes) {
                typeListCache = productTypes;
                callbackGet.onGotDataSuccess(findMainTypeList(productTypes));
            }
            @Override
            public void onError(int errType, String errMsg) {
                callbackGet.onError(errType, errMsg);
            }
        });
    }

    public void getSubTypeList(final ProductType parent, final RepoCallback.Get<ProductType> callbackGet) {
        if (typeListCache == null){     //缓存为空，重新获取
            getTypeListApi.getTypeList(new GetTypeListApi.Callback() {
                @Override
                public void onFinish(List<ProductType> productTypes) {
                    typeListCache = productTypes;
                    callbackGet.onGotDataSuccess(findSubTypeList(typeListCache, parent));
                }

                @Override
                public void onError(int errType, String errMsg) {
                    callbackGet.onError(errType, errMsg);
                }
            });
        } else {
            callbackGet.onGotDataSuccess(findSubTypeList(typeListCache, parent));
        }
    }

    public void getShopList(final ProductType parent, final RepoCallback.Get<Shop> callbackGet) {
        if (parent.getShops() == null){
            getShopListApi.getShopList(new GetShopListApi.Callback() {
                @Override
                public void onFinish(List<Shop> shops) {
                    parent.setShops(shops);
                    callbackGet.onGotDataSuccess(shops);
                }

                @Override
                public void onError(int errType, String errMsg) {
                    callbackGet.onError(errType, errMsg);
                }
            });
        } else {
            callbackGet.onGotDataSuccess(parent.getShops());    //TODO 是否需要更新ShopList
        }
    }


    private List<ProductType> findMainTypeList(List<ProductType> productTypes){ //TODO 直接转成树状存储
        if (productTypes == null || productTypes.isEmpty()){
            return null;
        }
        List<ProductType> result = new ArrayList<>();
        for(ProductType product: productTypes){
            if (product.getParentId() == MAIN_PRODUCT_PARENT_ID){
                result.add(product);
            }
        }
        return result;
    }

    private List<ProductType> findSubTypeList(List<ProductType> productTypes, ProductType parent){
        if (productTypes == null || productTypes.isEmpty() || parent == null){
            return null;
        }
        if (parent.getSubTypes() != null){
            return parent.getSubTypes();
        }
        List<ProductType> result = new ArrayList<>();
        for(ProductType product: productTypes){
            if (product.getParentId() == parent.getId()){
                result.add(product);
            }
        }
        parent.setSubTypes(result); //增加结果到缓存，下会直接返回

        return result;
    }
}
