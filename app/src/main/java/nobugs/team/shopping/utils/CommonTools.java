package nobugs.team.shopping.utils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import org.apache.http.message.BasicNameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import java.util.List;

/**
 * Created by xiayong on 2015/8/15.
 *
 * 通用工具类
 */
public class CommonTools {
    public static String getDeviceId(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if(telephonyManager != null ) {
            return telephonyManager.getDeviceId();
        }

        return "";

    }

    public static String getMacAddress(Context context) {
        // start get mac address
        WifiManager wifiMan = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (wifiMan != null) {
            WifiInfo wifiInf = wifiMan.getConnectionInfo();
            if (wifiInf != null && wifiInf.getMacAddress() != null) {
                // 48位，如FA:34:7C:6D:E4:D7
                return wifiInf.getMacAddress();
            }
        }
        return "";
    }

    /**
     * device model name, e.g: GT-I9100
     *
     * @return the user_Agent
     */
    public static String getDevice() {
        return Build.MODEL;
    }

    /**
     * device factory name, e.g: Samsung
     *
     * @return the vENDOR
     */
    public static String getVendor() {
        return Build.BRAND;
    }

    /**
     * @return the SDK version
     */
    public static int getSDKVersion() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * @return the OS version
     */
    public static String getOSVersion() {
        return Build.VERSION.RELEASE;
    }

    public static String getDevicNO(Context context) {
        String deviceID = getDeviceId(context);
        if(!TextUtils.isEmpty(deviceID)) {
            return deviceID;
        }

        String  macAddress = getMacAddress(context);
        if(!TextUtils.isEmpty(macAddress)) {
            return macAddress;
        }
        return " ";
    }
}
