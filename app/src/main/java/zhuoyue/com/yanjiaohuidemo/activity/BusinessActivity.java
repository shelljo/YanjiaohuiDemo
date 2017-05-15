package zhuoyue.com.yanjiaohuidemo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import zhuoyue.com.yanjiaohuidemo.R;
import zhuoyue.com.yanjiaohuidemo.adapter.ListDropDownAdapter;
import zhuoyue.com.yanjiaohuidemo.diyview.DropDownMenu;

/**
 * 这个是商家页面
 * */
public class BusinessActivity extends AppCompatActivity {
    private ListView mBuss_lv;
    private LinearLayout mBuss_lin_ll;
    private String [] strs;
    private TextView mBuss_location;
    private DropDownMenu mDropDownMenu=new DropDownMenu(this);

    private List<View> popupViews = new ArrayList<>();
    private ListDropDownAdapter mListDropDownAdapter;
    private String sexs[]=new String[]{"不限", "男", "女"};
    private String headers[] = {"城市", "年龄", "性别", "星座"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_business);

        initView();

        final ListView listView = new ListView(this);
        mListDropDownAdapter = new ListDropDownAdapter(this, Arrays.asList(sexs));
        listView.setDividerHeight(0);

        listView.setAdapter(mListDropDownAdapter);

        popupViews.add(listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListDropDownAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position==0 ? headers[0]: sexs[position] );
                mDropDownMenu.closeMenu();
            }
        });

        TextView contentView = new TextView(this);
//        ListView contentView = new ListView(this);
        contentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contentView.setText("内容显示区域");
        contentView.setGravity(Gravity.CENTER);
        contentView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

        mDropDownMenu.setDropDownMenu(Arrays.asList(headers),popupViews,contentView);

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

        mDropDownMenu = (DropDownMenu) findViewById(R.id.dropDownMenu);
        mBuss_location = (TextView) findViewById(R.id.buss_location);
        mBuss_lv = (ListView) findViewById(R.id.buss_lv);
        mBuss_lin_ll = (LinearLayout) findViewById(R.id.buss_lin_is);

    }

}

