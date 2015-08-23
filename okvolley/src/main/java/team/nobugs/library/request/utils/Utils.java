package team.nobugs.library.request.utils;

import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.util.List;

/**
 * Created by xiayong on 2015/8/23.
 */
public class Utils {
    /**
     * 为HttpGet 的 url 方便的添加多个name value 参数。
     *
     * @param url
     * @param params
     * @return
     */
    public static String attachHttpGetParams(String url, List<BasicNameValuePair> params) {
        return url + "?" + URLEncodedUtils.format(params, "UTF-8");
    }
}
