package nobugs.team.shopping.utils;

import android.content.Context;
import android.media.AudioManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiayong on 2015/8/15.
 * <p/>
 * 通用工具类
 */
public class CommonTools {
    private static int currVolume;

    public static String getDeviceId(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager != null) {
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
        if (!TextUtils.isEmpty(deviceID)) {
            return deviceID;
        }

        String macAddress = getMacAddress(context);
        if (!TextUtils.isEmpty(macAddress)) {
            return macAddress;
        }
        return " ";
    }

    public static String loadJSONFromAsset(final Context context, final String fileName)
            throws IOException {
        String json;

        InputStream is = context.getAssets().open(fileName);
        int size = is.available();
        byte[] buffer = new byte[size];

        is.read(buffer);
        is.close();

        json = new String(buffer, "UTF-8");

        return json;
    }

    /**
     * 将javaBean转换成Map
     *
     * @param javaBean javaBean
     * @return Map对象
     */
    public static Map<String, String> toMap(Object javaBean) {
        Map<String, String> result = new HashMap<String, String>();
        Method[] methods = javaBean.getClass().getDeclaredMethods();

        for (Method method : methods) {
            try {
                if (method.getName().startsWith("get")) {
                    String field = method.getName();
                    field = field.substring(field.indexOf("get") + 3);
                    field = field.toLowerCase().charAt(0) + field.substring(1);

                    Object value = method.invoke(javaBean, (Object[]) null);
                    if (value != null) {
                        result.put(field, value.toString());
                    }
                }
            } catch (Exception e) {
            }
        }

        return result;
    }

    /**
     * 判断number参数是否是整型数表示方式
     *
     * @param number
     * @return
     */
    public static boolean isIntegerNumber(String number) {
        number = number.trim();
        String intNumRegex = "\\-{0,1}\\d+";//整数的正则表达式
        if (number.matches(intNumRegex))
            return true;
        else
            return false;
    }

    /**
     * 判断number参数是否是浮点数表示方式
     *
     * @param number
     * @return
     */
    public static boolean isFloatPointNumber(String number) {
        number = number.trim();
        String pointPrefix = "(\\-|\\+){0,1}\\d*\\.\\d+";//浮点数的正则表达式-小数点在中间与前面
        String pointSuffix = "(\\-|\\+){0,1}\\d+\\.";//浮点数的正则表达式-小数点在后面
        if (number.matches(pointPrefix) || number.matches(pointSuffix))
            return true;
        else
            return false;
    }

    //打开扬声器
    public static void openSpeaker(Context context, boolean on) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        if(on) {
            audioManager.setMode(AudioManager.MODE_NORMAL);
            audioManager.setSpeakerphoneOn(true);
        } else {
            audioManager.setSpeakerphoneOn(false);
        }
    }


}
