package com.yaotuofu.android.framework.widget.video;

import android.hardware.Camera.Size;

public class RecordInfo {

  private Size pictureSize;
  private byte[] data;
  private boolean mirrored;
  private int cameraId;

  public RecordInfo(Size pictureSize, byte[] data, boolean mirrored, int cameraId) {
    this.pictureSize = pictureSize;
    this.data = data;
    this.mirrored = mirrored;
    this.cameraId = cameraId;
  }

  public Size getPictureSize() {
    return pictureSize;
  }

  public byte[] getRawData() {
    return data;
  }

  public int getDataLength() {
    return data.length;
  }

  public int getImageWidth() {
    return pictureSize.width;
  }

  public int getImageHeight() {
    return pictureSize.height;
  }

  public boolean isMirrored() {
    return mirrored;
  }

  public int getCameraId() {
    return cameraId;
  }
}