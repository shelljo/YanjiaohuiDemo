package com.yaotuofu.android.framework.baseutils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.yaotuofu.android.framework.R;
import com.yaotuofu.android.framework.application.BaseApplication;
import com.yaotuofu.android.framework.cache.acache.Md5FileNameGenerator;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 文件工具类
 * 1、获取缓存目录
 * 2、获取缓存目录下的文件
 * 3、获取图片下载文件
 * 4、获取自动更新App下载文件
 * 5、删除旧apk
 * 6、获取App存储路径
 *
 * Created by Felix on 2016/3/26.
 */
public class FileUtils {

    public static final String APP_WEBVIEW_DB_DIRNAME = "/dbcache";
    public static final String APP_WEBVIEW_CACAHE_DIRNAME = "/webcache";

    private static Context mContext = BaseApplication.getInstance();

    private static final String BASE_EXTERNAL_DIR_PATH =
            new File(Environment.getExternalStorageDirectory(), "mingjia").getAbsolutePath();


    static {
        File baseExternalDir = new File(BASE_EXTERNAL_DIR_PATH);
        if(!baseExternalDir.exists())
            baseExternalDir.mkdirs();
    }

    /**
     * 获取缓存目录
     *
     * @return
     */
    public static File getCacheDir() {
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return mContext.getExternalCacheDir();
        }
        return mContext.getCacheDir();
    }

    /**
     * 获取缓存目录下的文件夹
     */
    public static File getCacheDir(String name) {
        File file = new File(getCacheDir(), name);
        if(!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    /**
     * 清除过期缓存
     * @param dir
     * @param numDays
     * @return
     */
    public static  int clearCacheFolder(File dir, long numDays) {
        int deletedFiles = 0;
        if (dir!= null && dir.isDirectory()) {
            try {
                for (File child:dir.listFiles()) {

                    if (child.isDirectory()) {
                        deletedFiles += clearCacheFolder(child, numDays);
                    }
                    if (child.lastModified() < numDays) {
                        if (child.delete()) {
                            deletedFiles++;
                        }
                    }

                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        return deletedFiles;
    }

    /**
     * 获取图片下载文件夹
     */
    public static File getImageSaveDir() {
        File imageSaveDir = new File(BASE_EXTERNAL_DIR_PATH, "image");

        if(!imageSaveDir.exists())
            imageSaveDir.mkdirs();
        return imageSaveDir;
    }
    /**
     * 获取图片下载文件夹
     */
    public static File getVoiceSaveDir() {
        File voiceSaveDir = new File(BASE_EXTERNAL_DIR_PATH, "voice");
        if(!voiceSaveDir.exists())
            voiceSaveDir.mkdirs();
        return voiceSaveDir;
    }

    /**
     * 获取自动更新app下载文件夹
     */
    public static File getAppSaveDir() {
        File imageSaveDir = new File(BASE_EXTERNAL_DIR_PATH, "app");
        if(!imageSaveDir.exists())
            imageSaveDir.mkdirs();
        return imageSaveDir;
    }

    /**
     * 删除旧apk
     */
    public static void deleteOldApk() {
        if(!SdcardUtils.isSdcardEnable())
            return;
        File appSaveDir = getAppSaveDir();
        for(File f : appSaveDir.listFiles()) {
            String regex = "txmpApp_\\d+\\.apk";
            if(f.getName().matches(regex)) {
                String verCode = f.getName().replace("caiginApp_", "").replace(".apk", "");
                if(Integer.parseInt(verCode) <= AppUtils.getVersionCode(mContext)) {
                    f.delete();
                }
            }
        }
    }

    /**
     * 获得Apk存储路径
     */
    public static String getAppSaveFileName(int versionCode) {
        return "mingjiaApp_" + versionCode + ".apk";
    }

    public static byte[] getFileByte(String fileAndPath) {
        InputStream in = null;
        byte[] data = null;
        try {
            in = new FileInputStream(fileAndPath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
    public static InputStream getInputStreamFile(String fileAndPath){
        InputStream in =null;
        try{
            in=new FileInputStream(new File(fileAndPath));
        }catch (IOException e){
            e.printStackTrace();
        }
        return  in;
    }




    /**
     * 调用此方法自动计算指定文件或指定文件夹的大小
     *
     * @param filePath 文件路径
     * @return 计算好的带B、KB、MB、GB的字符串
     */
    public static String getAutoFileOrFilesSize(String filePath) {
        File file = new File(filePath);
        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getFileSizes(file);
            } else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("获取文件大小", "获取失败!");
        }
        return FormetFileSize(blockSize);
    }
    public static String getAutoFileOrFilesSize(File file) {
        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getFileSizes(file);
            } else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("获取文件大小", "获取失败!");
        }
        return FormetFileSize(blockSize);
    }

    /**
     * 获取指定文件大小
     *
     * @return
     * @throws Exception
     */
    public static long getFileSize(File file) throws Exception {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
        } else {
            file.createNewFile();
            Log.e("获取文件大小", "文件不存在!");
        }
        return size;
    }

    /**
     * 获取指定文件夹
     *
     * @param f
     * @return
     * @throws Exception
     */
    private static long getFileSizes(File f) throws Exception {
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileSizes(flist[i]);
            } else {
                size = size + getFileSize(flist[i]);
            }
        }
        return size;
    }

    /**
     * 转换文件大小
     *
     * @param fileS
     * @return
     */
    public  static String FormetFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize = "0B";
        if (fileS == 0) {
            return wrongSize;
        }
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }


    /**
     * 删除文件
     *
     * @param uri 文件Uri
     * @return 成功与否
     */
    public static boolean clearCropFile(Uri uri) {
        if (uri == null) {
            return false;
        }
        return clearCropFile(uri.getPath());
    }

    /**
     * 删除文件
     *
     * @param path 文件路径
     * @return 成功与否
     */
    public static boolean clearCropFile(String path) {
        if (TextUtils.isEmpty(path)) {
            return false;
        }
        //有时文件明明存在  但 file.exists为false
        File file = new File(path);
        //System.out.println("工具判断:"+FileUtils.exists(file)+" 原始判断:"+file.exists()+" \npath:"+file.getPath());
        if (file.exists()) {
            boolean result = file.delete();
            if (result) {
                System.out.println("Cached crop file cleared.");
            } else {
                System.out.println("Failed to clear cached crop file.");
            }
            return result;
        } else {
            System.out.println("Trying to clear cached crop file but it does not exist.");
        }
        return false;
    }


    public static File saveTempImageToSD(String fileName) throws IOException {
        File dirFile = new File(BASE_EXTERNAL_DIR_PATH);
        if(!dirFile.exists()){
            dirFile.mkdir();
        }
        File tempCaptureFile = new File(BASE_EXTERNAL_DIR_PATH + fileName + ".jpg");

        if(!tempCaptureFile.exists()){
            tempCaptureFile.createNewFile();
        }
        return tempCaptureFile;
    }

    public static File saveImageToSD(Bitmap bitmap, String fileName) throws IOException {
        File dirFile = new File(BASE_EXTERNAL_DIR_PATH);
        if(!dirFile.exists()){
            dirFile.mkdir();
        }
        File myCaptureFile = new File(BASE_EXTERNAL_DIR_PATH + fileName + ".jpg");
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        L.d("saveImageToSD length: " + baos.toByteArray().length);
        bos.flush();
        bos.close();
        return myCaptureFile;
    }



    public static File saveImageUrlToSD(String picURL, String filePathAndName) throws IOException {

        URL url = new URL(picURL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(6*1000);  // 注意要设置超时，设置时间不要超过10秒，避免被android系统回收
        if (conn.getResponseCode() != 200)
            throw new RuntimeException("请求url失败");
        InputStream inSream = conn.getInputStream();

        Bitmap bitmap = BitmapFactory.decodeStream(inSream);

        File myCaptureFile = new File(filePathAndName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        L.d("saveImageToSD length: " + baos.toByteArray().length);
        bos.flush();
        bos.close();
        return myCaptureFile;
    }


    public static File getBeanFile(String url) {
        File mediaFile = new File(
                getCacheDir().getPath() + File.separator + Md5FileNameGenerator.uri2Md5(url));
        mediaFile.deleteOnExit();
        return mediaFile;
    }


    public static String saveVideotoSD(Context context, File inputFile) {
        File videoSaveDir = new File(BASE_EXTERNAL_DIR_PATH, "movies");
        if (!videoSaveDir.exists()) {
            videoSaveDir.mkdir();
        }
        String fileSavePath=videoSaveDir.getAbsoluteFile() + "/" + new SimpleDateFormat("'MOV_'yyyyMMddHHmmss'.mp4'",Locale.US).format(new Date());

        InputStream in;
        OutputStream out;
        try {
            in = new FileInputStream(inputFile);
            out = new FileOutputStream(fileSavePath);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            out.flush();
            out.close();
            Toast.makeText(context, context.getString(R.string.text_video_save), Toast.LENGTH_SHORT)
                    .show();
            return fileSavePath;
        } catch (Exception e) {
            Toast.makeText(context, context.getString(R.string.text_video_save_fail),
                    Toast.LENGTH_SHORT).show();
            L.d(e.getMessage());
        }
        return null;
    }

    public static String saveVideotoSD(Context context, File inputFile,String fileName) {
        File videoSaveDir = new File(BASE_EXTERNAL_DIR_PATH, "movies");
        if (!videoSaveDir.exists()) {
            videoSaveDir.mkdir();
        }
        String fileSavePath=videoSaveDir.getAbsoluteFile() + "/" + fileName;

        InputStream in;
        OutputStream out;
        try {
            in = new FileInputStream(inputFile);
            out = new FileOutputStream(fileSavePath);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            out.flush();
            out.close();
            Toast.makeText(context, context.getString(R.string.text_video_save), Toast.LENGTH_SHORT)
                    .show();
            return fileSavePath;
        } catch (Exception e) {
            Toast.makeText(context, context.getString(R.string.text_video_save_fail),
                    Toast.LENGTH_SHORT).show();
            L.d(e.getMessage());
        }
        return null;
    }
}
