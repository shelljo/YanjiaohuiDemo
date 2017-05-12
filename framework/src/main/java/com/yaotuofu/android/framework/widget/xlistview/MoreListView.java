package com.yaotuofu.android.framework.widget.xlistview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.OverScroller;

import com.yaotuofu.android.framework.baseutils.L;

/**
 * 自定义自适应ScrollView的ListView
 * <p>
 * Created by Felix on 2016/4/21.
 */
public class MoreListView extends ListView implements AbsListView.OnScrollListener {
    private final static int SCROLL_HEADER = 0;
    private final static int SCROLL_FOOTER = 1;

    public XListViewListener mListViewListener;

    private OverScroller mScroller;
    private int iScrollWhich = SCROLL_HEADER;

    FooterView mFooterView;
    int iFooterHeight;

    private float mStartY;
    private float mLastY;
    private float dy;
    private float EndRawy;

    public interface XListViewListener {
        public void onLoadMore();
    }

    public MoreListView(Context context) {
        super(context);
        initView(context);
    }

    public MoreListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public MoreListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @TargetApi(21)
    public MoreListView(Context context, AttributeSet attrs, int defStyleAttr,
                        int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
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
                    L.d("action up s > 0");
                    if (mFooterView.getCurrentState() != LoadState.LOADING) {
                        L.d("action up not loading");
                        if (mFooterView.getFooterHeight() > iFooterHeight) {
                            L.d("action up height ok");
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
                            }, 1500);
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
            }
            invalidate();
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    public void setListViewListener(XListViewListener mListViewListener) {
        this.mListViewListener = mListViewListener;
    }
}
