package com.yaotuofu.android.framework.base;

import android.content.Context;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by FelixFang on 10/22/16.
 */

public abstract class SimpleTypeAdapter<T>  extends BaseAdapter {
    protected Context mContext;
    protected List<T> mList;

    public SimpleTypeAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setList(List<T> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return (null == mList) ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}

