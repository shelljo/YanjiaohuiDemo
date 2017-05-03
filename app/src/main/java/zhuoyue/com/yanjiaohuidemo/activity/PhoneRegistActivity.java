package zhuoyue.com.yanjiaohuidemo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import zhuoyue.com.yanjiaohuidemo.R;

public class PhoneRegistActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_regist);

    }

    //返回按钮点击事件
    public void back_click(View view) {

        finish();

    }
   //点击回到登录页面
    public void register_click(View view) {

        startActivity(new Intent(new Intent(PhoneRegistActivity.this,LoginActivity.class)));

    }
}
