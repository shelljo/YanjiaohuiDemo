package com.yaotuofu.android.framework.network.xrop;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by FelixFang on 10/31/16.
 */

public class XNetwork {


    // 通过XRopRequestQueue唯一索引网络请求context和network任务
    private static Map<XRequestQueue, Map<Object, XNetwork>> mDataMap = new HashMap();
    private XRequestQueue mRequestQueue;
    private Object mTag;

    private XNetwork(Object tag, XRequestQueue queue) {
        this.mTag = tag;
        this.mRequestQueue = queue;
    }

    public static XNetwork newInstance(Object tag, XRequestQueue queue) {
        if(null == tag) {
            throw new IllegalArgumentException("tag can not null");
        }

        Map<Object, XNetwork> map = mDataMap.get(queue);
        XNetwork network;
        if (null == map) {
            map = new HashMap<>();
            network = new XNetwork(tag, queue);
            map.put(tag, network);
            mDataMap.put(queue, map);
        } else {
            network = map.get(tag);
            if (network == null) {
                network = new XNetwork(tag, queue);
                map.put(tag, network);
            }
        }
        return network;
    }

    public static void removeInstance(Object tag) {
        for (XRequestQueue queue : mDataMap.keySet()) {
            mDataMap.get(queue).remove(tag);
        }
    }

    public static void cancelAll(Object tag) {
        for (XRequestQueue quene : mDataMap.keySet()) {
            quene.cancelAll(tag);
        }
    }

    public <T> XRequest<T> addRequest(XRequest<T> request) {
        request.setTag(mTag);
        mRequestQueue.add(request);
        return request;
    }


}
