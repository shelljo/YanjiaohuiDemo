package zhuoyue.com.yanjiaohuidemo.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;



import zhuoyue.com.yanjiaohuidemo.R;
import zhuoyue.com.yanjiaohuidemo.activity.PersonalInfoActivity;
import zhuoyue.com.yanjiaohuidemo.activity.SettingActivity;
import zhuoyue.com.yanjiaohuidemo.adapter.GridAdapter;
import zhuoyue.com.yanjiaohuidemo.util.MyToast;
import zhuoyue.com.yanjiaohuidemo.util.NetWorkApi;



/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {



    private LinearLayout mLin_Head_bg;
    private GridView mGridView;
    private GridAdapter mGridAdapter;
    private ImageView mSetting, mMsm, mMine_head_pic;
    private TextView mMine_perinfo, mMine_userName;
    private NetWorkApi mNetWorkApi = new NetWorkApi();

    private String mTitle[] = new String[]{"待评价", "待付款", "收藏", "评价", "余额", "我的订单", "抵用券", "积分商城", "会员中心", "我的钱包"};

    private Integer mImage[] = {
            R.drawable.ic_grid11,
            R.drawable.ic_grid12,
            R.drawable.ic_grid13,
            R.drawable.ic_grid14,
            R.drawable.ic_grid15,
            R.drawable.ic_grid16,
            R.drawable.ic_grid17,
            R.drawable.ic_grid18,
            R.drawable.ic_grid19,
            R.drawable.ic_grid110
    };


    public MineFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mineragment, container, false);

        initView(view);

        mGridAdapter = new GridAdapter(getContext(), mTitle, mImage);
        mGridView.setAdapter(mGridAdapter);
        mGridView.setOnItemClickListener(this);
        mMine_userName.setOnClickListener(this);

        //下面是高斯模糊代码，可以直接用。
        RenderScript script = RenderScript.create(getContext());
        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(script, Element.RGBA_8888(script));
        blur.setRadius(24);
        Bitmap src = BitmapFactory.decodeResource(getResources(), R.drawable.bk);
        Bitmap target = Bitmap.createBitmap(src.getWidth(), src.getHeight(), Bitmap.Config.ARGB_8888);
        Allocation in = Allocation.createFromBitmap(script, src);
        blur.setInput(in);
        Allocation out = Allocation.createFromBitmap(script, target);
        blur.forEach(out);
        out.copyTo(target);
        mLin_Head_bg.setBackground(new BitmapDrawable(target));


        //设置的点击事件
        mSetting.setOnClickListener(this);

        mMine_perinfo.setOnClickListener(this);

        mMine_head_pic.setOnClickListener(this);


        return view;
    }

    private void initView(View view) {

        mMine_head_pic = (ImageView) view.findViewById(R.id.mine_head_pic);
        mSetting = (ImageView) view.findViewById(R.id.mine_setting);
        mMsm = (ImageView) view.findViewById(R.id.mine_sms);
        mLin_Head_bg = (LinearLayout) view.findViewById(R.id.mine_head_bg);
        mGridView = (GridView) view.findViewById(R.id.mine_grid);
        mMine_perinfo = (TextView) view.findViewById(R.id.mine_perinfo);
        mMine_userName = (TextView) view.findViewById(R.id.mine_user_name);

    }


    //GridView点击事件
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                MyToast.showShort(getContext(), "点击了第" + position + "条数据");
                break;
            case 1:
                MyToast.showShort(getContext(), "点击了第" + position + "条数据");
                break;
            case 2:
                MyToast.showShort(getContext(), "点击了第" + position + "条数据");
                break;
            case 3:
                MyToast.showShort(getContext(), "点击了第" + position + "条数据");
                break;
            case 4:
                MyToast.showShort(getContext(), "点击了第" + position + "条数据");
                break;
            case 5:
                MyToast.showShort(getContext(), "点击了第" + position + "条数据");
                break;
            case 6:
                MyToast.showShort(getContext(), "点击了第" + position + "条数据");
                break;
            case 7:
                MyToast.showShort(getContext(), "点击了第" + position + "条数据");
                break;
            case 8:
                MyToast.showShort(getContext(), "点击了第" + position + "条数据");
                break;
            case 9:
                MyToast.showShort(getContext(), "点击了第" + position + "条数据");
                break;

        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.mine_user_name:

                break;
            case R.id.mine_perinfo:
                startActivity(new Intent(getContext(), PersonalInfoActivity.class));
                break;
            case R.id.mine_setting:
                startActivity(new Intent(getContext(), SettingActivity.class));
                break;
            case R.id.mine_head_pic:

                break;

        }
    }





}
