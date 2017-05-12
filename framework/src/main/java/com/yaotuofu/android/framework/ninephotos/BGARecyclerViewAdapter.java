package com.yaotuofu.android.framework.ninephotos;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/1.
 */

public abstract class BGARecyclerViewAdapter <M> extends RecyclerView.Adapter<BGARecyclerViewHolder> {
    protected final int mItemLayoutId;
    protected Context mContext;
    protected List<M> mDatas;
    protected BGAOnItemChildClickListener mOnItemChildClickListener;
    protected BGAOnItemChildLongClickListener mOnItemChildLongClickListener;
    protected BGAOnItemChildCheckedChangeListener mOnItemChildCheckedChangeListener;
    protected BGAOnRVItemClickListener mOnRVItemClickListener;
    protected BGAOnRVItemLongClickListener mOnRVItemLongClickListener;
    protected RecyclerView mRecyclerView;

    public BGARecyclerViewAdapter(RecyclerView recyclerView, int itemLayoutId) {
        this.mRecyclerView = recyclerView;
        this.mContext = this.mRecyclerView.getContext();
        this.mItemLayoutId = itemLayoutId;
        this.mDatas = new ArrayList();
    }

    public int getItemCount() {
        return this.mDatas.size();
    }

    public BGARecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BGARecyclerViewHolder viewHolder = new BGARecyclerViewHolder(this.mRecyclerView, LayoutInflater.from(this.mContext).inflate(this.mItemLayoutId, parent, false), this.mOnRVItemClickListener, this.mOnRVItemLongClickListener);
        viewHolder.getViewHolderHelper().setOnItemChildClickListener(this.mOnItemChildClickListener);
        viewHolder.getViewHolderHelper().setOnItemChildLongClickListener(this.mOnItemChildLongClickListener);
        viewHolder.getViewHolderHelper().setOnItemChildCheckedChangeListener(this.mOnItemChildCheckedChangeListener);
        this.setItemChildListener(viewHolder.getViewHolderHelper());
        return viewHolder;
    }

    protected void setItemChildListener(BGAViewHolderHelper viewHolderHelper) {
    }

    public void onBindViewHolder(BGARecyclerViewHolder viewHolder, int position) {
        this.fillData(viewHolder.getViewHolderHelper(), position, this.getItem(position));
    }

    protected abstract void fillData(BGAViewHolderHelper var1, int var2, M var3);

    public void setOnRVItemClickListener(BGAOnRVItemClickListener onRVItemClickListener) {
        this.mOnRVItemClickListener = onRVItemClickListener;
    }

    public void setOnRVItemLongClickListener(BGAOnRVItemLongClickListener onRVItemLongClickListener) {
        this.mOnRVItemLongClickListener = onRVItemLongClickListener;
    }

    public void setOnItemChildClickListener(BGAOnItemChildClickListener onItemChildClickListener) {
        this.mOnItemChildClickListener = onItemChildClickListener;
    }

    public void setOnItemChildLongClickListener(BGAOnItemChildLongClickListener onItemChildLongClickListener) {
        this.mOnItemChildLongClickListener = onItemChildLongClickListener;
    }

    public void setOnItemChildCheckedChangeListener(BGAOnItemChildCheckedChangeListener onItemChildCheckedChangeListener) {
        this.mOnItemChildCheckedChangeListener = onItemChildCheckedChangeListener;
    }

    public M getItem(int position) {
        return this.mDatas.get(position);
    }

    public List<M> getDatas() {
        return this.mDatas;
    }

    public void addNewDatas(List<M> datas) {
        if(datas != null) {
            this.mDatas.addAll(0, datas);
            this.notifyItemRangeInserted(0, datas.size());
        }

    }

    public void addMoreDatas(List<M> datas) {
        if(datas != null) {
            this.mDatas.addAll(this.mDatas.size(), datas);
            this.notifyItemRangeInserted(this.mDatas.size(), datas.size());
        }

    }

    public void setDatas(List<M> datas) {
        if(datas != null) {
            this.mDatas = datas;
        } else {
            this.mDatas.clear();
        }

        this.notifyDataSetChanged();
    }

    public void clear() {
        this.mDatas.clear();
        this.notifyDataSetChanged();
    }

    public void removeItem(int position) {
        this.mDatas.remove(position);
        this.notifyItemRemoved(position);
    }

    public void removeItem(M model) {
        this.removeItem(this.mDatas.indexOf(model));
    }

    public void addItem(int position, M model) {
        this.mDatas.add(position, model);
        this.notifyItemInserted(position);
    }

    public void addFirstItem(M model) {
        this.addItem(0, model);
    }

    public void addLastItem(M model) {
        this.addItem(this.mDatas.size(), model);
    }

    public void setItem(int location, M newModel) {
        this.mDatas.set(location, newModel);
        this.notifyItemChanged(location);
    }

    public void setItem(M oldModel, M newModel) {
        this.setItem(this.mDatas.indexOf(oldModel), newModel);
    }

    public void moveItem(int fromPosition, int toPosition) {
        this.mDatas.add(toPosition, this.mDatas.remove(fromPosition));
        this.notifyItemMoved(fromPosition, toPosition);
    }
}
