package zhuoyue.com.yanjiaohuidemo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import zhuoyue.com.yanjiaohuidemo.R;

public class MyAddressActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout mMyaddress_rl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_address);


        initView();

        mMyaddress_rl.setOnClickListener(this);



    }

    private void initView() {
        mMyaddress_rl = (RelativeLayout) findViewById(R.id.myaddress_rl);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //点击新增收货地址
            case R.id.myaddress_rl:

                startActivity(new Intent(MyAddressActivity.this,NewAddressActivity.class));

                break;


        }


    }
}
