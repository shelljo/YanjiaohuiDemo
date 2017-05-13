package zhuoyue.com.yanjiaohuidemo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import zhuoyue.com.yanjiaohuidemo.R;

/**
 * 这个是商家页面
 * */
public class BusinessActivity extends AppCompatActivity {
    private ListView mBuss_lv;
    private LinearLayout mBuss_lin_ll;
    private String [] strs;
    private TextView mBuss_location;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_business);

        initView();


        strs = new String[100];
        for (int i = 0; i < 100; i++) {

            strs[i]="data--"+i;

        }

        View headView = LayoutInflater.from(this).inflate(R.layout.lvhead, null);
        View secondheadView = LayoutInflater.from(this).inflate(R.layout.header, null);
        mBuss_lv.addHeaderView(headView);
        mBuss_lv.addHeaderView(secondheadView);

        mBuss_lv.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,strs));
        mBuss_lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                if (firstVisibleItem>=1) {
                    mBuss_lin_ll.setVisibility(View.VISIBLE);

                }else {
                    mBuss_lin_ll.setVisibility(View.GONE);
                }

            }
        });
    }

    private void initView() {

        mBuss_location = (TextView) findViewById(R.id.buss_location);
        mBuss_lv = (ListView) findViewById(R.id.buss_lv);
        mBuss_lin_ll = (LinearLayout) findViewById(R.id.buss_lin_is);

    }

}
