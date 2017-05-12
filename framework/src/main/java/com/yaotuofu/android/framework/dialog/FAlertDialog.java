package com.yaotuofu.android.framework.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yaotuofu.android.framework.R;


/**
 * Created by Felix on 2016/4/25.
 */
public class FAlertDialog extends Dialog {
    TextView dialogTitle;
    TextView dialogContent;
    Button dialogOk;
    Button dialogCancel;

    OnClickListener okListener;
    OnClickListener cancelListener;


    public FAlertDialog(Context context) {
        super(context, R.style.dialog);
        setContentView(R.layout.dialog_alert);
        dialogTitle = (TextView) findViewById(R.id.dialog_title);
        dialogTitle.setVisibility(View.GONE);
        dialogContent = (TextView) findViewById(R.id.dialog_content);

        dialogOk = (Button) findViewById(R.id.btn_ok);
        dialogCancel = (Button) findViewById(R.id.btn_cancel);
        dialogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != okListener) {
                    okListener.onClick(FAlertDialog.this, DialogInterface.BUTTON_POSITIVE);
                }
                dismiss();
            }
        });
        dialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != cancelListener) {
                    cancelListener.onClick(FAlertDialog.this, DialogInterface.BUTTON_NEGATIVE);
                }
                dismiss();
            }
        });
    }

    @Override
    public void setTitle(CharSequence title) {
        if (!TextUtils.isEmpty(title)) {
            dialogTitle.setVisibility(View.VISIBLE);
            dialogTitle.setText(title);
        }

    }

    public void setMessage(CharSequence msg) {
        dialogContent.setText(msg);
        dialogContent.setGravity(Gravity.CENTER_HORIZONTAL);
    }

    public void setOKButton(CharSequence okText, final OnClickListener okListener) {
        if (!TextUtils.isEmpty(okText)) {
            dialogOk.setText(okText);
        }
        this.okListener = okListener;
    }


    public void setCancelButton(CharSequence cancelText, final OnClickListener cancelListener) {
        if (!TextUtils.isEmpty(cancelText)) {
            dialogCancel.setText(cancelText);
        }
        this.cancelListener = cancelListener;
    }
}
