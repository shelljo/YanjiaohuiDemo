package zhuoyue.com.yanjiaohuidemo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import zhuoyue.com.yanjiaohuidemo.R;

/**
 * 这个是建议页面
 * */

public class SuggestActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mSug_Post_info,mSug_Conn_qq;
    private ImageView mSug_back_iv,mSug_post_ok,mSug_upload_pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggest);

        initView();

        mSug_back_iv.setOnClickListener(this);
        mSug_upload_pic.setOnClickListener(this);

    }

    private void initView() {

        mSug_Post_info = (EditText) findViewById(R.id.sug_post_et);
        mSug_Conn_qq = (EditText) findViewById(R.id.sug_conn_qq);
        mSug_back_iv = (ImageView) findViewById(R.id.sug_back_iv);
        mSug_post_ok = (ImageView) findViewById(R.id.sug_post_ok);
        mSug_upload_pic = (ImageView) findViewById(R.id.sug_upload_pic);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            //点击返回按钮
            case  R.id.sug_back_iv:

                finish();

                break;

            //点击提交按钮
            case R.id.sug_post_ok:

                if (mSug_Post_info != null  ) {

                }
                break;
            case R.id.sug_upload_pic:


                break;

        }

    }
}
