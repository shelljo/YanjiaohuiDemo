package com.yaotuofu.android.framework.recyclerview.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * User:lizhangqu(513163535@qq.com)
 * Date:2015-11-29
 * Time: 19:44
 */
public abstract class GridLayoutAdapter<T extends RecyclerViewAdapter.Item> extends RecyclerViewAdapter<T> {

    public GridLayoutAdapter(List list) {
        super(list);
    }
    public GridLayoutAdapter(List list, int headerViewRes) {
        super(list, headerViewRes);
    }

    public GridLayoutAdapter(List list, int headerViewRes, int footerViewRes) {
        super(list, headerViewRes, footerViewRes);
    }

    private GridSpanSizeLookup mGridSpanSizeLookup;
    private GridLayoutManager gridManager;
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            gridManager = ((GridLayoutManager) manager);
            if (mGridSpanSizeLookup == null) {
                mGridSpanSizeLookup = new GridSpanSizeLookup();
            }
            gridManager.setSpanSizeLookup(mGridSpanSizeLookup);
        }
    }

    class GridSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {
        @Override
        public int getSpanSize(int position) {
            if (isHeader(position) || isFooter(position)) {
                return gridManager.getSpanCount();
            }
            return 1;
        }
    }


}
