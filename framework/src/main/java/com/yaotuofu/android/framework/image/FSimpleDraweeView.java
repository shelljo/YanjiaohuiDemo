package com.yaotuofu.android.framework.image;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.yaotuofu.android.framework.baseutils.CheckUtils;
import com.yaotuofu.android.framework.baseutils.L;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

/**
 * 改模块封装的是facebook出的fresco图片加载库，优点是：将Bitmap数据避开Java堆内存，存在匿名共享内存中，避免了绝大部分图片引起的oom
 * 使用方法：
 * http://fresco-cn.org/docs/getting-started.html#_
 * <CGSimpleDraweeView
 * android:id="@+id/my_image_view"
 * android:layout_width="20dp"
 * android:layout_height="20dp"
 * fresco:placeholderImage="@drawable/my_drawable"
 * <p>
 * Created by Felix on 2016/4/14.
 */
public class FSimpleDraweeView extends SimpleDraweeView {

    public FSimpleDraweeView(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
    }

    public FSimpleDraweeView(Context context) {
        super(context);
    }

    public FSimpleDraweeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FSimpleDraweeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 远程图片	http://, https://	HttpURLConnection 或者参考 使用其他网络加载方案
     * 本地文件	file://	FileInputStream  Uri.fromFile(new File(pathString))
     * Content provider	content://	ContentResolver
     * asset目录下的资源	asset://	AssetManager
     * res目录下的资源	res://	Resources.openRawResource
     * res 示例:
     * Uri uri = Uri.parse("res://包名(实际可以是任何字符串甚至留空)/" + R.drawable.ic_launcher);
     *
     * @param uri
     */
    public void setURI(String uri) {
        if (!TextUtils.isEmpty(uri)) {
            Uri tUri = Uri.parse(uri);
            setImageURI(tUri);
        }
    }
    public void setURI(String defaultHost,String uri) {
        if(TextUtils.isEmpty(uri)) {
            return;
        }
        if(!CheckUtils.isUrl(uri)){
            uri=defaultHost+uri;
        }
        Uri tUri = Uri.parse(uri);
        setImageURI(tUri);
    }

    public void setURINativeJpeg(Uri uri) {
        if (null != uri) {
            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                    .setProgressiveRenderingEnabled(true)
                    .build();
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setImageRequest(request)
                    .setTapToRetryEnabled(true)
                    .setOldController(this.getController())
                    .build();
            this.setController(controller);
        }
    }

    /**
     * 网络图片不使用缓存
     *
     * @param uri
     */
    public void setURINoCacheHttp(String uri) {
        if (!TextUtils.isEmpty(uri)) {
            Uri tUri = Uri.parse(uri + "?random=" + System.currentTimeMillis());
            setImageURI(tUri);
        }
    }

    public void setURIGif(Uri uri) {
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .build();
        DraweeController draweeController =
                Fresco.newDraweeControllerBuilder()
                        .setImageRequest(request)
                        .setAutoPlayAnimations(true) // 设置加载图片完成后是否直接进行播放
                        .setOldController(this.getController())
                        .setTapToRetryEnabled(true)
                        .setControllerListener(new ControllerListener<ImageInfo>() {
                            @Override
                            public void onSubmit(String id, Object callerContext) {

                            }

                            @Override
                            public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                                L.d("onFinalImageSet" + id);
                            }

                            @Override
                            public void onIntermediateImageSet(String id, ImageInfo imageInfo) {

                            }

                            @Override
                            public void onIntermediateImageFailed(String id, Throwable throwable) {
                                L.e(id, throwable);
                            }

                            @Override
                            public void onFailure(String id, Throwable throwable) {
                                L.e(id, throwable);
                            }

                            @Override
                            public void onRelease(String id) {

                            }
                        })
                        .build();
        this.setController(draweeController);
    }
}
