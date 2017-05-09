package zhuoyue.com.yanjiaohuidemo.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import zhuoyue.com.yanjiaohuidemo.R;
import zhuoyue.com.yanjiaohuidemo.entity.LoginCallBackEntity;
import zhuoyue.com.yanjiaohuidemo.entity.LoginInfoEntity;
import zhuoyue.com.yanjiaohuidemo.util.MyLog;
import zhuoyue.com.yanjiaohuidemo.util.MyToast;
import zhuoyue.com.yanjiaohuidemo.util.NetWorkApi;

public class PersonalInfoActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mPer_back;
    private Button mPer_save;
    private EditText mPer_data_born, mPer_province,mPer_nickname,mPer_sex,mPer_city;
    private String mYear,mMonth,mDay,mLocation;
    private NetWorkApi mNetWorkApi=new NetWorkApi();
    private String mMobile;
    private String mUser_pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        initView();
        mPer_back.setOnClickListener(this);
        mPer_save.setOnClickListener(this);

        initEditText();


    }

    private void initEditText() {

        mNetWorkApi.PostLoginData(mMobile, mUser_pwd, new Callback<LoginCallBackEntity>() {
            @Override
            public void onResponse(Call<LoginCallBackEntity> call, Response<LoginCallBackEntity> response) {
                LoginCallBackEntity body = response.body();
                if (body != null) {
                    LoginInfoEntity info = body.getInfo();
                    if(info.getUser_nick()==null){
                      mPer_nickname.setText(info.getUser_nick());
                    }
//                    info.get

                }


            }

            @Override
            public void onFailure(Call<LoginCallBackEntity> call, Throwable t) {

            }
        });

    }

    private void initView() {

        mPer_city = (EditText) findViewById(R.id.per_city);
        mPer_sex = (EditText) findViewById(R.id.per_sex);
        mPer_save = (Button) findViewById(R.id.per_save);
        mPer_back = (ImageView) findViewById(R.id.per_back);
        mPer_data_born = (EditText) findViewById(R.id.per_data_born);
        mPer_province = (EditText) findViewById(R.id.per_province);
        mPer_nickname = (EditText) findViewById(R.id.per_nickname);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //点击回退按钮
           case  R.id.per_back:
                finish();
            break;
           //点击出生日期
            case R.id.per_data_born:
                DatePickerDialog dialog=new DatePickerDialog(PersonalInfoActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        MyToast.showShort(PersonalInfoActivity.this,"您选择的时间是 "+year+"年，"+month+"月"+dayOfMonth+"日");

                        mYear = year + "";
                        mMonth = (month+1) +"";
                        mDay = dayOfMonth + "";
                        mPer_data_born.setText(mYear+"-"+mMonth+"-"+mDay);

                    }
                },1990,1,1);
                dialog.show();
                break;
            //点击保存
            case R.id.per_save:

                SharedPreferences sp = getSharedPreferences("perinfo", Context.MODE_PRIVATE);

                mMobile = sp.getString("mobile", "");
                mUser_pwd = sp.getString("user_pwd","");

                mNetWorkApi.PostPersonalInfo(mPer_nickname.getText().toString(),
                                             mPer_sex.getText().toString(),
                                             mYear, mMonth, mDay, mPer_province.getText().toString(),mPer_city.getText().toString(),
                        mMobile, mUser_pwd, new Callback<LoginInfoEntity>() {
                    @Override
                    public void onResponse(Call<LoginInfoEntity> call, Response<LoginInfoEntity> response) {
                        MyLog.d("flag","nickname"+mPer_nickname.getText().toString());
                        MyLog.d("flag","sex"+ mPer_sex.getText().toString());
                        MyLog.d("flag","year"+mYear);
                        MyLog.d("flag","month"+mMonth);
                        MyLog.d("flag","mday"+mDay);
                        MyLog.d("flag","per_provinc"+mPer_province.getText().toString());
                        MyLog.d("flag","Per_city"+mPer_city.getText().toString());
                        MyLog.d("flag","user_pwd:"+ mUser_pwd);
                        MyLog.d("flag","mobile:"+ mMobile);


                        LoginInfoEntity body = response.body();

                        if (body != null) {
                        MyLog.d("flag+","back"+ body.getBack());

                            body.getBack().equals("true");
                            MyToast.showShort(PersonalInfoActivity.this,"保存成功");
                            finish();

                        }
                    }
                    @Override
                    public void onFailure(Call<LoginInfoEntity> call, Throwable t) {
                    }
                });
                MyToast.showShort(this,"保存了啊");
                break;


        }

    }




}
