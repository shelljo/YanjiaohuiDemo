package zhuoyue.com.yanjiaohuidemo.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import zhuoyue.com.yanjiaohuidemo.R;
import zhuoyue.com.yanjiaohuidemo.base.BaseActivity;
import zhuoyue.com.yanjiaohuidemo.entity.LoginCallBackEntity;
import zhuoyue.com.yanjiaohuidemo.entity.LoginInfoEntity;
import zhuoyue.com.yanjiaohuidemo.util.MD5util;
import zhuoyue.com.yanjiaohuidemo.util.MyLog;
import zhuoyue.com.yanjiaohuidemo.util.MyToast;
import zhuoyue.com.yanjiaohuidemo.util.NetWorkApi;

/**
 * 这个是登录界面
 * */

public class LoginActivity extends BaseActivity {
    private EditText mLogin_Phone,mLogin_Password;
    private Button mLogin_Register,mGoto_Register;
    private NetWorkApi mNetWorkApi=new NetWorkApi();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();



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


                mNetWorkApi.PostLoginData(mLogin_Phone.getText().toString(), Md5Handle(mLogin_Password.getText().toString()),
                        new Callback<LoginCallBackEntity>() {
                            @Override
                            public void onResponse(Call<LoginCallBackEntity> call, Response<LoginCallBackEntity> response) {
                                LoginCallBackEntity body = response.body();
                                MyToast.showLong(LoginActivity.this,"手机 ："+mLogin_Phone.getText().toString()+",密码 ："+mLogin_Password.getText().toString());
                                if (body != null) {
                                    if (body.getBack().equals("true")) {
                                        MyToast.showShort(LoginActivity.this,"登录成功");
                                        LoginInfoEntity info = body.getInfo();

                                        MyLog.d("flag","用户信息："+info.getUser_name());
                                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                    }else {
                                        if (body.getBack().equals("false")) {
                                            MyToast.showShort(LoginActivity.this,"登录失败");
                                        }
                                    }


                                }else {
                                    MyLog.d("flag","body null");
                                }
                            }

                            @Override
                            public void onFailure(Call<LoginCallBackEntity> call, Throwable t) {
                                MyLog.d("flag error","error :"+t.getMessage());

                            }
                        });

                break;

        }


    }
    //忘记密码，点击事件。
    public void forget_password(View view) {

        startActivity(new Intent(LoginActivity.this,ForgetPasswordActivity.class));

    }

    public String Md5Handle(String string){
        String encrypt = MD5util.encrypt(string);
        String s = encrypt + "yanjiaohui".toString();
        String encrypt1 = MD5util.encrypt(s);
        return encrypt1;
    }

}
