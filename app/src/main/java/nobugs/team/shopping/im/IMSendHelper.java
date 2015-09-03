package nobugs.team.shopping.im;

import android.util.Log;

import com.google.gson.Gson;
import com.yuntongxun.ecsdk.ECDeskManager;
import com.yuntongxun.ecsdk.ECError;
import com.yuntongxun.ecsdk.ECMessage;

import nobugs.team.shopping.im.entity.IMAddOrder;
import nobugs.team.shopping.im.entity.IMDelOrder;
import nobugs.team.shopping.im.entity.IMSelectShop;
import nobugs.team.shopping.im.entity.IMShoppingCartCommit;
import nobugs.team.shopping.mvp.model.Order;
import nobugs.team.shopping.mvp.model.Shop;
import nobugs.team.shopping.mvp.model.User;
import nobugs.team.shopping.repo.mapper.OrderMapper;
import nobugs.team.shopping.repo.mapper.UserMapper;

/**
 * Created by wangyf on 2015/9/2 0002.
 */
public class IMSendHelper {

    private static final String TAG = "IMHelper";

    public static void sendSelectShop(User me, String peerPhone, Shop shop) {
        UserMapper mapper = new UserMapper();

        IMSelectShop selectShop = new IMSelectShop(shop.getId(), mapper.fromModel(me));
        Gson gson = new Gson();

        String myPhone = me.getPhone();
        String json = gson.toJson(selectShop, IMSelectShop.class);

        IMChattingHelper.sendECMessage(myPhone, peerPhone, json, new ECDeskManager.OnSendDeskMessageListener() {
            @Override
            public void onSendMessageComplete(ECError ecError, ECMessage ecMessage) {
                Log.e(TAG, "[onSendMessageComplete] ecError: " + ecError + ", ecMessage:" + ecMessage);
            }

            @Override
            public void onProgress(String s, int i, int i1) {
                Log.e(TAG, "[onProgress] s: " + s + ", i:" + i);
            }
        });
    }

    public static void sendAddOrder(String myPhone, String peerPhone, Order order) {
        OrderMapper mapper = new OrderMapper();

        IMAddOrder imAddOrder = new IMAddOrder(mapper.fromModel(order));
        Gson gson = new Gson();

        String json = gson.toJson(imAddOrder, IMAddOrder.class);

        IMChattingHelper.sendECMessage(myPhone, peerPhone, json, new ECDeskManager.OnSendDeskMessageListener() {
            @Override
            public void onSendMessageComplete(ECError ecError, ECMessage ecMessage) {
                Log.e(TAG, "[onSendMessageComplete] ecError: " + ecError + ", ecMessage:" + ecMessage);
            }

            @Override
            public void onProgress(String s, int i, int i1) {
                Log.e(TAG, "[onProgress] s: " + s + ", i:" + i);
            }
        });
    }

    public static void sendDelOrder(String myPhone, String peerPhone, int orderId) {

        IMDelOrder imDelOrder = new IMDelOrder(orderId);
        Gson gson = new Gson();

        String json = gson.toJson(imDelOrder, IMDelOrder.class);

        IMChattingHelper.sendECMessage(myPhone, peerPhone, json, new ECDeskManager.OnSendDeskMessageListener() {
            @Override
            public void onSendMessageComplete(ECError ecError, ECMessage ecMessage) {
                Log.e(TAG, "[onSendMessageComplete] ecError: " + ecError + ", ecMessage:" + ecMessage);
            }

            @Override
            public void onProgress(String s, int i, int i1) {
                Log.e(TAG, "[onProgress] s: " + s + ", i:" + i);
            }
        });
    }

    public static void sendShoppingCartCommit(String myPhone, String peerPhone, int productTotal, double priceTotal) {

        IMShoppingCartCommit imShoppingCartCommit = new IMShoppingCartCommit(productTotal, priceTotal);
        Gson gson = new Gson();

        String json = gson.toJson(imShoppingCartCommit, IMShoppingCartCommit.class);

        IMChattingHelper.sendECMessage(myPhone, peerPhone, json, new ECDeskManager.OnSendDeskMessageListener() {
            @Override
            public void onSendMessageComplete(ECError ecError, ECMessage ecMessage) {
                Log.e(TAG, "[onSendMessageComplete] ecError: " + ecError + ", ecMessage:" + ecMessage);
            }

            @Override
            public void onProgress(String s, int i, int i1) {
                Log.e(TAG, "[onProgress] s: " + s + ", i:" + i);
            }
        });
    }
}
