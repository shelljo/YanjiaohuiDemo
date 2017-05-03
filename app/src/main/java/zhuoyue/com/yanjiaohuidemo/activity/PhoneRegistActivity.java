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
import zhuoyue.com.yanjiaohuidemo.entity.RegisterCallBackEntity;
import zhuoyue.com.yanjiaohuidemo.entity.SmsCallBackEntity;
import zhuoyue.com.yanjiaohuidemo.util.MD5util;
import zhuoyue.com.yanjiaohuidemo.util.MyCountDownTimer;
import zhuoyue.com.yanjiaohuidemo.util.MyLog;
import zhuoyue.com.yanjiaohuidemo.util.NetWorkApi;

public class PhoneRegistActivity extends AppCompatActivity {
    private EditText mRegister_Phone,mRegister_Num,mRegister_Pwd;
    private Button mGet_Num,mOk;
    private MyCountDownTimer mMyCountDownTimer;
    private NetWorkApi mNetWorkApi=new NetWorkApi();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_regist);

        initView();

        mMyCountDownTimer = new MyCountDownTimer(6000, 10, mGet_Num);

        //获取验证码的点击事件
        mGet_Num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mMyCountDownTimer.start();

                mNetWorkApi.GetSmsNum(mRegister_Phone.getText().toString(), new Callback<SmsCallBackEntity>() {
                    @Override
                    public void onResponse(Call<SmsCallBackEntity> call, Response<SmsCallBackEntity> response) {
                        SmsCallBackEntity body = response.body();
                        if (body != null) {
//                            MyLog.d("短信：back",body.getBack());
//                            MyLog.d(body.getError());
//                            MyLog.d("短信 info",body.getInfo());

                        }else {
                            Toast.makeText(PhoneRegistActivity.this, "body null", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<SmsCallBackEntity> call, Throwable t) {
                        MyLog.d(t.getMessage());
                    }
                });

            }
        });

        //点击确定的点击事件
        mOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             mNetWorkApi.PostRegisterData(mRegister_Phone.getText().toString(),
                     mRegister_Num.getText().toString(),
                     Md5HandleData(mRegister_Pwd.getText().toString()),
                     new Callback<RegisterCallBackEntity>() {
                         @Override
                         public void onResponse(Call<RegisterCallBackEntity> call, Response<RegisterCallBackEntity> response) {
                             MyLog.d("flag 电话",mRegister_Phone.getText().toString());
                             MyLog.d("flag 验证码",mRegister_Num.getText().toString());
                             MyLog.d("flag 密码 ",mRegister_Pwd.getText().toString());

                             RegisterCallBackEntity body = response.body();
                             if (body != null) {
                                 MyLog.d("注册back:",body.getBack());
//                                 MyLog.d(body.getError());
                                 MyLog.d("注册info:",body.getInfo());

                             }else {
                                 Toast.makeText(PhoneRegistActivity.this, "body null", Toast.LENGTH_SHORT).show();
                             }
                         }

                         @Override
                         public void onFailure(Call<RegisterCallBackEntity> call, Throwable t) {
                             MyLog.d("flag error","error:"+t.getMessage());
                         }
                     }
             );

            }
        });

    }

    private String Md5HandleData(String string) {
        String encrypt = MD5util.encrypt(string);
        String s = encrypt + "yanjiaohui".toString();
        String encrypt1 = MD5util.encrypt(s);
        return encrypt1;
    }

    private void initView() {

        mRegister_Phone = (EditText) findViewById(R.id.register_phone);
        mRegister_Num = (EditText) findViewById(R.id.register_num);
        mRegister_Pwd = (EditText) findViewById(R.id.register_pwd);
        mGet_Num = (Button) findViewById(R.id.register_get_num);
        mOk = (Button) findViewById(R.id.register_ok);


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
