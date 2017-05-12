package com.yaotuofu.android.framework.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yaotuofu.android.framework.R;

/**
 * Created by Administrator on 2017/4/5.
 */

public class CGTipDialog extends Dialog {
    TextView dialogTitle;
    TextView dialogContent;
    Button dialogOk;
    OnClickListener okListener;

    public CGTipDialog(Context context) {
        super(context, R.style.dialog);
        setContentView(R.layout.dialog_tip);
        dialogTitle = (TextView) findViewById(R.id.dialog_title);
        dialogTitle.setVisibility(View.GONE);
        dialogContent = (TextView) findViewById(R.id.dialog_content);

        dialogOk = (Button) findViewById(R.id.dialog_ok);

        dialogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != okListener) {
                    okListener.onClick(CGTipDialog.this, DialogInterface.BUTTON_POSITIVE);
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

    public void setonCanclelisener(OnCancelListener onCancelListener){
        this.setOnCancelListener(onCancelListener);
    }

    public void setMessage(CharSequence msg) {
        dialogContent.setText(msg);
    }

    public void setOKButton(CharSequence okText, final OnClickListener okListener) {
        if (!TextUtils.isEmpty(okText)) {
            dialogOk.setText(okText);
        }
        this.okListener = okListener;
    }
}
