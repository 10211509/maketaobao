package nobugs.team.shopping.repo.api;

/**
 * Created by xiayong on 2015/8/26.
 */
public interface DeleteOrderApi {
    void deleteOrder(int orderId, Callback callback);

    interface Callback {

        void onFinish();

        void onError(int errType, String errMsg);
    }
}