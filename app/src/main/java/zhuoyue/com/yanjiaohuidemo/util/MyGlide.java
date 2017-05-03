package zhuoyue.com.yanjiaohuidemo.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by ShellJor on 2017/5/3 0003.
 * at 13:36
 * 这个是自己封装一层的图片加载框架，
 * 方便以后换框架
 * MyGlide.load();
 */


public class MyGlide {

    public static void load(Context context, String url, ImageView imageView){

        Glide.with(context).load(url).into(imageView);

    }

}
