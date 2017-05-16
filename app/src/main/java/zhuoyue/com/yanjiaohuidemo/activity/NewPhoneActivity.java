package zhuoyue.com.yanjiaohuidemo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import zhuoyue.com.yanjiaohuidemo.R;
import zhuoyue.com.yanjiaohuidemo.entity.SmsCallBackEntity;
import zhuoyue.com.yanjiaohuidemo.url.UrlConfig;
import zhuoyue.com.yanjiaohuidemo.util.CheckPhoneUtil;
import zhuoyue.com.yanjiaohuidemo.util.MyLog;
import zhuoyue.com.yanjiaohuidemo.util.MyToast;
import zhuoyue.com.yanjiaohuidemo.util.NetWorkApi;

public class NewPhoneActivity extends AppCompatActivity {
    private EditText mNewPhone_num,mNewPhone_check_num;
    private String mOldphone,mOldcode;
    private NetWorkApi mNetWorkApi;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_phone);

        Intent intent = getIntent();
        mOldphone = intent.getStringExtra("oldphone");
        mOldcode = intent.getStringExtra("oldcode");


        initView();
        mNetWorkApi = new NetWorkApi();

    }

    private void initView() {

        mNewPhone_num = (EditText) findViewById(R.id.new_phone_num);
        mNewPhone_check_num = (EditText) findViewById(R.id.new_phone_check_num);


    }

    //获取验证码
    public void new_phone_getcode(View view) {

        boolean matchered = CheckPhoneUtil.isMatchered(UrlConfig.MATCH_PHONE, mNewPhone_num.getText().toString());
        if (!matchered) {

            MyToast.showShort(NewPhoneActivity.this,"手机号码是不是输错了 O(∩_∩)O哈哈~");

        }else{
        mNetWorkApi.ChangeNum_Third_NewPhoneNum(mNewPhone_num.getText().toString(),
                new Callback<SmsCallBackEntity>() {
                    @Override
                    public void onResponse(Call<SmsCallBackEntity> call, Response<SmsCallBackEntity> response) {
                        SmsCallBackEntity body = response.body();
                        if (body != null) {
                            if (body.getBack().equals("true")) {

                                MyToast.showShort(NewPhoneActivity.this,"验证码发送成功，请注意查收");
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<SmsCallBackEntity> call, Throwable t) {
                    }
                });
        }
    }

    //确认按钮
    public void new_phone_ok(View view) {
        mNetWorkApi.ChangeSuccesful(mNewPhone_num.getText().toString(), mNewPhone_check_num.getText().toString(),
                mOldphone, mOldcode, new Callback<SmsCallBackEntity>() {
                    @Override
                    public void onResponse(Call<SmsCallBackEntity> call, Response<SmsCallBackEntity> response) {
                        SmsCallBackEntity body = response.body();

//                        private EditText mNewPhone_num,mNewPhone_check_num;
//                        private String mOldphone,mOldcode;
                        MyLog.d("change oldphone",mOldphone);
                        MyLog.d("change oldcode",mOldcode);
                        MyLog.d("change newphone ","body.newphone"+mNewPhone_num.getText().toString());
                        MyLog.d("change newpwd", "body.newpwd" + mNewPhone_check_num.getText().toString());


                        if (body != null) {
                            if (body.getBack().equals("true")) {
                                MyToast.showShort(NewPhoneActivity.this,"换绑成功");
                                startActivity(new Intent(NewPhoneActivity.this,MainActivity.class));
                            }else {
                                MyToast.showShort(NewPhoneActivity.this,"出了点问题呢");
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<SmsCallBackEntity> call, Throwable t) {

                    }
                });


    }

    public void finish_click(View view) {

        finish();

    }
}
