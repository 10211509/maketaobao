package nobugs.team.shopping.db;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import  nobugs.team.shopping.db.dao.UserDao;
import  nobugs.team.shopping.db.dao.ProductTypeDao;
import  nobugs.team.shopping.db.dao.ProductDao;
import  nobugs.team.shopping.db.dao.ShopDao;
import  nobugs.team.shopping.db.dao.OrderDao;
import nobugs.team.shopping.db.entity.Order;
import nobugs.team.shopping.db.entity.Product;
import nobugs.team.shopping.db.entity.ProductType;
import nobugs.team.shopping.db.entity.Shop;
import nobugs.team.shopping.db.entity.User;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig userDaoConfig;
    private final DaoConfig productTypeDaoConfig;
    private final DaoConfig productDaoConfig;
    private final DaoConfig shopDaoConfig;
    private final DaoConfig orderDaoConfig;

    private final UserDao userDao;
    private final ProductTypeDao productTypeDao;
    private final ProductDao productDao;
    private final ShopDao shopDao;
    private final OrderDao orderDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        userDaoConfig = daoConfigMap.get(UserDao.class).clone();
        userDaoConfig.initIdentityScope(type);

        productTypeDaoConfig = daoConfigMap.get(ProductTypeDao.class).clone();
        productTypeDaoConfig.initIdentityScope(type);

        productDaoConfig = daoConfigMap.get(ProductDao.class).clone();
        productDaoConfig.initIdentityScope(type);

        shopDaoConfig = daoConfigMap.get(ShopDao.class).clone();
        shopDaoConfig.initIdentityScope(type);

        orderDaoConfig = daoConfigMap.get(OrderDao.class).clone();
        orderDaoConfig.initIdentityScope(type);

        userDao = new UserDao(userDaoConfig, this);
        productTypeDao = new ProductTypeDao(productTypeDaoConfig, this);
        productDao = new ProductDao(productDaoConfig, this);
        shopDao = new ShopDao(shopDaoConfig, this);
        orderDao = new OrderDao(orderDaoConfig, this);

        registerDao(User.class, userDao);
        registerDao(ProductType.class, productTypeDao);
        registerDao(Product.class, productDao);
        registerDao(Shop.class, shopDao);
        registerDao(Order.class, orderDao);
    }
    
    public void clear() {
        userDaoConfig.getIdentityScope().clear();
        productTypeDaoConfig.getIdentityScope().clear();
        productDaoConfig.getIdentityScope().clear();
        shopDaoConfig.getIdentityScope().clear();
        orderDaoConfig.getIdentityScope().clear();
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public ProductTypeDao getProductTypeDao() {
        return productTypeDao;
    }

    public ProductDao getProductDao() {
        return productDao;
    }

    public ShopDao getShopDao() {
        return shopDao;
    }

    public OrderDao getOrderDao() {
        return orderDao;
    }

}
