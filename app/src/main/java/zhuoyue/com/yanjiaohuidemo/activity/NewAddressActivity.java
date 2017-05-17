package zhuoyue.com.yanjiaohuidemo.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import zhuoyue.com.yanjiaohuidemo.R;
import zhuoyue.com.yanjiaohuidemo.entity.SmsCallBackEntity;
import zhuoyue.com.yanjiaohuidemo.util.MyLog;
import zhuoyue.com.yanjiaohuidemo.util.MyToast;
import zhuoyue.com.yanjiaohuidemo.util.NetWorkApi;

public class NewAddressActivity extends AppCompatActivity {

    private EditText mAddress_name,mAddress_phone,mAddress_room;
    private NetWorkApi mNetWorkApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_address);

        initView();
        mNetWorkApi = new NetWorkApi();


    }

    private void initView() {

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

         SharedPreferences sp = getSharedPreferences("perinfo", Context.MODE_PRIVATE);

         mNetWorkApi.AddUserAddress(sp.getString("mobile", null), sp.getString("user_pwd", null),
                 mAddress_name.getText().toString(), mAddress_phone.getText().toString(), mAddress_room.getText().toString()
                 , "1", "116.788257", "39.972457",
                 new Callback<SmsCallBackEntity>() {
                     @Override
                     public void onResponse(Call<SmsCallBackEntity> call, Response<SmsCallBackEntity> response) {
                         SmsCallBackEntity body = response.body();
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
