package com.yaotuofu.android.framework.network.xrop;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;

import com.yaotuofu.android.framework.R;
import com.yaotuofu.android.framework.application.BaseApplication;
import com.yaotuofu.android.framework.baseutils.ToastUtils;
import com.yaotuofu.android.framework.dialog.CGTipDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/5.
 */

public abstract class CGNetCallback<T> implements XNetCallback<T> {
    private LoadingController mLoadingController;

    private ErrorHandler mErrorHandler;
    public Activity activity;

    /**
     * 数据请求时的回调接口
     * 1、showLoading（）
     * 2、dismissLoading（）
     */

    public interface LoadingHandler {
        void showLoading();
        void dismissLoading();
    }

    /**
     * 错误处理回调接口
     * 1、handError(VolleyError)
     */
    public interface ErrorHandler {
        void handError(String volleyError);
    }

    public CGNetCallback(Activity activity) {
        this.mLoadingController = LoadingController.getInstance(new DialogLoadingHandler(activity));
        this.mErrorHandler = new DialogErrorHandler(activity);
        this.activity=activity;
    }

    public CGNetCallback(LoadingHandler loadingHandler, ErrorHandler errorHandler) {
        this.mLoadingController = LoadingController.getInstance(loadingHandler);
        this.mErrorHandler = errorHandler;
    }

    //    public CGNetCallback(LoadDowningHandler loadingHandler, ErrorHandler errorHandler) {
//        this.mLoadingController = LoadingController.getInstance(loadingHandler);
//        this.mErrorHandler = errorHandler;
//    }
    public CGNetCallback(Activity activity,T data) {
        this.mLoadingController = LoadingController.getInstance(new DialogLoadingHandler(activity));
        this.mErrorHandler = new DialogErrorHandler(activity);
        this.activity=activity;
    }

    /**
     * 继承类在此实现request准备逻辑
     */
    @Override
    public void onStart(XRequest request) {
        if (mLoadingController != null) {
            mLoadingController.controlShowLoading(request);
        }
    }


    /**
     * 继承类在此实现request完成逻辑
     */
    @Override
    public void onFinish(XRequest request) {
        if (mLoadingController != null) {
            mLoadingController.controlDismissLoading(request);
        }
    }
    /**
     * 继承类在此实现request成功逻辑
     */
    @Override
    public void onSuccess(XRequest request, T data) {
        onSuccess(data);
    }

    public void onSuccess(T data) {}

    /**
     * 继承类可以在此实现request错误处理
     */
    @Override
    public void onError(XRequest request, String volleyError) {
        if (mErrorHandler != null) {
            mErrorHandler.handError(volleyError);
        }
    }

    /**
     * 继承类可以在此实现request完成逻辑
     */
    @Override
    public void onNoNetwork(XRequest request) {
        ToastUtils.showShort(BaseApplication.getInstance(), R.string.no_network);
    }

    /**
     * 继承类可以在此实现request完成逻辑
     */
    @Override
    public void onCancel(XRequest request) {
    }

    /**
     * 弹出对话框处理错误 静态内部类 实现内部接口
     */
    public static class DialogErrorHandler implements ErrorHandler {
        Activity mActivity;
        boolean mIsFinishActivty = false; //点击确定是否关闭Activity;

        public DialogErrorHandler(Activity mActivity, boolean isFinshActivty) {
            this.mActivity = mActivity;
            this.mIsFinishActivty = isFinshActivty;
        }

        public DialogErrorHandler(Activity mActivity) {
            this(mActivity, false);
        }

