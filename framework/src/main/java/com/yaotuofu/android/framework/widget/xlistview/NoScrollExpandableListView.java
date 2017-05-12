package com.yaotuofu.android.framework.widget.xlistview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.ExpandableListView;
import android.widget.OverScroller;

/**
 * Created by FelixFang on 10/9/16.
 */

public class NoScrollExpandableListView extends ExpandableListView implements AbsListView.OnScrollListener {
    private final static int SCROLL_HEADER = 0;
    private final static int SCROLL_FOOTER = 1;

    public MoreListView.XListViewListener mListViewListener;

    private OverScroller mScroller;
    private int iScrollWhich = SCROLL_HEADER;

    FooterView mFooterView;
    int iFooterHeight;

    private float mStartY;
    private float mLastY;
    private float dy;
    private float EndRawy;

    public NoScrollExpandableListView(Context context) {
        super(context);
        initView(context);
    }

    public NoScrollExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public NoScrollExpandableListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @TargetApi(21)
    public NoScrollExpandableListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    /**
     * 设置不滚动
     */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);

    }

    private void initView(Context context) {
        mScroller = new OverScroller(context, new DecelerateInterpolator());
        mFooterView = new FooterView(context);
        mFooterView.setFooterHeight(200);
        //隐藏footview
        mFooterView.setPadding(0, 0, 0, 0 - 200);
        addFooterView(mFooterView);
        mFooterView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //获取测量的footview的高度
                iFooterHeight = mFooterView.getMeasuredHeight();
                getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //按下的时候我们需要记住起始点的Y轴的坐标
                mLastY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                //移动的时候获取当前的y轴的坐标
                mStartY = ev.getY();
                //计算滑动的距离
                dy = mStartY - mLastY;
                //再次获取一次，因为我们是增量增加宽度的，每次获取的是相对于上一点的位移
                mLastY = ev.getY();
                if (getLastVisiblePosition() == getCount() - 1 && (mFooterView.getFooterHeight() > 0 || dy < 0)) {
                    //通过改变footview的高度
                    updateFooterState(-dy);
                }
                break;
            case MotionEvent.ACTION_UP:
                EndRawy = ev.getRawY();
                //如果当前已经显示到最后一个条目了就是上拉加载，并且当前footview的高度大于0，移动的距离也大于0
                if (getLastVisiblePosition() == getCount() - 1 && (mFooterView.getFooterHeight() > 0 || dy < 0)) {
                    //判断当前的状态不是正在刷新的状态就设置为刷新的状态
                    if (mFooterView.getCurrentState() != LoadState.LOADING) {
                        if (mFooterView.getFooterHeight() > iFooterHeight) {
                            mFooterView.setFooterState(LoadState.LOADING);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    //调用是界面的刷新,延时1秒
                                    if(null != mListViewListener) {
                                        mListViewListener.onLoadMore();
                                    }
                                    resetFooter();
                                }
                            }, 2000);
                        }
                    }
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }

    private void updateFooterState(float delta) {
        if (null == mFooterView) {
            return;
        }
        //设置footview的高度移动的时候，这个高度是增加每次移动的点相当于上一个点的位置的距离
        mFooterView.setFooterHeight((int) (delta + mFooterView.getFooterHeight()));
        //当前的状态不是在刷新的状态！
        if (mFooterView.getCurrentState() != LoadState.LOADING) {
            if (mFooterView.getFooterHeight() > iFooterHeight) {
                //设置当前的显示的内容为松开加载更多
                mFooterView.setFooterState(LoadState.WILL_RELEASE);
            } else {
                mFooterView.setFooterState(LoadState.NORMAL);
            }
        }
    }

    private void resetFooter() {
        //重置刷新状态
        mFooterView.setFooterState(LoadState.NORMAL);
        if (null == mFooterView) {
            return;
        }
        int height = mFooterView.getMeasuredHeight();
        if (height == 0) {
            return;
        }
        int finalHeight = 0;
        if (height > iFooterHeight && mFooterView.getCurrentState() != LoadState.NORMAL) {
            finalHeight = iFooterHeight;
        } else if (mFooterView.getCurrentState() == LoadState.LOADING) {
            return;
        }
        iScrollWhich = SCROLL_FOOTER;
        mScroller.startScroll(0, height, 0, finalHeight - height, 300);
        invalidate();
    }

    @Override
    public void computeScroll() {
        //判断是否滑动结束
        if (mScroller.computeScrollOffset()) {
            if (iScrollWhich == SCROLL_FOOTER) {
                //不断去改变footview的高度
                mFooterView.setFooterHeight(mScroller.getCurrY());
//                if(mScroller.getCurrY()>200){
//                    mFooterView.setFooterHeight(200);
//                }else{
//                    mFooterView.setFooterHeight(mScroller.getCurrY());
//                }

            }
            invalidate();
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if(mScroller.getCurrY() >= 200) {
            mScroller.forceFinished(true);
        }

    }

    public void setListViewListener(MoreListView.XListViewListener mListViewListener) {
        this.mListViewListener = mListViewListener;
    }
}
