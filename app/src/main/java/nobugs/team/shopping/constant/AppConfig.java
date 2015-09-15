package nobugs.team.shopping.constant;

/**
 * Created by xiayong on 2015/8/11.
 * 配置文件
 */
public class AppConfig {
    public static final String CCP_ACCOUNT_SID = "aaf98f894ef91b17014ef95b726a0073";
    public static final String CCP_AUTH_TOKEN = "96d0f65f6a77405cb60ec29cebc16caf";

    public static final class URL {
        public static final String WEB_HOST = "http://211.149.223.154:8080/JCNet";//主机address

        public static final String LOGIN            = "/web!login.do";

        public static final String GET_TYPE_LIST    = "/web!typeList.do";

        public static final String GET_SHOP_LIST    = "/web!searchseller.do";

        public static final String GET_PRODUCT_LIST = "/web!queryProductList.do";

        public static final String GET_ORDER_LIST   = "/web!orderList.do";

        public static final String ADD_ORDER   = "/web!addOrder.do";

        public static final String DEL_ORDER   = "/web!delOrder.do";

        public static final String GET_ORDER  = "/web!queryOrderByid.do";

        public static final String UPDATE_ORDER  = "/web!updateOrderState.do";

        public static final String GET_UNIT_LIST  = "/web!unitList.do";

        public static final String CHANGE_ORDERS_STATE  = "/web!updateAllOrderState.do";
    }
}
