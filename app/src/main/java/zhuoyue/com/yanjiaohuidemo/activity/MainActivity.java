package zhuoyue.com.yanjiaohuidemo.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.view.View;
import android.widget.LinearLayout;

import zhuoyue.com.yanjiaohuidemo.R;
import zhuoyue.com.yanjiaohuidemo.base.BaseActivity;

public class MainActivity extends BaseActivity {
    private LinearLayout mLinearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLinearLayout = (LinearLayout) findViewById(R.id.main_lin);

        RenderScript script = RenderScript.create(this);
        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(script, Element.RGBA_8888(script));
        blur.setRadius(25);
        Bitmap src = BitmapFactory.decodeResource(getResources(), R.drawable.gaosi);
        Bitmap target = Bitmap.createBitmap(src.getWidth(), src.getHeight(), Bitmap.Config.ARGB_8888);
        Allocation in = Allocation.createFromBitmap(script, src);
        blur.setInput(in);
        Allocation out = Allocation.createFromBitmap(script, target);
        blur.forEach(out);
        out.copyTo(target);
        mLinearLayout.setBackgroundDrawable(new BitmapDrawable(target));


    }

    //
    public void shangchuan_click(View view) {



    }
}
