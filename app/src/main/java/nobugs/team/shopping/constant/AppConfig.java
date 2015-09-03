package nobugs.team.shopping.constant;

/**
 * Created by xiayong on 2015/8/11.
 * 配置文件
 */
public class AppConfig {
    public static final String CCP_ACCOUNT_SID = "aaf98f894ef91b17014ef95b726a0073";
    public static final String CCP_AUTH_TOKEN = "96d0f65f6a77405cb60ec29cebc16caf";
    public static final String CCP_APP_ID = "8a48b5514f2b46d0014f2f7c697b04b3";//应用ID
    public static final String CCP_APP_TOKEN = "4c29b30377563dbb90ad92beb68a18f5";//应用Token
//    public static final String CCP_APP_ID = "20150314000000110000000000000010";//DEMO应用ID
//    public static final String CCP_APP_TOKEN = "17E24E5AFDB6D0C1EF32F3533494502B";//DEMO应用Token

    public static final class URL {
        public static final String WEB_HOST = "http://211.149.223.154:8080/JCNet";//主机address

        public static final String LOGIN            = "/web!login.do";

        public static final String GET_TYPE_LIST    = "/web!typeList.do";

        public static final String GET_SHOP_LIST    = "/web!searchseller.do";

        public static final String GET_PRODUCT_LIST = "/web!queryProductList.do";

        public static final String GET_ORDER_LIST   = "/web!orderList.do";

        public static final String ADD_ORDER   = "/web!refreshViewPager.do";

        public static final String DEL_ORDER   = "/web!delOrder.do";

        public static final String GET_ORDER  = "/web!queryOrderByid.do";

        public static final String UPDATE_ORDER  = "/web!updateOrderState.do";
    }
}
