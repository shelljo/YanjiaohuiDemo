package zhuoyue.com.yanjiaohuidemo.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

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

public class NewAddressActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private EditText mAddress_name,mAddress_phone,mAddress_room;
    private NetWorkApi mNetWorkApi;
    private RadioGroup mNewaddress__radiogroup;

    private String mSex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_address);

        initView();
        mNetWorkApi = new NetWorkApi();

        mNewaddress__radiogroup.setOnCheckedChangeListener(this);

    }


    private void initView() {

        mNewaddress__radiogroup= (RadioGroup) findViewById(R.id.newaddress_radiogroup);
        mAddress_name = (EditText) findViewById(R.id.address_name);
        mAddress_phone = (EditText) findViewById(R.id.address_phone_num);
        mAddress_room = (EditText) findViewById(R.id.address_room);

    }

    //返回按钮
    public void finish_click(View view) {
        finish();
    }

    //点击保存
    public void newaddress_save(View view) {
        if (TextUtils.isEmpty(mAddress_name.getText().toString())) {

            MyToast.showShort(NewAddressActivity.this,"用户名为空");

            return;
        }else {

        boolean matchered = CheckPhoneUtil.isMatchered(UrlConfig.MATCH_PHONE, mAddress_phone.getText().toString());
            if (!matchered) {
                MyToast.showShort(NewAddressActivity.this,"手机号码输入不正确");
            } else {

                SharedPreferences sp = getSharedPreferences("perinfo", Context.MODE_PRIVATE);
                mNetWorkApi.AddUserAddress( sp.getString("mobile", null), sp.getString("user_pwd", null),
                        mAddress_name.getText().toString(), mAddress_phone.getText().toString(), mAddress_room.getText().toString()
                        ,mSex==null? mSex="1":mSex, "116.788257", "39.972457",

                        new Callback<SmsCallBackEntity>() {

                            @Override
                            public void onResponse(Call<SmsCallBackEntity> call, Response<SmsCallBackEntity> response) {
                                SmsCallBackEntity body = response.body();

                                MyLog.d("flag sex",mSex);

                                if (body != null) {
                                    MyLog.d("flag","body getBack"+body.getBack());
                                    MyLog.d("flag","body getError"+body.getError());
                                    MyLog.d("flag","body getInfo"+body.getInfo());
                                    if (body.getBack().equals("true")){
                                        MyToast.showShort(NewAddressActivity.this,"保存成功");
                                        finish();
                                    }

                                }else {
                                    MyLog.d("flag","body null");
                                }

                            }

                            @Override
                            public void onFailure(Call<SmsCallBackEntity> call, Throwable t) {

                            }
                        });

            }
        }

    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

        switch (checkedId) {
            case R.id.newaddress_boy:
                mSex="1";
                MyLog.d("msex",mSex);

                break;
            case R.id.newaddress_girl:

                mSex="0";
                MyLog.d("msex",mSex);

                break;

        }

    }
}
