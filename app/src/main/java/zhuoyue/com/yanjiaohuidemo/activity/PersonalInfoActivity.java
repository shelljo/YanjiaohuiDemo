package zhuoyue.com.yanjiaohuidemo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import zhuoyue.com.yanjiaohuidemo.R;

public class PersonalInfoActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mPer_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

        initView();
        mPer_back.setOnClickListener(this);

    }

    private void initView() {

        mPer_back = (ImageView) findViewById(R.id.per_back);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
           case  R.id.per_back:
            finish();
            break;


        }


    }
}
