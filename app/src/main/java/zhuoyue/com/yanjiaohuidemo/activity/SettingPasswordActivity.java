package zhuoyue.com.yanjiaohuidemo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import zhuoyue.com.yanjiaohuidemo.R;
import zhuoyue.com.yanjiaohuidemo.entity.RegisterCallBackEntity;
import zhuoyue.com.yanjiaohuidemo.util.MD5util;
import zhuoyue.com.yanjiaohuidemo.util.MyLog;
import zhuoyue.com.yanjiaohuidemo.util.MyToast;
import zhuoyue.com.yanjiaohuidemo.util.NetWorkApi;

public class SettingPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mSet_password,mSet_password_again;
    private Button mOk;
    private NetWorkApi mNetWorkApi=new NetWorkApi();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_password);

        initView();

        mOk.setOnClickListener(this);

    }

    private void initView() {

        mSet_password = (EditText) findViewById(R.id.Set_password);
        mSet_password_again = (EditText) findViewById(R.id.set_password_again);
        mOk = (Button) findViewById(R.id.set_ok);

    }

    @Override
    public void onClick(View v) {
        if (mSet_password.getTextSize()<6) {
            MyToast.showShort(SettingPasswordActivity.this,"密码不能太短啊！");
            return;
        } else {
            if(!mSet_password.getText().equals(mSet_password_again.getText())){
                MyToast.showShort(SettingPasswordActivity.this,"两次输入的好像不一样呢！");
            }

        Intent intent = getIntent();
        String mobile = intent.getStringExtra("mobile");
        String num = intent.getStringExtra("num");

        MyLog.d("flag mobile",mobile);
        MyLog.d("flag num",num);
                        mNetWorkApi.ForgetPassword(mobile, num,
                                Md5Handle(mSet_password.getText().toString()),
                                new Callback<RegisterCallBackEntity>() {
                                    @Override
                                    public void onResponse(Call<RegisterCallBackEntity> call, Response<RegisterCallBackEntity> response) {
                                        RegisterCallBackEntity body = response.body();
                                        if (body != null) {
                                            if (body.getBack().equals("true")) {
                                                MyToast.showShort(SettingPasswordActivity.this,"修改密码成功");
                                                startActivity(new Intent(SettingPasswordActivity.this,LoginActivity.class));
                                            }
                                        }else {
                                            MyToast.showShort(SettingPasswordActivity.this,"body null");
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<RegisterCallBackEntity> call, Throwable t) {
                                      MyLog.i("error"," error T ："+t.getMessage());
                                    }
                                }
                        );
        }
    }
    private  String Md5Handle(String string){
        String encrypt = MD5util.encrypt(string);
        String s = encrypt + "yanjiaohui".toString();
        String encrypt1 = MD5util.encrypt(s);
        return encrypt1;
    }
}
