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

public class ChangeNumActivity extends AppCompatActivity {
    private EditText mChange_num_oldphone,mChange_num_check_num;
    private NetWorkApi mNetWorkApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_num);

        initView();

        mNetWorkApi=new NetWorkApi();

    }

    private void initView() {

        mChange_num_oldphone = (EditText) findViewById(R.id.change_num_oldphone);
        mChange_num_check_num = (EditText) findViewById(R.id.change_num_check_num);

    }


    //点击下一步
    public void change_num_next_click(View view) {
        mNetWorkApi.ChangeNum_Second_Check_PhoneNum(mChange_num_oldphone.getText().toString(), mChange_num_check_num.getText().toString(),
                new Callback<SmsCallBackEntity>() {
                    @Override
                    public void onResponse(Call<SmsCallBackEntity> call, Response<SmsCallBackEntity> response) {
                        SmsCallBackEntity body = response.body();
                        MyLog.d("change oldphone",mChange_num_oldphone.getText().toString());
                        MyLog.d("change oldcode",mChange_num_check_num.getText().toString());
                        MyLog.d("change ","body.getBack"+body.getBack());
                        MyLog.d("change","body.getError"+body.getError());

                        if (body != null) {
                            if (body.getBack().equals("true")) {
                                Intent intent = new Intent(ChangeNumActivity.this,NewPhoneActivity.class);
                                intent.putExtra("oldphone", mChange_num_oldphone.getText().toString());
                                intent.putExtra("oldcode", mChange_num_check_num.getText().toString());
                                startActivity(intent);
                            }else {

                                MyToast.showShort(ChangeNumActivity.this,"验证码好像不符合呢");
                            }
                        }

                    }
                    @Override
                    public void onFailure(Call<SmsCallBackEntity> call, Throwable t) {
                    }
                });
    }

    //获取验证码
    public void change_num_getcode(View view) {
        boolean matchered = CheckPhoneUtil.isMatchered(UrlConfig.MATCH_PHONE, mChange_num_oldphone.getText().toString());
        if (!matchered) {
            MyToast.showShort(ChangeNumActivity.this,"手机号码是不是输错了 O(∩_∩)O哈哈~");
        }else {
            mNetWorkApi.ChangeNum_First_oldPhone(mChange_num_oldphone.getText().toString(),
                    new Callback<SmsCallBackEntity>() {
                        @Override
                        public void onResponse(Call<SmsCallBackEntity> call, Response<SmsCallBackEntity> response) {
                            SmsCallBackEntity body = response.body();
                            if (body != null) {
                                if (body.getBack().equals("true")) {
                                    MyToast.showShort(ChangeNumActivity.this,"验证码发送成功，请注意查收");
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<SmsCallBackEntity> call, Throwable t) {
                        }
                    });
        }
    }

    public void finish_click(View view) {

        finish();

    }
}
