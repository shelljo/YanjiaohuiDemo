package zhuoyue.com.yanjiaohuidemo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import zhuoyue.com.yanjiaohuidemo.R;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mSetting_back;

    private LinearLayout mSetting_sug,mSetting_about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initView();
        mSetting_back.setOnClickListener(this);
        mSetting_sug.setOnClickListener(this);
        mSetting_about.setOnClickListener(this);

    }

    private void initView() {

        mSetting_back = (TextView) findViewById(R.id.setting_back);
        mSetting_sug = (LinearLayout) findViewById(R.id.setting_sug);
        mSetting_about = (LinearLayout) findViewById(R.id.setting_about);

    }

    //点击退出登录。
    public void exit_register(View view) {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //这个是回退箭头的点击事件
            case R.id.setting_back:
                finish();
                break;
            //这个反馈建议
            case R.id.setting_sug:
            startActivity(new Intent(SettingActivity.this,SuggestActivity.class));
                break;
            //这个是关于燕郊汇
            case R.id.setting_about:
            startActivity(new Intent(SettingActivity.this,AboutActivity.class));
                break;



        }


    }
}
