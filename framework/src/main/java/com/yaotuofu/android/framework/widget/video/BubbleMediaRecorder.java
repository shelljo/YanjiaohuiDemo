package com.yaotuofu.android.framework.widget.video;

import android.content.Context;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.util.Log;

import java.io.File;

/**
 * @author apolloyujl on 1/11/15.
 */
public class BubbleMediaRecorder extends MediaRecorder {

    private final static String TAG = "BUBBLE_MEDIA_RECORDER";

    public static File dir;

    private String mUri;

    public static BubbleMediaRecorder initWithCamera(Camera pCamera, boolean isSelfie,
                                                     Context context,boolean isMaic) {
        BubbleMediaRecorder mediaRecorder = new BubbleMediaRecorder();

        mediaRecorder.setCamera(pCamera);
        mediaRecorder.setVideoSource(VideoSource.CAMERA);
        if(isMaic){
            mediaRecorder.setAudioSource(AudioSource.CAMCORDER);
        }else{
            mediaRecorder.setAudioSource(AudioSource.MIC);
        }
        if (isSelfie) {
            mediaRecorder.setOrientationHint(270);
        } else {
            mediaRecorder.setOrientationHint(90);
        }

        mediaRecorder.setOutputFormat(OutputFormat.MPEG_4);

        CamcorderProfile profile = CamcorderProfile.get(CamcorderProfile.QUALITY_480P);

        // No limit. Don't forget to check the space on disk.
        mediaRecorder.setMaxDuration(15000);

        mediaRecorder.setVideoSize(1280, 720);
    /*某些手机不支持854*480*/
//    mediaRecorder.setVideoSize(854, 480);
        mediaRecorder.setVideoFrameRate(30);
        mediaRecorder.setVideoEncodingBitRate(1500000);
        mediaRecorder.setVideoEncoder(VideoEncoder.H264);

        mediaRecorder.setAudioEncoder(AudioEncoder.AAC);
        mediaRecorder.setAudioChannels(profile.audioChannels);
        mediaRecorder.setAudioEncodingBitRate(64000);
        mediaRecorder.setAudioSamplingRate(44100);

        dir = new File(context.getExternalFilesDir(null).getAbsolutePath());
        mediaRecorder.mUri = initFile().getAbsolutePath();
        mediaRecorder.setOutputFile(mediaRecorder.mUri);

        try {
            mediaRecorder.prepare();
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
        return mediaRecorder;
    }

    private static File initFile() {
        File file;
        if (!dir.exists() && !dir.mkdirs()) {
            Log.e(TAG, "Failed to create storage directory: " + dir.getAbsolutePath());
            file = null;
        } else {
            file = new File(dir.getAbsolutePath(), (int) (System.currentTimeMillis() / 1000) + ".mp4");

            Log.e(TAG, dir.getAbsolutePath());
        }

        return file;
    }

    public String getUri() {
        return mUri;
    }
}
