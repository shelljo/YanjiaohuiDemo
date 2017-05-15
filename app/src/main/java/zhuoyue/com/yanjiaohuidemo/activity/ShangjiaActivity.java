package zhuoyue.com.yanjiaohuidemo.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import zhuoyue.com.yanjiaohuidemo.R;
import zhuoyue.com.yanjiaohuidemo.adapter.ShangjialvAdapter;
import zhuoyue.com.yanjiaohuidemo.entity.ShangjiaEntity;
import zhuoyue.com.yanjiaohuidemo.util.NetWorkApi;

public class ShangjiaActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AbsListView.OnScrollListener {


    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ShangjialvAdapter mAdapter;
    private ListView shangjia_lv;
    private NetWorkApi mNetWorkApi = new NetWorkApi();
    private List<ShangjiaEntity.SupplierListBean> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shangjia);

        shangjia_lv = (ListView) findViewById(R.id.shangjia_lv);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.shangjia_refresh);
        mSwipeRefreshLayout.setRefreshing(false);
        mList = new ArrayList<>();
        initData();

        //listview的点击事件
        shangjia_lv.setOnItemClickListener(this);
        //listview的分页加载
        shangjia_lv.setOnScrollListener(this);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                mList.clear();
                ShangjiaEntity.SupplierListBean listBean=new ShangjiaEntity.SupplierListBean();
                listBean.setName("haha");
                listBean.setIndex_img("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png");
                listBean.setAddress(Math.random()+"日本");
                mList.add(listBean);
                initData();
                mAdapter.notifyDataSetChanged();
                Toast.makeText(ShangjiaActivity.this, "刷新成功", Toast.LENGTH_SHORT).show();
                mSwipeRefreshLayout.setRefreshing(false);

            }
        });

    }

    //这个是list_view的数据。
    private void initData() {
        mNetWorkApi.GetHomeInfo("0", "116.8229", "39.994026",
                new Callback<ShangjiaEntity>() {

                    @Override
                    public void onResponse(Call<ShangjiaEntity> call, Response<ShangjiaEntity> response) {
                        ShangjiaEntity body = response.body();
                        List<ShangjiaEntity.SupplierListBean> supplier_list = body.getSupplier_list();
//                        mList = new ArrayList<ShangjiaEntity.SupplierListBean>();
                        for (int i = 0; i < supplier_list.size(); i++) {
                            mList.add(supplier_list.get(i));

                        }

                        mAdapter = new ShangjialvAdapter(ShangjiaActivity.this, mList);

                        shangjia_lv.setAdapter(mAdapter);

                    }
                    @Override
                    public void onFailure(Call<ShangjiaEntity> call, Throwable t) {

                    }
                }
        );
    }

    //listview 的item点击事件
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, "点击了数据"+position, Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {


    }

    //在这里进行分页
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {


    }



}
