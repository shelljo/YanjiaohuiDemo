package com.yaotuofu.android.framework.base;

/**
 * Created by FelixFang on 5/23/16.
 */
public interface BaseView<T extends BasePresenter> {

    /**
     * 使用fragment作为view时,将activity的presenter传递给fragment。
     */
    void setPresenter(T presenter);
}
