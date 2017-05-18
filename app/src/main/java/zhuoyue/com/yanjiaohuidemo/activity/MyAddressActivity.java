package zhuoyue.com.yanjiaohuidemo.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import zhuoyue.com.yanjiaohuidemo.R;
import zhuoyue.com.yanjiaohuidemo.adapter.MyAddresslvadapter;
import zhuoyue.com.yanjiaohuidemo.entity.AddressEntity;
import zhuoyue.com.yanjiaohuidemo.entity.UserAddressCallBackEntity;
import zhuoyue.com.yanjiaohuidemo.util.NetWorkApi;

public class MyAddressActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private RelativeLayout mMyaddress_rl;
    private ListView mMyAddress_lv;
    private MyAddresslvadapter mMyAddresslvadapter;
    private NetWorkApi mNetWorkApi;
    private TextView mMy_manager;
    private List<UserAddressCallBackEntity> mInfo;
//    private ImageView mMy_change,mMy_delete;
//    private LinearLayout mMy_show_change_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_address);

        initView();

        mMyaddress_rl.setOnClickListener(this);

        mNetWorkApi = new NetWorkApi();

        initData();


        mMy_manager.setOnClickListener(this);

     //   mMyAddress_lv.setOnItemClickListener(this);

    }


    private void initData() {

        SharedPreferences sp = getSharedPreferences("perinfo", Context.MODE_PRIVATE);

        mNetWorkApi.GetUserAddress(sp.getString("mobile", null), sp.getString("user_pwd", null),
                new Callback<AddressEntity>() {
                    @Override
                    public void onResponse(Call<AddressEntity> call, Response<AddressEntity> response) {

                        AddressEntity body = response.body();

                        mInfo = body.getInfo();

                        mMyAddresslvadapter = new MyAddresslvadapter(MyAddressActivity.this, mInfo);

                        mMyAddress_lv.setAdapter(mMyAddresslvadapter);

                    }

                    @Override
                    public void onFailure(Call<AddressEntity> call, Throwable t) {

                    }
                }

        );

    }

    private void initView() {

        mMy_manager = (TextView) findViewById(R.id.my_manager);
        mMyAddress_lv = (ListView) findViewById(R.id.myaddress_lv);
        mMyaddress_rl = (RelativeLayout) findViewById(R.id.myaddress_rl);

//        mMy_show_change_delete = (LinearLayout) findViewById(R.id.my_show_change_delete);
//        mMy_change = (ImageView) findViewById(R.id.my_change);
//        mMy_delete = (ImageView) findViewById(R.id.my_delete);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //点击新增收货地址
            case R.id.myaddress_rl:

                startActivity(new Intent(MyAddressActivity.this,NewAddressActivity.class));

                break;

            case R.id.my_manager:

                break;

        }

    }

    //点击回退键
    public void finish_click(View view) {
        finish();
    }

    //点击管理
    public void manager_click(View view) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(MyAddressActivity.this, EditAddressActivity.class);
        intent.putExtra("position",position);
        intent.putExtra("aid", mInfo.get(position).getId());
        intent.putExtra("phone", mInfo.get(position).getMobile());
        intent.putExtra("name", mInfo.get(position).getName());
        intent.putExtra("address", mInfo.get(position).getAddress());
        intent.putExtra("sex", mInfo.get(position).getSex());

        startActivity(intent);

    }


}
