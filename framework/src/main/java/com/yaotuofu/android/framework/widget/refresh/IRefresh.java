package com.yaotuofu.android.framework.widget.refresh;

import android.view.View;

/**
 * Created by Felix on 2016/4/14.
 */
public interface IRefresh {
    /**
     * 获取下拉刷新的控件
     *
     * @return
     */
    public View getRefrshView();

    public void setRefreshing(boolean refreshing);


    public interface IRefreshListener {
        void onRefresh();
    }

    public void setIRefreshListener(IRefreshListener listener);
}
