package com.yaotuofu.android.framework.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.yaotuofu.android.framework.R;
import com.yaotuofu.android.framework.image.FSimpleDraweeView;

/**
 * Created by Felix on 2016/3/28.
 */
public class FAdDialog extends Dialog {

    View.OnClickListener adListener;
    OnClickListener okListener;

    FSimpleDraweeView mAd;
    TextView mClose;

    public FAdDialog(Context context) {
        super(context, R.style.dialog);
        setContentView(R.layout.dialog_ad);
        mAd = (FSimpleDraweeView) findViewById(R.id.image_ad_dialog);
        mAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != adListener) {
                    adListener.onClick(v);
                }
            }
        });
        mClose = (TextView) findViewById(R.id.text_ad_dialog_close);

        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != okListener) {
                    okListener.onClick(FAdDialog.this, DialogInterface.BUTTON_POSITIVE);
                }
                dismiss();
            }
        });
    }

    public void setAd(String uri) {
        if(null != uri) {
            mAd.setURI(uri);
        }
    }

    public void setOKButton(CharSequence okText, final OnClickListener okListener) {
        if (!TextUtils.isEmpty(okText)) {
            mClose.setText(okText);
        }
        this.okListener = okListener;
    }

    public void setAdListener(View.OnClickListener adListener) {
        this.adListener = adListener;
    }
}
