package com.yaotuofu.android.framework.image;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.yaotuofu.android.framework.encryption.MD5Util;
import com.yaotuofu.android.framework.guava.Preconditions;


/**
 * @author atomd
 */
public class ImageUtil {

    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1;
    public static final int PICK_IMAGE_ACTIVITY_REQUEST_CODE = 2;

    private ImageUtil() {
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap;
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1,
                    Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static String saveTemporaryPicture(Context context, Bitmap bitmap) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File storageDir = ImageStorageUtils.getIndividualCacheDirectory(context, "temporary");
        File file = new File(storageDir, File.separator + "IMG_" + timeStamp);
        // Saving the bitmap
        savePicture(bitmap, file, 90);
        return file.getAbsolutePath();
    }

    public static boolean savePicture(Bitmap bitmap, File file, int quality) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, out);
            FileOutputStream stream = new FileOutputStream(file);
            stream.write(out.toByteArray());
            stream.close();
            return true;
        } catch (IOException exception) {
            exception.printStackTrace();
            return false;
        }
    }

    public static Uri savePicture(Context context, Bitmap bitmap) {

        File mediaStorageDir;
        if (Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED)) {
            mediaStorageDir = new File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    "Now!");
        } else {
            mediaStorageDir = context.getCacheDir();
        }

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile = new File(
                mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg"
        );
        // Saving the bitmap
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            FileOutputStream stream = new FileOutputStream(mediaFile);
            stream.write(out.toByteArray());
            stream.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        // Mediascanner need to scan for the image saved
        Intent mediaScannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri fileContentUri = Uri.fromFile(mediaFile);
        mediaScannerIntent.setData(fileContentUri);
        context.sendBroadcast(mediaScannerIntent);
        //MediaScannerConnection.scanFile(
        //    context, new String[] { mediaFile.getAbsolutePath() }, null, null);
        return fileContentUri;
    }

    public static Bitmap decodeSampledBitmapFromPath(String path, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inMutable = true;
        options.inBitmap = BitmapFactory.decodeFile(path, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        options.inPurgeable = true;
        options.inInputShareable = true;
        return BitmapFactory.decodeFile(path, options);
    }

    /**
     * Decode and sample down a bitmap from a byte stream
     */
    public static Bitmap decodeSampledBitmapFromByte(Context context, byte[] bitmapBytes) {
        Display display = ((WindowManager) context.getSystemService(
                Context.WINDOW_SERVICE)).getDefaultDisplay();
        int reqWidth, reqHeight;
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            display.getSize(point);
            reqWidth = point.x;
            reqHeight = point.y;
        } else {
            reqWidth = display.getWidth();
            reqHeight = display.getHeight();
        }
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inMutable = true;
        options.inBitmap = BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length,
                options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false; // If set to true, the decoder will return null (no bitmap), but the out... fields will still be set, allowing the caller to query the bitmap without having to allocate the memory for its pixels.
        options.inPurgeable = true;         // Tell to gc that whether it needs free memory, the Bitmap can be cleared
        options.inInputShareable = true;    // Which kind of reference will be used to recover the Bitmap data after being unregister, when it will be used in the future
        return BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length, options);
    }

    /**
     * Calculate an inSampleSize for use in a {@link BitmapFactory.Options} object
     * when decoding
     * bitmaps using the decode* methods from {@link BitmapFactory}. This
     * implementation calculates
     * the closest inSampleSize that is a power of 2 and will result in the final decoded bitmap
     * having a width and height equal to or larger than the requested width and height
     * <p>
     * The function rounds up the sample size to a power of 2 or multiple
     * of 8 because BitmapFactory only honors sample size this way.
     * For example, BitmapFactory downsamples an image by 2 even though the
     * request is 3. So we round up the sample size to avoid OOM.
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth,
                                            int reqHeight) {
        int initialInSampleSize = computeInitialSampleSize(options, reqWidth, reqHeight);
        int roundedInSampleSize;
        if (initialInSampleSize <= 8) {
            roundedInSampleSize = 1;
            while (roundedInSampleSize < initialInSampleSize) {
                // Shift one bit to left
                roundedInSampleSize <<= 1;
            }
        } else {
            roundedInSampleSize = (initialInSampleSize + 7) / 8 * 8;
        }
        return roundedInSampleSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options, int reqWidth,
                                                int reqHeight) {
        // Raw height and width of image
        final double height = options.outHeight;
        final double width = options.outWidth;
        final long maxNumOfPixels = reqWidth * reqHeight;
        final int minSideLength = Math.min(reqHeight, reqWidth);
        int lowerBound = (maxNumOfPixels < 0) ? 1 :
                (int) Math.ceil(Math.sqrt(width * height / maxNumOfPixels));
        int upperBound = (minSideLength < 0) ? 128 :
                (int) Math.min(Math.floor(width / minSideLength),
                        Math.floor(height / minSideLength));
        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }
        if (maxNumOfPixels < 0 && minSideLength < 0) {
            return 1;
        } else if (minSideLength < 0) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    public static Bitmap rotate(Bitmap bitmap, int degree) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        if (degree != 0) {
            Matrix mtx = new Matrix();
            mtx.postRotate(degree);
            return Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, false);
        }
        return bitmap;
    }

    public static int getPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
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

    public static int sizeOf(Bitmap data) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR1) {
            return data.getRowBytes() * data.getHeight();
        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return data.getByteCount();
        } else {
            return data.getAllocationByteCount();
        }
    }

    public static void setBlurryBackground(Uri uri, SimpleDraweeView view, int radius) {
        ImageRequest networkRequest = ImageRequestBuilder.newBuilderWithSource(uri)
                .setPostprocessor(new BlurPostprocessor(radius, 4))
                .build();
        PipelineDraweeController controller = (PipelineDraweeController)
                Fresco.newDraweeControllerBuilder()
                        .setImageRequest(networkRequest)
                        .setOldController(view.getController())
                        .build();
        view.setController(controller);
    }

    public static void setCachedBlurryBackground(SimpleDraweeView view, Uri assetUri) {

        final File blurryFile = mkBlurryFile(view.getContext(), assetUri.getPath());
        if (blurryFile.exists()) {
            view.setImageURI(Uri.fromFile(blurryFile));
            return;
        }

        ImageRequest networkRequest = ImageRequestBuilder
                .newBuilderWithSource(assetUri)
                .setLowestPermittedRequestLevel(ImageRequest.RequestLevel.FULL_FETCH)
                .setPostprocessor(new BlurPostprocessor(10, 3)
                        .setPostAction(new BlurPostprocessor.PostAction() {
                            @Override
                            public void process(Bitmap destBitmap) {
                                ImageUtil.savePicture(destBitmap, blurryFile, 80);
                            }
                        }))
                .build();

        PipelineDraweeController controller = (PipelineDraweeController)
                Fresco.newDraweeControllerBuilder()
                        .setImageRequest(networkRequest)
                        .setOldController(view.getController())
                        .build();
        view.setController(controller);
    }

    public static File mkBlurryFile(Context context, String key) {
        return new File(
                ImageStorageUtils.getIndividualCacheDirectory(context, "blur"), MD5Util.MD5(key));
    }

    public static File mkVideoFile(Context context, String key) {
        return new File(
                ImageStorageUtils.getIndividualCacheDirectory(context, "video"), MD5Util.MD5(key));
    }

    public static Bitmap overlay(Bitmap bmp1, Bitmap bmp2) {
        Bitmap bmOverlay = Bitmap.createBitmap(bmp1.getWidth(), bmp1.getHeight(), bmp1.getConfig());
        Canvas canvas = new Canvas(bmOverlay);
        canvas.drawBitmap(bmp1, new Matrix(), null);
        canvas.drawBitmap(bmp2, bmp1.getWidth() - bmp2.getWidth(), 0, null);
        return bmOverlay;
    }

    public static Bitmap getBitmapFromView(final int index, View... views) {
        Preconditions.checkArgument(views.length > index);
        View rootView = views[index];
        int canvasWidth = rootView.getWidth();
        int canvasHeight = rootView.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(canvasWidth, canvasHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        for (View view : views) {
            view.draw(canvas);
        }
        return bitmap;
    }

    public static Bitmap getBitmapFromView(View... views) {
        return getBitmapFromView(0, views);
    }
}
