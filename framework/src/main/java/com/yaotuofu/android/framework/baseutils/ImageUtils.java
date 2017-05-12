package com.yaotuofu.android.framework.baseutils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by FelixFang on 6/6/16.
 */
public class ImageUtils {
    public static final String TAG = ImageUtils.class.getSimpleName();
    /**
     * get scaled bitmap
     * @param filePath  local file path
     * @param maxWidth  scaled bitmap width you desired, if maxWidth < maxHeight, then scaled
     *                  bitmap width is maxWidth while bitmap height is maxWidth * ratio
     * @param maxHeight scaled bitmap height you desired, if maxHeight < maxWidth, then scaled
     *                  bitmap height is maxHeight while bitmap width is maxHeight / ratio.
     * @return scaled bitmap
     */
    public static Bitmap getScaledBitmap(String filePath, int maxWidth, int maxHeight) {
        BitmapFactory.Options decodeOptions = new BitmapFactory.Options();
        Bitmap bitmap;
        // If we have to resize this image, first get the natural bounds.
        decodeOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, decodeOptions);
        int actualWidth = decodeOptions.outWidth;
        int actualHeight = decodeOptions.outHeight;
        Log.d(TAG, "Actual width: " + actualWidth + ", actual height: " + actualHeight);
        // Then compute the dimensions we would ideally like to decode to.
        int desiredWidth = getResizedDimension(maxWidth, maxHeight,
                actualWidth, actualHeight);
        int desiredHeight = getResizedDimension(maxHeight, maxWidth,
                actualHeight, actualWidth);
        Log.d(TAG, "Desired width: " + desiredWidth + ", desired height: " + desiredHeight);

