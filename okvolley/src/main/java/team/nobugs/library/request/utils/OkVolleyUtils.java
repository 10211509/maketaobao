package team.nobugs.library.request.utils;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.squareup.okhttp.OkHttpClient;

import team.nobugs.library.request.OkHttpStack;

/**
 * Created by xiayong on 2015/8/12.
 */
public class OkVolleyUtils {

    // Volley request queue
    private static RequestQueue mRequestQueue;

    //init the RequestQueue
    public static void init(Context context) {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(context, new OkHttpStack(new OkHttpClient()));
        }
    }

    public static RequestQueue getVolleyRequestQueue() {
        if (mRequestQueue == null) {
            throw new IllegalStateException("mRequestQueue is null,forget call init()?");
        }
        return mRequestQueue;
    }

    /**
     * Adds a request to the Volley request queue
     *
     * @param request is the request to add to the Volley queue
     */
    private static void addRequest(Request<?> request) {
        getVolleyRequestQueue().add(request);
    }

    /**
     * Adds a request to the Volley request queue with a given tag
     *
     * @param request is the request to be added
     * @param tag     tag identifying the request
     */
    public static void addRequest(Request<?> request, String tag) {
        request.setTag(tag);
        addRequest(request);
    }

    /**
     * Cancels all the request in the Volley queue for a given tag
     *
     * @param tag associated with the Volley requests to be cancelled
     */
    public static void cancelAllRequests(String tag) {
        if (getVolleyRequestQueue() != null) {
            getVolleyRequestQueue().cancelAll(tag);
        }
    }
}
