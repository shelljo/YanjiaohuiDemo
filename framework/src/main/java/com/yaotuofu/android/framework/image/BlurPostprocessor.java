package com.yaotuofu.android.framework.image;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.facebook.cache.common.CacheKey;
import com.facebook.cache.common.SimpleCacheKey;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.bitmaps.PlatformBitmapFactory;
import com.facebook.imagepipeline.request.BasePostprocessor;

/**
 * @author atomd
 */
public class BlurPostprocessor extends BasePostprocessor {

  private int radius;
  private float sampling;
  private PostAction postAction;
  public BlurPostprocessor(int radius, float sampling) {
    this.radius = radius;
    this.sampling = sampling;
  }

  public BlurPostprocessor setPostAction(PostAction postAction) {
    this.postAction = postAction;
    return this;
  }

  @Override
  public CloseableReference<Bitmap> process(
      Bitmap sourceBitmap,
      PlatformBitmapFactory bitmapFactory) {

    CloseableReference<Bitmap> bitmapRef = bitmapFactory.createBitmap(
        (int) (sourceBitmap.getWidth() / sampling),
        (int) (sourceBitmap.getHeight() / sampling));

    try {
      Bitmap destBitmap = bitmapRef.get();

      Canvas canvas = new Canvas(destBitmap);
      canvas.scale(1 / sampling, 1 / sampling);
      Paint paint = new Paint();
      paint.setFlags(Paint.FILTER_BITMAP_FLAG);
      canvas.drawBitmap(sourceBitmap, 0.0F, 0.0F, paint);
      BlurUtil.blur(destBitmap, radius);
      if (postAction != null) {
        postAction.process(destBitmap);
      }
      return CloseableReference.cloneOrNull(bitmapRef);
    } finally {
      CloseableReference.closeSafely(bitmapRef);
    }
  }

  public String getName() {
    return this.getClass().getSimpleName();
  }

  public CacheKey getPostprocessorCacheKey() {
    return new SimpleCacheKey("radius=" + radius + ", sampling=" + sampling);
  }

  public interface PostAction {

    void process(Bitmap destBitmap);
  }
}
