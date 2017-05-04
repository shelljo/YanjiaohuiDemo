package zhuoyue.com.yanjiaohuidemo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import zhuoyue.com.yanjiaohuidemo.R;
import zhuoyue.com.yanjiaohuidemo.entity.SmsCallBackEntity;
import zhuoyue.com.yanjiaohuidemo.util.MD5util;
import zhuoyue.com.yanjiaohuidemo.util.MyCountDownTimer;
import zhuoyue.com.yanjiaohuidemo.util.MyLog;
import zhuoyue.com.yanjiaohuidemo.util.MyToast;
import zhuoyue.com.yanjiaohuidemo.util.NetWorkApi;

public class ForgetPasswordActivity extends AppCompatActivity {
    private EditText mForget_Phone,mForget_num;
    private Button mForget_get_phone_num,mForget_ok,mGoto_Login;
    private MyCountDownTimer mMyCountDownTimer;
    private NetWorkApi mNetWorkApi=new NetWorkApi();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        initView();

        mMyCountDownTimer = new MyCountDownTimer(6000, 10, mForget_get_phone_num);

        //点击获取验证码
        mForget_get_phone_num.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            mNetWorkApi.GetSmsNum(mForget_Phone.getText().toString(), new Callback<SmsCallBackEntity>() {
                @Override
                public void onResponse(Call<SmsCallBackEntity> call, Response<SmsCallBackEntity> response) {
                    SmsCallBackEntity body = response.body();
                    if (body != null) {
                        if (body.getBack().equals("true")) {
                            MyToast.showShort(ForgetPasswordActivity.this,"验证码下发成功，请注意查收");
                        }
                    }else {
                        Toast.makeText(ForgetPasswordActivity.this, "手机号不正确", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<SmsCallBackEntity> call, Throwable t) {
                    MyLog.d("error","phone_num error:"+t.getMessage());
                }
            });
            }
        });
       //点击确定
        mForget_ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mNetWorkApi.CheckNum(mForget_Phone.getText().toString(),mForget_num.getText().toString(), new Callback<SmsCallBackEntity>() {
                    @Override
                    public void onResponse(Call<SmsCallBackEntity> call, Response<SmsCallBackEntity> response) {
                        SmsCallBackEntity body = response.body();
                        if (body != null) {
                            if (body.getBack().equals("true")) {

                                Intent intent = new Intent(ForgetPasswordActivity.this,SettingPasswordActivity.class);
                                intent.putExtra("mobile", mForget_Phone.getText().toString());
                                intent.putExtra("num", mForget_num.getText().toString());
                                startActivity(intent);

                            }
                        }else {
                            Toast.makeText(ForgetPasswordActivity.this, "手机号不正确", Toast.LENGTH_SHORT).show();

                        }
                    }
                    @Override
                    public void onFailure(Call<SmsCallBackEntity> call, Throwable t) {
                        MyLog.d("error","phone_num error:"+t.getMessage());
                    }
                });


            }
        });
        //点击去登陆
        mGoto_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ForgetPasswordActivity.this,LoginActivity.class));

            }
        });

    }

    private void initView() {

        mForget_Phone = (EditText) findViewById(R.id.forget_phone);
        mForget_num = (EditText) findViewById(R.id.forget_num);
        mForget_get_phone_num = (Button) findViewById(R.id.forget_get_num);
        mForget_ok = (Button) findViewById(R.id.forget_ok);
        mGoto_Login = (Button) findViewById(R.id.go_to_login);

    }
    private String Md5HandleData(String string) {
        String encrypt = MD5util.encrypt(string);
        String s = encrypt + "yanjiaohui".toString();
        String encrypt1 = MD5util.encrypt(s);
        return encrypt1;
    }

}