        // Decode to the nearest power of two scaling factor.
        decodeOptions.inJustDecodeBounds = false;
        // TODO(ficus): Do we need this or is it okay since API 8 doesn't support it?
        // decodeOptions.inPreferQualityOverSpeed = PREFER_QUALITY_OVER_SPEED;
        decodeOptions.inSampleSize =
                findBestSampleSize(actualWidth, actualHeight, desiredWidth, desiredHeight);
        Bitmap tempBitmap = BitmapFactory.decodeFile(filePath, decodeOptions);
        // If necessary, scale down to the maximal acceptable size.
        if (tempBitmap != null && (tempBitmap.getWidth() > desiredWidth ||
                tempBitmap.getHeight() > desiredHeight)) {
            bitmap = Bitmap.createScaledBitmap(tempBitmap,
                    desiredWidth, desiredHeight, true);
            tempBitmap.recycle();
        } else {
            bitmap = tempBitmap;
        }
        return bitmap;
    }

    /**
     * get scaled bitmap
     * @param imageResId image resource id
     * @param maxWidth  scaled bitmap width you desired, if maxWidth < maxHeight, then scaled
     *                  bitmap width is maxWidth while bitmap height is maxWidth * ratio
     * @param maxHeight scaled bitmap height you desired, if maxHeight < maxWidth, then scaled
     *                  bitmap height is maxHeight while bitmap width is maxHeight / ratio.
     * @return scaled bitmap
     */
    public static Bitmap getScaledBitmap(Context context, int imageResId, int maxWidth, int maxHeight) {
        BitmapFactory.Options decodeOptions = new BitmapFactory.Options();
        Bitmap bitmap = null;
        // If we have to resize this image, first get the natural bounds.
        decodeOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), imageResId, decodeOptions);
        int actualWidth = decodeOptions.outWidth;
        int actualHeight = decodeOptions.outHeight;
        Log.d(TAG, "Actual width: " + actualWidth + ", actual height: " + actualHeight);

        // Then compute the dimensions we would ideally like to decode to.
        int desiredWidth = getResizedDimension(maxWidth, maxHeight,
                actualWidth, actualHeight);
        int desiredHeight = getResizedDimension(maxHeight, maxWidth,
                actualHeight, actualWidth);
        Log.d(TAG, "Desired width: " + desiredWidth + ", desired height: " + desiredHeight);

        // Decode to the nearest power of two scaling factor.
        decodeOptions.inJustDecodeBounds = false;
        // TODO(ficus): Do we need this or is it okay since API 8 doesn't support it?
        // decodeOptions.inPreferQualityOverSpeed = PREFER_QUALITY_OVER_SPEED;
        decodeOptions.inSampleSize =
                findBestSampleSize(actualWidth, actualHeight, desiredWidth, desiredHeight);
        Bitmap tempBitmap = BitmapFactory.decodeResource(context.getResources(), imageResId, decodeOptions);
        // If necessary, scale down to the maximal acceptable size.
        if (tempBitmap != null && (tempBitmap.getWidth() > desiredWidth ||
                tempBitmap.getHeight() > desiredHeight)) {
            bitmap = Bitmap.createScaledBitmap(tempBitmap,
                    desiredWidth, desiredHeight, true);
            tempBitmap.recycle();
        } else {
            bitmap = tempBitmap;
        }
        return bitmap;
    }

    /**
     * Returns the largest power-of-two divisor for use in downscaling a bitmap
     * that will not result in the scaling past the desired dimensions.
     *
     * @param actualWidth Actual width of the bitmap
     * @param actualHeight Actual height of the bitmap
     * @param desiredWidth Desired width of the bitmap
     * @param desiredHeight Desired height of the bitmap
     */
    // Visible for testing.
    private static int findBestSampleSize(
            int actualWidth, int actualHeight, int desiredWidth, int desiredHeight) {
        double wr = (double) actualWidth / desiredWidth;
        double hr = (double) actualHeight / desiredHeight;
        double ratio = Math.min(wr, hr);
        float n = 1.0f;
        while ((n * 2) <= ratio) {
            n *= 2;
        }

        return (int) n;
    }

    /**
     * Scales one side of a rectangle to fit aspect ratio.
     *
     * @param maxPrimary Maximum size of the primary dimension (i.e. width for
     *        max width), or zero to maintain aspect ratio with secondary
     *        dimension
     * @param maxSecondary Maximum size of the secondary dimension, or zero to
     *        maintain aspect ratio with primary dimension
     * @param actualPrimary Actual size of the primary dimension
     * @param actualSecondary Actual size of the secondary dimension
     */
    private static int getResizedDimension(int maxPrimary, int maxSecondary, int actualPrimary,
                                           int actualSecondary) {
        // If no dominant value at all, just return the actual.
        if (maxPrimary == 0 && maxSecondary == 0) {
            return actualPrimary;
        }

        // If primary is unspecified, scale primary to match secondary's scaling ratio.
        if (maxPrimary == 0) {
            double ratio = (double) maxSecondary / (double) actualSecondary;
            return (int) (actualPrimary * ratio);
        }

        if (maxSecondary == 0) {
            return maxPrimary;
        }

        double ratio = (double) actualSecondary / (double) actualPrimary;
        int resized = maxPrimary;
        if (resized * ratio > maxSecondary) {
            resized = (int) (maxSecondary / ratio);
        }
        return resized;
    }

    /**
     * get actual image dimension
     * @param imagePath local file path
     * @return
     */
    public static int[] getActualImageDimension(String imagePath) {
        int[] imageSize = new int[2];
        BitmapFactory.Options decodeOptions = new BitmapFactory.Options();
        // If we have to resize this image, first get the natural bounds.
        decodeOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, decodeOptions);
        int actualWidth = decodeOptions.outWidth;
        int actualHeight = decodeOptions.outHeight;
        imageSize[0] = actualWidth;
        imageSize[1] = actualHeight;
        return imageSize;
    }

    /**
     * get actual image dimension
     * @param imageResId image resource id
     * @return
     */
    public static int[] getActualImageDimension(Context context, int imageResId) {
        int[] imageSize = new int[2];
        BitmapFactory.Options decodeOptions = new BitmapFactory.Options();
        // If we have to resize this image, first get the natural bounds.
        decodeOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), imageResId, decodeOptions);
        int actualWidth = decodeOptions.outWidth;
        int actualHeight = decodeOptions.outHeight;
        imageSize[0] = actualWidth;
        imageSize[1] = actualHeight;
        return imageSize;
    }

    private static int[] getDesiredImageDimension(String imagePath, int maxWidth, int maxHeight) {
        int[] desiredImageDimension = new int[2];
        int[] actualImageDimension = getActualImageDimension(imagePath);
        Log.d(TAG, "Actual width: " + actualImageDimension[0] + ", actual height: " + actualImageDimension[1]);
        int maxPrimary;
        int maxSecondary;
        if (actualImageDimension[0] >= actualImageDimension[1]) {
            maxPrimary = maxWidth;
            maxSecondary = 0;
            desiredImageDimension[0] = getResizedDimension(maxPrimary, maxSecondary, actualImageDimension[0], actualImageDimension[1]);
            desiredImageDimension[1] = getResizedDimension(maxSecondary, maxPrimary, actualImageDimension[1], actualImageDimension[0]);
        } else {
            maxPrimary = maxHeight;
            maxSecondary = 0;
            desiredImageDimension[1] = getResizedDimension(maxPrimary, maxSecondary, actualImageDimension[1], actualImageDimension[0]);
            desiredImageDimension[0] = getResizedDimension(maxPrimary, maxSecondary, actualImageDimension[0], actualImageDimension[1]);
        }
        Log.d(TAG, "Desired width: " + desiredImageDimension[0] + ", desired height: " + desiredImageDimension[1]);
        return desiredImageDimension;
    }

    private static int[] getDesiredImageDimension(Context context, int imageResId, int maxWidth, int maxHeight) {
        int[] desiredImageDimension = new int[2];
        int[] actualImageDimension = getActualImageDimension(context, imageResId);
        Log.d(TAG, "Actual width: " + actualImageDimension[0] + ", actual height: " + actualImageDimension[1]);
        int maxPrimary;
        int maxSecondary;
        if (actualImageDimension[0] >= actualImageDimension[1]) {
            maxPrimary = maxWidth;
            maxSecondary = 0;
            desiredImageDimension[0] = getResizedDimension(maxPrimary, maxSecondary, actualImageDimension[0], actualImageDimension[1]);
            desiredImageDimension[1] = getResizedDimension(maxSecondary, maxPrimary, actualImageDimension[1], actualImageDimension[0]);
        } else {
            maxPrimary = maxHeight;
            maxSecondary = 0;
            desiredImageDimension[1] = getResizedDimension(maxPrimary, maxSecondary, actualImageDimension[1], actualImageDimension[0]);
            desiredImageDimension[0] = getResizedDimension(maxPrimary, maxSecondary, actualImageDimension[0], actualImageDimension[1]);
        }
        Log.d(TAG, "Desired width: " + desiredImageDimension[0] + ", desired height: " + desiredImageDimension[1]);
        return desiredImageDimension;
    }

    /**
     * compress the image file, create a scaled compressed image file, and overwrite the origin one.
     * @param path  origin image file path
     * @param maxWidth
     * @param maxHeight
     * @param quality
     */
    public static void compress(String path, int maxWidth, int maxHeight, int quality) {
        FileOutputStream out;
        try {
            Bitmap scaledBitmap = getScaledBitmap(path, maxWidth, maxHeight);
            Bitmap rotatedBitmap = rotateBitmap(getBitmapDegree(path), scaledBitmap);
            out = new FileOutputStream(path);
            Bitmap mutableBitmap = rotatedBitmap.copy(Bitmap.Config.ARGB_8888, true);

            // write the compressed bitmap at the destination specified by filename.
            mutableBitmap.compress(Bitmap.CompressFormat.JPEG, quality, out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * compress the image file, create a scaled compressed image file, and overwrite the origin one.
     * @param originPath  origin image file path
     * @param maxWidth
     * @param maxHeight
     * @param quality
     */
    public static void compress(String originPath, String outputPath, int maxWidth, int maxHeight, int quality) {
        FileOutputStream out;
        try {
            Bitmap scaledBitmap = getScaledBitmap(originPath, maxWidth, maxHeight);
            Bitmap rotatedBitmap = rotateBitmap(getBitmapDegree(originPath), scaledBitmap);
            out = new FileOutputStream(outputPath);
            Bitmap mutableBitmap = rotatedBitmap.copy(Bitmap.Config.ARGB_8888, true);

            // write the compressed bitmap at the destination specified by filename.
            mutableBitmap.compress(Bitmap.CompressFormat.JPEG, quality, out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * add water mark at the left top of image.
     * @param context
     * @param srcPath   local image file path
     * @param watermarkRes  watermark resource
     * @param maxWidth scaled bitmap width you desired, if maxWidth < maxHeight, then scaled
     *                  bitmap width is maxWidth while bitmap height is maxWidth * ratio
     * @param maxHeight scaled bitmap height you desired, if maxHeight < maxWidth, then scaled
     *                  bitmap height is maxHeight while bitmap width is maxHeight / ratio.
     * @param quality compress quality
     */
    // http://developer.android.com/training/displaying-bitmaps/load-bitmap.html
    public static void watermark(Context context, String srcPath, int watermarkRes, int maxWidth, int maxHeight, int quality) {

        FileOutputStream out;
        try {
            Bitmap scaledBitmap = getScaledBitmap(srcPath, maxWidth, maxHeight);
            Bitmap rotatedBitmap = rotateBitmap(getBitmapDegree(srcPath), scaledBitmap);
            Bitmap scaledWatermark = getScaledBitmap(context, watermarkRes, maxWidth, maxHeight);
            out = new FileOutputStream(srcPath);

            Bitmap mutableBitmap = rotatedBitmap.copy(Bitmap.Config.ARGB_8888, true);
            Canvas canvas = new Canvas(mutableBitmap);
            canvas.drawBitmap(scaledWatermark, 0, 0, null);

            // write the compressed bitmap at the destination specified by filename.
            mutableBitmap.compress(Bitmap.CompressFormat.JPEG, quality, out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            Log.e(TAG, "Add watermark result in OOM");
            e.printStackTrace();
        }
    }

    /**
     * add watermark at the right bottom of the image
     * @param context
     * @param srcPath   local image file path
     * @param watermarkRes  watermark resource
     * @param maxWidth scaled bitmap width you desired, if maxWidth < maxHeight, then scaled
     *                  bitmap width is maxWidth while bitmap height is maxWidth * ratio
     * @param maxHeight scaled bitmap height you desired, if maxHeight < maxWidth, then scaled
     *                  bitmap height is maxHeight while bitmap width is maxHeight / ratio.
     * @param quality compress quality
     */
    public static void watermarkAtRightBottom(Context context, String srcPath, int watermarkRes, int maxWidth, int maxHeight, int quality) {

        FileOutputStream out;
        try {
            Bitmap scaledBitmap = getScaledBitmap(srcPath, maxWidth, maxHeight);
            Bitmap rotatedBitmap = rotateBitmap(getBitmapDegree(srcPath), scaledBitmap);
            Bitmap scaledWatermark = getScaledBitmap(context, watermarkRes, maxWidth, maxHeight);
            out = new FileOutputStream(srcPath);

            int left = rotatedBitmap.getWidth() - scaledWatermark.getWidth();
            int top = rotatedBitmap.getHeight() - scaledWatermark.getHeight();

            Bitmap mutableBitmap = rotatedBitmap.copy(Bitmap.Config.ARGB_8888, true);
            Canvas canvas = new Canvas(mutableBitmap);
            canvas.drawBitmap(scaledWatermark, left, top, null);

            // write the compressed bitmap at the destination specified by filename.
            mutableBitmap.compress(Bitmap.CompressFormat.JPEG, quality, out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            Log.e(TAG, "Add watermark result in OOM");
            e.printStackTrace();
        }
    }

    /**
     * get bitmap degree, you may get an rotated photo when you take a picture in some devices.
     * @param path local image file path
     * @return
     */
    public static int getBitmapDegree(String path) {
        int degree  = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * rotate bitmap
     * @param angle rotate angle
     * @param bitmap origin bitmap
     * @return rotated bitmap
     */
    public static Bitmap rotateBitmap(int angle, Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    /**
     * create a copied image.
     * @param context
     * @param photoUri origin image uri
     * @param outputPath   output image uri.
     * @return true if successfully compressed to the specified stream.
     * @throws IOException
     */
    public static boolean copyBitmapFile(Context context, Uri photoUri, String outputPath) throws IOException {
        // Load image from path
        InputStream input = context.getContentResolver().openInputStream(photoUri);

        // compress it
        Bitmap bitmapOrigin = BitmapFactory.decodeStream(input);
        if (bitmapOrigin == null) return false;
        // save to file
        FileOutputStream output = new FileOutputStream(outputPath);
        return bitmapOrigin.compress(Bitmap.CompressFormat.JPEG, 100, output);
    }


    public static Bitmap compressByWidth(String imagePath, int width) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, options);

        int outW = options.outWidth;
        int inSampleSize = 1;

        if (outW > width) {
            int halfW = outW / 2;
            while ((halfW / inSampleSize) > width) {
                inSampleSize *= 2;
            }
        }

        options.inSampleSize = inSampleSize;

        options.inJustDecodeBounds = false;

        int widthRatio = (int) Math.ceil(options.outWidth / (float) width);
        if (widthRatio > 1) {
            options.inSampleSize = widthRatio;
        }
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(imagePath, options);
    }


    /**
     * 自适应宽度加载图片。保持图片的长宽比例不变，通过修改imageView的高度来完全显示图片。
     */
    public static ImageView loadIntoUseFitWidth(Context context, final String imageUrl, int errorImageId, final ImageView imageView) {

//        GlideRequestListenner glideRequestListenner=new GlideRequestListenner<String,GlideDrawable>(imageView);
        Glide.with(context)
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        if (imageView == null) {
                            return false;
                        }
                        if (imageView.getScaleType() != ImageView.ScaleType.FIT_XY) {
                            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        }
                        ViewGroup.LayoutParams params = imageView.getLayoutParams();
                        int vw = imageView.getWidth() - imageView.getPaddingLeft() - imageView.getPaddingRight();
                        float scale = (float) vw / (float) resource.getIntrinsicWidth();
                        int vh = Math.round(resource.getIntrinsicHeight() * scale);
                        params.height = vh + imageView.getPaddingTop() + imageView.getPaddingBottom();
                        imageView.setLayoutParams(params);
                        return false;
                    }
                })
                .placeholder(errorImageId)
                .error(errorImageId)
                .into(imageView);

        return imageView;
    }




    /**
     *
     * @param originPath
     * @param maxWidth
     * @param w 宽高裁剪
     * @param h 搞裁剪 从左右上叫0,0 计算
     * @return
     */
    public static Bitmap compressAndCropBitmap(String originPath, int maxWidth,int w,int h ){
        Bitmap bitmapOrigin=compressByWidth(originPath,maxWidth);//压缩 等比宽
        bitmapOrigin=cropBitmap(bitmapOrigin,w,h);//裁剪
        return  bitmapOrigin;
    }


    public static Bitmap cropBitmap(Bitmap croppedImage,int w,int h){
        int orgW = croppedImage.getWidth();
        int orgH = croppedImage.getHeight();
        if(orgH<h){
            h=orgH;
        }
        if(orgW<w){
            w=orgW;
        }
        croppedImage = Bitmap.createBitmap(croppedImage, 0, 0, w,h, null, true);
        return croppedImage;
    }




    public static ByteArrayOutputStream saveImageToStream(Bitmap originBitmap, long maxBK) {
        Bitmap mutableBitmap = originBitmap.copy(Bitmap.Config.ARGB_8888, true);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        int options = 80;

        mutableBitmap.compress(Bitmap.CompressFormat.JPEG, options, stream);
        while (stream.toByteArray().length / 1024 > maxBK) {
            stream.reset();
            options -= 10;
            L.d("size==="+stream.toByteArray().length);
            L.d("size kb==="+(stream.toByteArray().length/1024));
            L.d("size m==="+(stream.toByteArray().length/1024/1024));
            mutableBitmap.compress(Bitmap.CompressFormat.JPEG, options, stream);
        }
        L.d("size kb==="+stream.toByteArray().length/1024);
        return stream;
    }


}
