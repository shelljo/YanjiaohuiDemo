package zhuoyue.com.yanjiaohuidemo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import zhuoyue.com.yanjiaohuidemo.R;
/**
 * 这个是关于燕郊汇页面。
 * */

public class AboutActivity extends AppCompatActivity {

    private ImageView mAbout_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        mAbout_back= (ImageView) findViewById(R.id.about_back);
        mAbout_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

    }
}
