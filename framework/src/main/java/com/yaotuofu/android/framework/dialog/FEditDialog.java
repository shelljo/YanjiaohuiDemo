package com.yaotuofu.android.framework.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yaotuofu.android.framework.R;
import com.yaotuofu.android.framework.baseutils.KeyboardUtil;

/**
 * Created by Felix on 2016/4/25.
 */
public class FEditDialog extends Dialog {
    TextView dialogTitle;
    EditText mEditInput;
    Button dialogOk;
    Button dialogCancel;

    OnClickBackListener okListener;
    OnClickListener cancelListener;

    public interface OnClickBackListener {
        public void onClick(DialogInterface dialog, int which, String data);
    }

    public FEditDialog(Context context) {
        super(context, R.style.dialog);
        setContentView(R.layout.dialog_edit);
        dialogTitle = (TextView) findViewById(R.id.dialog_title);
        dialogTitle.setVisibility(View.GONE);
        mEditInput = (EditText) findViewById(R.id.edit_input);

        dialogOk = (Button) findViewById(R.id.btn_ok);
        dialogCancel = (Button) findViewById(R.id.btn_cancel);
        dialogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != okListener) {
                    okListener.onClick(FEditDialog.this, DialogInterface.BUTTON_POSITIVE,
                            mEditInput.getText().toString().trim());
                }
                dismiss();
            }
        });
        dialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != cancelListener) {
                    cancelListener.onClick(FEditDialog.this, DialogInterface.BUTTON_NEGATIVE);
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
        mEditInput.setText(msg);
        KeyboardUtil.openInputMethod(getContext(), mEditInput);
    }

    public void setOKButton(CharSequence okText, final OnClickBackListener okListener) {
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
