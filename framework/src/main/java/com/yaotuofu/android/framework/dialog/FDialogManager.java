package com.yaotuofu.android.framework.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

/**
 * Created by Felix on 2016/4/25.
 */
public class FDialogManager {
    /**
     * 显示一个alert dialog
     *
     * @param context        要显示dialog的context
     * @param title          dialog标题，为空则不显示标题
     * @param msg            dialog 内容信息
     * @param okText         dialog确定按钮文字，传空则显示确定
     * @param okListener     dialog确定按钮事件监听
     * @param cancelText     dialog取消按按钮文字，传空则显示确定
     * @param cancelListener dialog取消按钮事件监听
     * @return Dialog
     */
    public static FAlertDialog showAlert(Context context, CharSequence title, CharSequence msg, CharSequence okText, DialogInterface.OnClickListener okListener, CharSequence cancelText, DialogInterface.OnClickListener cancelListener) {
        FAlertDialog dialog = new FAlertDialog(context);
        dialog.setTitle(title);
        dialog.setMessage(msg);
        dialog.setOKButton(okText, okListener);
        dialog.setCancelButton(cancelText, cancelListener);
        dialog.show();
        return dialog;
    }

    public static FEditDialog showEdit(Context context, CharSequence title, CharSequence msg, CharSequence okText, FEditDialog.OnClickBackListener okListener, CharSequence cancelText, DialogInterface.OnClickListener cancelListener) {
        FEditDialog dialog = new FEditDialog(context);
        dialog.setTitle(title);
        dialog.setOKButton(okText, okListener);
        dialog.setCancelButton(cancelText, cancelListener);
        dialog.show();
        dialog.setMessage(msg);
        return dialog;
    }

    /**
     * 显示一个tip dialog
     *
     * @param context    要显示dialog的context
     * @param title      dialog标题，为空则不显示标题
     * @param msg        dialog 内容信息
     * @param okText     dialog确定按钮文字，传空则显示确定
     * @param okListener dialog确定按钮事件监听
     * @return Dialog
     */
    public static FTipDialog showTip(Context context, CharSequence title, CharSequence msg, CharSequence okText, DialogInterface.OnClickListener okListener) {
        FTipDialog dialog = new FTipDialog(context);
        dialog.setTitle(title);
        dialog.setMessage(msg);
        dialog.setOKButton(okText, okListener);
        dialog.show();
        return dialog;
    }

    public static FAdDialog showAD(Context context, String url, View.OnClickListener listener) {
        FAdDialog dialog = new FAdDialog(context);
        dialog.setAd(url);
        dialog.setAdListener(listener);
        dialog.setOKButton("", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();
        return dialog;
    }

}
