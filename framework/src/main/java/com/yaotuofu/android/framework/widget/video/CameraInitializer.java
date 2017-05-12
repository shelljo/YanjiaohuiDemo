package com.yaotuofu.android.framework.widget.video;

import android.annotation.TargetApi;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.view.SurfaceHolder;

public class CameraInitializer {

  public static CameraHandler open(boolean selfshot, SurfaceHolder holder) {
    Camera camera = null;
    try {
      camera = openCamera(selfshot);
      camera.setPreviewDisplay(holder);
    } catch (Exception e) {
      if (camera != null) {
        camera.release();
        camera = null;
      }
    }
    return new CameraHandler(camera, selfshot);
  }

  private static Camera openCamera(boolean selfshot) {
    Camera camera;
    //		camera = Camera.open();
    if (Build.VERSION.SDK_INT >= VERSION_CODES.GINGERBREAD) {
      camera = openCameraGingerbread(selfshot);
    } else {
      camera = Camera.open();
    }
    return camera;
  }

  @TargetApi(VERSION_CODES.GINGERBREAD)
  private static Camera openCameraGingerbread(boolean selfshot) {
    Camera camera = null;
    if (selfshot) {
      camera = openCameraGingerbread(CameraInfo.CAMERA_FACING_FRONT);
    } else {
      camera = openCameraGingerbread(CameraInfo.CAMERA_FACING_BACK);
    }
    if (camera == null) {
      camera = Camera.open(0);
    }
    return camera;
  }

  @TargetApi(VERSION_CODES.GINGERBREAD)
  private static Camera openCameraGingerbread(int facing) {
    Camera camera = null;
    CameraInfo cameraInfo = new CameraInfo();
    int cameraCount = Camera.getNumberOfCameras();
    for (int i = 0; i < cameraCount; i++) {
      Camera.getCameraInfo(i, cameraInfo);
      if (cameraInfo.facing == facing) {
        camera = Camera.open(i);
        break;
      }
    }
    return camera;
  }

  public static boolean hasFrontCamera() {
    return Build.VERSION.SDK_INT >= VERSION_CODES.GINGERBREAD
        && hasFrontCameraGingerbread();
  }

  @TargetApi(VERSION_CODES.GINGERBREAD)
  private static boolean hasFrontCameraGingerbread() {
    boolean retval = false;
    CameraInfo cameraInfo = new CameraInfo();
    int cameraCount = Camera.getNumberOfCameras();
    for (int i = 0; i < cameraCount; i++) {
      Camera.getCameraInfo(i, cameraInfo);
      if (cameraInfo.facing == CameraInfo.CAMERA_FACING_FRONT) {
        retval = true;
        break;
      }
    }
    return retval;
  }
}
