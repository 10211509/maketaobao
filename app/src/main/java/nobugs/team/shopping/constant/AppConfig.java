package nobugs.team.shopping.constant;

/**
 * Created by xiayong on 2015/8/11.
 * 配置文件
 */
public class AppConfig {
    public static final String CCP_ACCOUNT_SID = "aaf98f894ef91b17014ef95b726a0073";
    public static final String CCP_AUTH_TOKEN = "96d0f65f6a77405cb60ec29cebc16caf";
//    public static final String CCP_APP_ID = "aaf98f894ef91b17014ef95b72460072";//应用ID
//    public static final String CCP_APP_TOKEN = "4c7ef1139f829cbd48a80fd54a4b6cdc";//应用Token
    public static final String CCP_APP_ID = "20150314000000110000000000000010";//DEMO应用ID
    public static final String CCP_APP_TOKEN = "17E24E5AFDB6D0C1EF32F3533494502B";//DEMO应用Token

    public static final class URL {
        public static final String WEB_HOST = "http://211.149.223.154:8080/JCNet";//主机address

        public static final String LOGIN            = "/web!login.do";

        public static final String GET_ORDER_LIST   = "/web!orderList.do";

        public static final String GET_TYPE_LIST    = "/web!typeList.do";

        public static final String GET_SHOP_LIST    = "/web!searchseller.do";
    }
}
