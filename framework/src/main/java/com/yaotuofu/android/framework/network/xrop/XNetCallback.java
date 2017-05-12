package com.yaotuofu.android.framework.network.xrop;



/**
 * 网络回调接口
 * 1、onStart
 * 2、onFinish
 * 3、onSuccess
 * 4、onError
 * 5、onNoNetwork
 * 6、onCancel
 *
 * Created by Felix on 2016/3/25.
 */
public interface XNetCallback<T> {
    /**
     * 在执行请求前执行
     */
    void onStart(XRequest request);

    /**
     * 请求结束后执行，包括执行成功和失败
     */
    void onFinish(XRequest request);

    /**
     * 成功返回并解析数据
     */
    void onSuccess(XRequest request, T data);

    /**
     * 包括所有请求失败情况
     */
    void onError(XRequest request, String volleyError);

    /**
     * 无网络
     */
    void onNoNetwork(XRequest request);

    /**
     * 取消操作
     */
    void onCancel(XRequest request);
}
