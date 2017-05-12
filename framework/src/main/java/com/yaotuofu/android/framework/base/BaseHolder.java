package com.yaotuofu.android.framework.base;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.yaotuofu.android.framework.controller.BaseActivity;


/**
 * R: activity
 * S: prsenter
 * T: response
 *
 * Created by Felix on 2016/4/18.
 */
public abstract class BaseHolder<R, S, T> {
    protected S mPresenter;
    protected R mActivity;
    protected ViewGroup mContainer;
    protected View mContentView;

    public BaseHolder(R activity, ViewGroup mContainer, S presenter) {
        this.mActivity = activity;
        this.mContainer = mContainer;
        this.mPresenter = presenter;
    }

    public void setContentView (View contentView) {
        this.mContentView = contentView;
    }

    public View getContentView() {
        return mContentView;
    }

    public void attachContainer() {
        if(null == mContainer.getChildAt(0)) {
            mContainer.addView(mContentView);
            mContainer.setVisibility(View.VISIBLE);
        }
    }

    public abstract void fetchData();

    public abstract void showData(T response);

    /**
     * Android M运行时权限请求封装
     * @param permissionDes 权限描述
     * @param runnable 请求权限回调
     * @param permissions 请求的权限（数组类型），直接从Manifest中读取相应的值，比如Manifest.permission.WRITE_CONTACTS
     */
    public void performCodeWithPermission(@NonNull String permissionDes, BaseActivity.PermissionCallback runnable, @NonNull String... permissions){
        if(mActivity !=null && mActivity instanceof BaseActivity){
            ((BaseActivity) mActivity).performCodeWithPermission(permissionDes,runnable,permissions);
        }
    }
}
