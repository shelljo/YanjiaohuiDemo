package zhuoyue.com.yanjiaohuidemo.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import zhuoyue.com.yanjiaohuidemo.R;
import zhuoyue.com.yanjiaohuidemo.base.BaseActivity;
import zhuoyue.com.yanjiaohuidemo.util.MyToast;

/**
 * 这个是登录界面
 * */

public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



    }

     //三方登录
    public void Sanfang_click(View view) {
        switch (view.getId()) {
            case  R.id.login_weibo:
                MyToast.showShort(LoginActivity.this,"微博登录");

            break;
            case  R.id.login_qq:

                MyToast.showShort(LoginActivity.this,"QQ登录");

                break;
            case  R.id.login_weichat:

                MyToast.showShort(LoginActivity.this,"微信登录");

                break;
            case  R.id.login_sms:

//              startActivity(new Intent(LoginActivity.this,));
                MyToast.showShort(LoginActivity.this,"短信登录");
                break;

        }

    }

    // 点击跳转注册页面
    public void register_click(View view) {

        startActivity(new Intent(LoginActivity.this,PhoneRegistActivity.class));

    }
}
