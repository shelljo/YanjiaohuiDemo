package com.yaotuofu.android.framework.widget.video;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.util.Pair;
import android.view.Surface;

import java.util.Date;
import java.util.List;

/**
 * @author apolloyujl on 12/18/14.
 */
public class CameraParameters {

  public static Parameters init(Context pContext, Camera camera, int previewW, int previewH) {
    camera = setCameraDisplayOrientation((Activity) pContext, 0, camera);

    Parameters parameters = camera.getParameters();
    parameters = setPreviewSize(previewW, previewH, parameters);

    Size size = parameters.getPreviewSize();
    parameters = setPictureSize(size.width, size.height, parameters);
    parameters = setPictureMode(parameters);
    camera.setParameters(parameters);

    return parameters;
  }

  public static Camera setCameraDisplayOrientation(Activity activity, int cameraId, Camera camera) {
    Camera.CameraInfo info = new Camera.CameraInfo();
    Camera.getCameraInfo(cameraId, info);
    int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
    int degrees = 0;
    switch (rotation) {
      case Surface.ROTATION_0:
        degrees = 0;
        break;
      case Surface.ROTATION_90:
        degrees = 90;
        break;
      case Surface.ROTATION_180:
        degrees = 180;
        break;
      case Surface.ROTATION_270:
        degrees = 270;
        break;
    }

    int result;
    if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
      result = (info.orientation + degrees) % 360;
      result = (360 - result) % 360; // compensate the mirror
    } else { // back-facing
      result = (info.orientation - degrees + 360) % 360;
    }
    camera.setDisplayOrientation(result);

    return camera;
  }

  private static Parameters setPictureMode(Parameters pParameters) {
    long time = new Date().getTime();
    pParameters.setGpsTimestamp(time);
    for (String s : pParameters.getSupportedFocusModes()) {
      if (s.equals(Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
        pParameters.setFocusMode(Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        break;
      }
    }

    return pParameters;
  }

  private static Parameters setPreviewSize(int previewW, int previewH, Parameters parameters) {
    List<Size> sizes = parameters.getSupportedPreviewSizes();
    Pair<Integer, Integer> optimalSize = getOptimalPreviewSize(sizes, previewW, previewH);
    parameters.setPreviewSize(optimalSize.first, optimalSize.second);

    return parameters;
  }

  private static Parameters setPictureSize(int previewW, int previewH, Parameters parameters) {
    List<Size> sizes = parameters.getSupportedPictureSizes();
    Pair<Integer, Integer> optimalSize = getOptimalPictureSize(sizes, previewW, previewH);
    if (optimalSize != null) {
      parameters.setPictureSize(optimalSize.first, optimalSize.second);
    }

    return parameters;
  }

  private static Pair<Integer, Integer> getOptimalPreviewSize(List<Size> sizes, int w, int h) {
    return new DefaultPreviewSizeAssignStrategy().assign(sizes, w, h);
  }

  private static Pair<Integer, Integer> getOptimalPictureSize(List<Size> sizes, int w, int h) {
    return new DefaultPictureSizeAssignStrategy().assign(sizes, w, h);
  }
}