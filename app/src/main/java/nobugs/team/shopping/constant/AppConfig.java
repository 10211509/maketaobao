package nobugs.team.shopping.constant;

/**
 * Created by xiayong on 2015/8/11.
 * 配置文件
 */
public class AppConfig {
    public static  final String CCP_ACCOUNT_SID = "8a48b5514ef922c2014ef95b16c00066";
    public static  final String CCP_AUTH_TOKEN = "96d0f65f6a77405cb60ec29cebc16caf";
    public static  final String CCP_APP_ID = "aaf98f894ef91b17014ef95b72460072";//应用ID
    public static  final String CCP_APP_TOKEN = "4c7ef1139f829cbd48a80fd54a4b6cdc";//应用Token
    public static final class URL{
        public static final String WEB_HOST="http://211.149.223.154:8080/JCNet/web!";//主机address

        public static final String LOGIN=WEB_HOST+"login.do";
        public static final String GET_ORDER_LIST=WEB_HOST+"orderList.do";

    }
}
