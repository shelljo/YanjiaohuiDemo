package com.yaotuofu.android.framework.baseutils;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author AirZcm on 2016612.
 */
public class VideoUtil {

    private VideoUtil() {
    }

    public static Bitmap createVideoThumbnail(Context context, Uri uri, int width, int height) {

        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        if (TextUtils.isEmpty(uri.toString())) {
            return null;
        }

        Bitmap thumbnail = null;
        try {
            retriever.setDataSource(context, uri);
            thumbnail = retriever.getFrameAtTime(-1); //取得指定时间的Bitmap，即可以实现抓图（缩略图）功能
        } catch (RuntimeException ex) {
            // Assume this is a corrupt video file.
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException ex) {
                // Ignore failures while cleaning up.
            }
        }

        if (thumbnail == null) {
            return null;
        }

        thumbnail = Bitmap.createScaledBitmap(thumbnail, width, height, true);
        return thumbnail;
    }

    public static boolean savePicture(Bitmap bitmap, File file, int quality) {
        try {
            FileOutputStream stream = new FileOutputStream(file);
            stream.write(compressToJpegByteArray(bitmap, quality));
            stream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static byte[] compressToJpegByteArray(Bitmap bm, final int quality) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream(bm.getWidth() * bm.getHeight());
        bm.compress(Bitmap.CompressFormat.JPEG, quality, buffer);
        return buffer.toByteArray();
    }
}
