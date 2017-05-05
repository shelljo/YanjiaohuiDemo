package zhuoyue.com.yanjiaohuidemo.fragment;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.LinearLayout;

import zhuoyue.com.yanjiaohuidemo.R;
import zhuoyue.com.yanjiaohuidemo.adapter.GridAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends Fragment {
    private LinearLayout mLin_Head_bg;
    private GridView mGridView;
    private GridAdapter mGridAdapter;

    private String mTitle[]=new String[]{"待评价","待付款","收藏","评价","余额","我的订单","抵用券","积分商城","会员中心","我的钱包"};

    private Integer mImage[]={
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mineragment, container, false);
        //findview_By_Id
        mLin_Head_bg = (LinearLayout) view.findViewById(R.id.mine_head_bg);
        mGridView = (GridView) view.findViewById(R.id.mine_grid);
        mGridAdapter = new GridAdapter(getContext(), mTitle, mImage);
        mGridView.setAdapter(mGridAdapter);


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


        return view;
    }



}
