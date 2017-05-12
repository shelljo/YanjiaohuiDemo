package com.yaotuofu.android.framework.widget.video;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.media.AudioManager;
import android.os.Handler;
import android.view.SurfaceHolder;

public class CameraHandler {

  private static Context mContext;
  private final Camera.ShutterCallback shutterCallback = new Camera.ShutterCallback() {
    public void onShutter() {
      AudioManager mgr = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
      mgr.playSoundEffect(AudioManager.FLAG_PLAY_SOUND);
    }
  };
  private BubbleMediaRecorder mMediaRecorder;
  private Camera camera;
  private Size pictureSize;
  private boolean autoFocusEnabled;
  private boolean selfShot;
  private Handler handler;

  CameraHandler(Camera camera, boolean selfshot) {
    this.camera = camera;
    this.selfShot = selfshot;
  }

  public static CameraHandler open(boolean selfshot, SurfaceHolder holder) {
    return CameraInitializer.open(selfshot, holder);
  }

  public void start(Context pContext, int previewW, int previewH) {
    mContext = pContext;

    initalizeParameters(previewW, previewH);
    restartPreview();
  }

  private void initalizeParameters(int previewW, int previewH) {
    if (camera != null) {
      Parameters params = CameraParameters.init(mContext, camera, previewW,
          previewH);
      pictureSize = params.getPictureSize();
    }
  }

  public void restartPreview() {
    if (camera != null) {
      camera.startPreview();
    }
  }

  public void takePicture(final RecordingActionListener listener) {
    if (camera != null) {
      if (autoFocusEnabled) {
        focusCamera(listener);
      } else {
        onFocusingFinished(listener, camera);
      }
    } else {
      listener.onRecordFailed();
    }
  }

  public String takeVideo() {
    if (camera != null) {
         mMediaRecorder = BubbleMediaRecorder.initWithCamera(camera, selfShot, mContext,false);
      try {
        camera.unlock();
        mMediaRecorder.start();
      } catch (Exception ex) {
      }
      return mMediaRecorder.getUri();
    }
    return null;
  }

  public String takeCamcorderVideo() {
    if (camera != null) {
      mMediaRecorder = BubbleMediaRecorder.initWithCamera(camera, selfShot, mContext,true);
      try {
        camera.unlock();
        mMediaRecorder.start();
      } catch (Exception ex) {
      }
      return mMediaRecorder.getUri();
    }
    return null;
  }

  public void stopVideo() {
    if (mMediaRecorder != null) {
      mMediaRecorder.setOnErrorListener(null);
      mMediaRecorder.setPreviewDisplay(null);

      try {
        mMediaRecorder.stop();
      } catch (RuntimeException e) {
      } finally {
        mMediaRecorder.release();
        mMediaRecorder = null;
      }
    }
  }

  private void focusCamera(final RecordingActionListener listener) {
    camera.autoFocus(new Camera.AutoFocusCallback() {
      @Override
      public void onAutoFocus(boolean success, Camera camera) {
        if (success) {
          onFocusingFinished(listener, camera);
        } else {
          listener.onRecordFailed();
        }
      }
    });
  }

  private void onFocusingFinished(final RecordingActionListener listener, Camera camera) {
    camera.takePicture(shutterCallback, null, new Camera.PictureCallback() {
      @Override
      public void onPictureTaken(byte[] data, Camera camera) {
        listener.onRecordSaved(new RecordInfo(pictureSize, data, selfShot, selfShot ? 1 : 0));
      }
    });
  }

  public void release() {
    if (camera != null) {
      camera.stopPreview();
      camera.release();
      camera = null;
    }

    if (mMediaRecorder != null) {
      mMediaRecorder.release();
      mMediaRecorder = null;
    }
  }

  public Camera getCamera() {
    return camera;
  }
}
