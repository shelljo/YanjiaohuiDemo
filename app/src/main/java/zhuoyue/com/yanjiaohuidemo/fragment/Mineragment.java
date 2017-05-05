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
import android.widget.LinearLayout;

import zhuoyue.com.yanjiaohuidemo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Mineragment extends Fragment {
    private LinearLayout mLin_Head_bg;


    public Mineragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mineragment, container, false);
        mLin_Head_bg = (LinearLayout) view.findViewById(R.id.mine_head_bg);


        //下面是高斯模糊代码，可以直接用。
        RenderScript script = RenderScript.create(getContext());
        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(script, Element.RGBA_8888(script));
        blur.setRadius(30);
        Bitmap src = BitmapFactory.decodeResource(getResources(), R.drawable.gaosi);
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