        @Override
        public void handError(String volleyError) {
            CGTipDialog dialog = new CGTipDialog(mActivity);
            dialog.setTitle("出错了");
            //dialog.setMessage(VolleyUtils.getVolleyErrorMsg(volleyErro);

            dialog.setMessage("网络请求出错");

            dialog.setOKButton(null, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(mIsFinishActivty) {
                        mActivity.finish();
                    }
                }
            });
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    if(mIsFinishActivty) {
                        mActivity.finish();
                    }
                }
            });

            dialog.show();
        }


    }

    /**
     * 弹出Toast处理错误 静态内部类 实现内部接口
     */
  /*  public static class ToastErrorHandler implements ErrorHandler {
        @Override
        public void handError(String volleyError) {
            ToastUtils.showShort(BaseApplication.getInstance(),
                    VolleyUtils.getVolleyErrorMsg(volleyError));
        }
    }*/

    /**
     * 默认实现的以laoding dialog的方式展示loading
     */
    public static class DialogLoadingHandler implements LoadingHandler {
        Activity mActivity;
        Dialog mLoadingDialog;

        public DialogLoadingHandler(Activity activity) {
            this(activity, true);
        }

        public DialogLoadingHandler(Activity activity, boolean cancelable) {
            this.mActivity = activity;
            mLoadingDialog = createLoadingDialog();
            //如果可取消，则只能点后退键取消，不能通过点击外面取消
            mLoadingDialog.setCanceledOnTouchOutside(false);
            mLoadingDialog.setCancelable(cancelable);
        }

        /**
         * 创建对话框
         * 此类子类可以重写此方法
         */
        protected Dialog createLoadingDialog() {
            // 创建无标题无边框透明悬浮的对话框
            Dialog dialog = new Dialog(mActivity, R.style.dialog);
            // 布局一个进度条
            dialog.setContentView(R.layout.loading);
            return dialog;
        }

        @Override
        public void showLoading() {
            if (!mLoadingDialog.isShowing())
                mLoadingDialog.show();
        }

        @Override
        public void dismissLoading() {
            if (mLoadingDialog.isShowing())
                mLoadingDialog.dismiss();
        }
    }

    /**
     * 控制具体的LoadingHanldler的显示与隐藏
     * 可以处理多个请求共用一个request的情况
     * 包括一个请求成功后接着下一个请求，多个请求并发请求等（串联 并联）
     */
    public static class LoadingController {
        private static Map<LoadingHandler, LoadingController> map = new HashMap<>();
        LoadingHandler mLoadingHandler;
        private List<XRequest> mRequestList = new ArrayList<>();
        private Boolean isLoadingHandlerShowing = false;

        private LoadingController(LoadingHandler loadingHandler) {
            mLoadingHandler = loadingHandler;
            if (loadingHandler != null && loadingHandler instanceof DialogLoadingHandler) {
                //当dialog取消时，request也取消掉
                ((DialogLoadingHandler) loadingHandler).mLoadingDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        for (XRequest request : mRequestList) {
                            if (request != null) {
                                request.cancel();
                            }
                        }
                    }
                });
            }
        }

        public static LoadingController getInstance(LoadingHandler loadingHandler) {
            if (null == loadingHandler) {
                return null;
            }
            if (map.containsKey(loadingHandler)) {
                return map.get(loadingHandler);
            } else {
                LoadingController controller = new LoadingController(loadingHandler);
                map.put(loadingHandler, controller);
                return controller;
            }
        }

        public synchronized void  controlShowLoading(XRequest request) {
            mRequestList.add(request);
            if (mLoadingHandler != null && !isLoadingHandlerShowing) {
                mLoadingHandler.showLoading();
                isLoadingHandlerShowing = true;
            }
        }

        public synchronized void controlDismissLoading(XRequest request) {
            mRequestList.remove(request);
            // response已返回 并且需要下一次request 保持showloading
            if (request.hasHadResponseDelivered() && request.needLoadingStillAfterSucess()) {
                return;
            }
            //当使用该callback的所有请求都完成后，再取消loading
            if (mRequestList.isEmpty() && mLoadingHandler != null && isLoadingHandlerShowing) {
                mLoadingHandler.dismissLoading();
                isLoadingHandlerShowing = false;
                map.remove(mLoadingHandler);
            }
        }
    }
}
