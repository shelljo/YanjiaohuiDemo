package com.yaotuofu.android.framework.message;


import org.greenrobot.eventbus.EventBus;

/**
 * 消息模块
 * 1，用于模块间通信。
 * 2，用于转发推送消息
 * 用法：MsMessage.getInstance.sendMessage();
 * 由于eventbus的特性。需要接受时注册一下，并用onEventMainThread 等方法接受消息
 * Created by Felix on 2016/4/7.
 */
public class FMessagePie {
    private EventBus mBus = null;
    private static FMessagePie mInstance;

    public static FMessagePie getInstance() {
        if (mInstance == null) {
            synchronized (FMessagePie.class) {
                if (mInstance == null) {
                    mInstance = new FMessagePie();
                }
            }
        }
        return mInstance;
    }

    private FMessagePie() {
        this.mBus = EventBus.getDefault();
    }

    public void registListener(Object listener) {
        if ((listener != null) && (!isRegistered(listener)))
            this.mBus.register(listener);
    }

    public void unRegistListener(Object listener) {
        if (listener != null)
            this.mBus.unregister(listener);
    }

    public void sendMessage(IMessage message) {
        if (message != null)
            this.mBus.post(message);
    }

    public boolean isRegistered(Object listener) {
        return this.mBus.isRegistered(listener);
    }

}
