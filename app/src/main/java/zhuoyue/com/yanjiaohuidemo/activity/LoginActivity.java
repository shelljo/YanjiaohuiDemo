package zhuoyue.com.yanjiaohuidemo.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import zhuoyue.com.yanjiaohuidemo.R;
import zhuoyue.com.yanjiaohuidemo.base.BaseActivity;
import zhuoyue.com.yanjiaohuidemo.util.MyToast;

/**
 * 这个是登录界面
 * */

public class LoginActivity extends BaseActivity {
    private EditText mLogin_Phone,mLogin_Password;
    private Button mLogin_Register,mGoto_Register;
    private String mPhone,mPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        //获取用户输入的信息，
        initLoginInfo();


    }

    private void initLoginInfo() {

        mPhone=mLogin_Phone.getText().toString();
        mPassword=mLogin_Password.getText().toString();


    }

    private void initView() {

        mLogin_Phone = (EditText) findViewById(R.id.login_phone_number);
        mLogin_Password = (EditText) findViewById(R.id.login_password);
        mLogin_Register = (Button) findViewById(R.id.login_login);
        mGoto_Register = (Button) findViewById(R.id.login_goto_register);

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

    // 按钮点击事件
    public void register_click(View view) {
        switch (view.getId()) {
            case R.id.login_goto_register:
    //点击去注册页面
                startActivity(new Intent(LoginActivity.this,PhoneRegistActivity.class));

                break;
    //点击登录，上交数据
            case R.id.login_login:

                MyToast.showLong(LoginActivity.this,"手机 ："+mLogin_Phone.getText().toString()+",密码 ："+mLogin_Password.getText().toString());

                break;

        }



    }
}
