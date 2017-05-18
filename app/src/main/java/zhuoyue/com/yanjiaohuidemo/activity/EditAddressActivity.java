package zhuoyue.com.yanjiaohuidemo.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import zhuoyue.com.yanjiaohuidemo.R;
import zhuoyue.com.yanjiaohuidemo.entity.SmsCallBackEntity;
import zhuoyue.com.yanjiaohuidemo.util.MyLog;
import zhuoyue.com.yanjiaohuidemo.util.MyToast;
import zhuoyue.com.yanjiaohuidemo.util.NetWorkApi;

public class EditAddressActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private RadioGroup mEdit_address_radiogroup;
    private EditText mEdit_address_phone,mEdit_address_name,mEdit_address_room;
    private NetWorkApi mNetWorkApi;
    private String mName,mPhone,mAddress,mSex,mPosition,mAid;
    private RadioButton mEdit_address_boy,mEdit_address_girl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_address);

        Intent intent = getIntent();
        mName = intent.getStringExtra("name");
        mPhone = intent.getStringExtra("phone");
        mAddress = intent.getStringExtra("address");
        mSex = intent.getStringExtra("sex");
        mPosition = intent.getStringExtra("position");
        mAid = intent.getStringExtra("aid");

        initView();

        mNetWorkApi = new NetWorkApi();

        initData();

        mEdit_address_radiogroup.setOnCheckedChangeListener(this);

    }

    private void initData() {


        mEdit_address_name.setText(mName);
        mEdit_address_phone.setText(mPhone);
        mEdit_address_room.setText(mAddress);
        if (mSex.equals("0")) {
            mEdit_address_girl.setChecked(true);
            mEdit_address_boy.setChecked(false);
        }

//        mNetWorkApi.ChangeUserAddress( mAid , sp.getString("mobile", null), sp.getString("user_pwd", null),
//                mEdit_address_name.getText().toString(),mEdit_address_phone.getText().toString()
//                ,mEdit_address_room.getText().toString(),mSex, "116.788257", "39.972457",
//                new Callback<SmsCallBackEntity>() {
//
//                    @Override
//                    public void onResponse(Call<SmsCallBackEntity> call, Response<SmsCallBackEntity> response) {
//
//                        SmsCallBackEntity body = response.body();
//
//                        if (body != null) {
//                            if (body.getBack().equals("true")) {
//
//                                MyToast.showShort(EditAddressActivity.this,"修改成功");
//
//                                finish();
//                            }
//
//                        }
//
//                    }
//                    @Override
//                    public void onFailure(Call<SmsCallBackEntity> call, Throwable t) {
//
//                    }
//                });

    }

    private void initView() {

        mEdit_address_boy = (RadioButton) findViewById(R.id.edit_address_boy);
        mEdit_address_girl = (RadioButton) findViewById(R.id.edit_address_girl);
        mEdit_address_phone = (EditText) findViewById(R.id.edit_address_phone_num);
        mEdit_address_name = (EditText) findViewById(R.id.edit_address_name);
        mEdit_address_room = (EditText) findViewById(R.id.edit_address_room);
        mEdit_address_radiogroup = (RadioGroup) findViewById(R.id.edit_address_radiogroup);

    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

        switch (checkedId) {
            case R.id.edit_address_boy:
                mSex = "1";
                break;
            case R.id.edit_address_girl:
                mSex = "0";
                break;
        }

    }

    public void finish_click(View view) {
        finish();
    }

    //保存修改用户地址。  aid是获取列表的时候回调的。
    public void editaddress_save(View view) {

         SharedPreferences sp = getSharedPreferences("perinfo", Context.MODE_PRIVATE);
        mNetWorkApi.ChangeUserAddress( mAid , sp.getString("mobile", null), sp.getString("user_pwd", null), mEdit_address_name.getText().toString(),
                mEdit_address_phone.getText().toString(), mEdit_address_room.getText().toString(), mSex == null ? mSex = "1" : mSex, "116.788257", "39.972457",

                new Callback<SmsCallBackEntity>() {
                    @Override
                    public void onResponse(Call<SmsCallBackEntity> call, Response<SmsCallBackEntity> response) {

                        MyLog.d("flag","name  "+mEdit_address_name.getText().toString());
                        MyLog.d("flag","phone  "+mEdit_address_phone.getText().toString());
                        MyLog.d("flag","address  "+mEdit_address_room.getText().toString());
                        MyLog.d("flag","sex  "+mSex);

//                        MyLog.d("flag","name  "+mEdit_address_name.getText().toString());
//                        MyLog.d("flag","name  "+mEdit_address_name.getText().toString());
//                        MyLog.d("flag","name  "+mEdit_address_name.getText().toString());

                        SmsCallBackEntity body = response.body();

                        if (body != null) {

                            MyLog.d("flag data"+body.getBack());
                            MyLog.d("flag data"+body.getInfo());
                            MyLog.d("flag data"+body.getError());

                            if (body.getBack().equals("true")) {

                                MyToast.showShort(EditAddressActivity.this,"修改成功");
                                      finish();
                            }
                        }else {
                            MyLog.d("flag body"," body null");
                        }
                    }
                    @Override
                    public void onFailure(Call<SmsCallBackEntity> call, Throwable t) {
                           MyLog.d("flag error","T ："+t.getMessage());
                    }
                });


    }
}
