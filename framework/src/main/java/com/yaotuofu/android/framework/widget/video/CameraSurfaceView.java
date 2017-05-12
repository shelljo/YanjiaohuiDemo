package com.yaotuofu.android.framework.widget.video;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.hardware.Camera.Area;
import android.hardware.Camera.Parameters;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.yaotuofu.android.framework.baseutils.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author apolloyujl on 12/18/14.
 */
//,Camera.PreviewCallback
public class CameraSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

  private SurfaceHolder holder;

  private CameraHandler cameraHandler;

  private Context mContext;

  private int w;

  private int h;

  private boolean pointerCountBiggerThanOne;

  private boolean mSelfshot;

  private float mDist;

  private int mZoomFactor = 100;
  private CameraFocusListener mCameraFocusListener;

  public CameraSurfaceView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    init(context);
  }

  public CameraSurfaceView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context);
  }

  public CameraSurfaceView(Context context) {
    super(context);
    init(context);
  }

  public static int dip2px(Context context, float dpValue) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (dpValue * scale + 0.5f);
  }

  @SuppressWarnings("deprecation")
  private void init(Context pContext) {
    mContext = pContext;
    holder = getHolder();
    // Required for 2.x compatibiltiy
    //		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    holder.addCallback(this);
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    //setMeasuredDimension(getMeasuredHeight() * 9 / 16, getMeasuredHeight());
    setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth()*16/9);
  }

  @Override
  public void surfaceCreated(SurfaceHolder holder) {
  }

  @Override
  public void surfaceDestroyed(SurfaceHolder arg0) {
    release();
  }

  public void release() {
    if (cameraHandler != null) {
      cameraHandler.release();
      cameraHandler = null;
    }
  }

  @Override
  public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
    this.w = w;
    this.h = h;
    restart();
  }

  @Override
  public boolean onTouchEvent(final MotionEvent motionEvent) {
    if (cameraHandler == null || cameraHandler.getCamera() == null) {
      return true;
    }

    Parameters params = cameraHandler.getCamera().getParameters();
    if (motionEvent.getPointerCount() > 1) {
      pointerCountBiggerThanOne = true;

      if (motionEvent.getAction() == MotionEvent.ACTION_POINTER_DOWN) {
        mDist = getFingerSpacing(motionEvent);
      } else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE && params.isZoomSupported()) {
        cameraHandler.getCamera().cancelAutoFocus();
        handleZoom(motionEvent, params);
      }
    } else {
      if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
        pointerCountBiggerThanOne = false;
      } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
        if (pointerCountBiggerThanOne) {
          return true;
        }

        if (mCameraFocusListener != null) {
          mCameraFocusListener.onFocused(motionEvent);
        }

        Rect rect = calculateFocusArea(motionEvent.getX(), motionEvent.getY());


        final List<Area> focusList = new ArrayList<Area>();
        Area focusArea = new Area(rect, 1000);
        focusList.add(focusArea);

        Parameters para = cameraHandler.getCamera().getParameters();
        para.setFocusAreas(focusList);
        para.setFocusMode(Parameters.FOCUS_MODE_AUTO);
        try {
          cameraHandler.getCamera().setParameters(para);
          cameraHandler.getCamera().autoFocus(null);
        } catch (Exception ex) {
        }
      }
    }

    return true;
  }

  private Rect calculateFocusArea(float x, float y) {
    int FOCUS_AREA_SIZE = dip2px(mContext, 64);
    int left = clamp(Float.valueOf((x / getWidth()) * 2000 - 1000).intValue(), FOCUS_AREA_SIZE);
    int top = clamp(Float.valueOf((y / getHeight()) * 2000 - 1000).intValue(), FOCUS_AREA_SIZE);

    return new Rect(left, top, left + FOCUS_AREA_SIZE, top + FOCUS_AREA_SIZE);
  }

  private int clamp(int touchCoordinateInCameraReper, int focusAreaSize) {
    int result;
    if (Math.abs(touchCoordinateInCameraReper) + focusAreaSize / 2 > 1000) {
      if (touchCoordinateInCameraReper > 0) {
        result = 1000 - focusAreaSize / 2;
      } else {
        result = -1000 + focusAreaSize / 2;
      }
    } else {
      result = touchCoordinateInCameraReper - focusAreaSize / 2;
    }
    return result;
  }

  public void handleZoom(MotionEvent event, Parameters params) {
    int maxZoom = params.getMaxZoom();
    int zoom = params.getZoom();
    float newDist = getFingerSpacing(event);
    if (newDist > mDist) {
      //zoom in
      if (zoom < maxZoom)
        zoom++;
    } else if (newDist < mDist) {
      //zoom out
      if (zoom > 0)
        zoom--;
    }

    mDist = newDist;

    params.setZoom(zoom);

    try {
      cameraHandler.getCamera().setParameters(params);
      mZoomFactor = params.getZoomRatios().get(zoom);
    } catch (Exception ex) {
    }
  }

  private float getFingerSpacing(MotionEvent event) {
    float x = event.getX(0) - event.getX(1);
    float y = event.getY(0) - event.getY(1);
    return (float) Math.sqrt(x * x + y * y);
  }

  public void restart() {
    this.restart(mSelfshot);
  }

  public void restart(boolean selfshot) {
    mSelfshot = selfshot;

    if (cameraHandler == null) {
      cameraHandler = CameraHandler.open(selfshot, holder);
      cameraHandler.start(mContext, w, h);
    } else {
      cameraHandler.restartPreview();
    }
  }

  public void takePicture(final RecordingActionListener listener) {
    if (cameraHandler != null) {
      cameraHandler.takePicture(listener);
    }
  }




  public String takeVideo(boolean isMic) {
    if (cameraHandler != null) {
      if(isMic){
        return cameraHandler.takeCamcorderVideo();
      }
      return cameraHandler.takeVideo();
    }
    return null;
  }

  public void stopVideo() {
    if (cameraHandler != null) {
      cameraHandler.stopVideo();
    }
  }

  public void setSelfshot(final boolean pSelfshot) {
    mSelfshot = pSelfshot;
  }

  public boolean isPreviewStopped() {
    // Surface destroyed or not initialized ever
    return cameraHandler == null;
  }

  public void setCameraFocusListener(final CameraFocusListener pCameraFocusListener) {
    mCameraFocusListener = pCameraFocusListener;
  }

  public interface CameraFocusListener {
    public void onFocused(MotionEvent pMotionEvent);
  }
//  @Override
  public void onPreviewFrame(byte[] data, Camera camera) {
    Camera.Parameters parameters = camera.getParameters();
    Camera.Size size = parameters.getPreviewSize();
    // 刚刚拍照的文件名
    String fileName = "IMG_"+ new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date())
            .toString() + ".jpg";
    try {
    File tempFile=FileUtils.saveTempImageToSD(fileName);
    YuvImage yuvImage = new YuvImage(data,parameters.getPreviewFormat(), size.width, size.height,null);
      FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
      yuvImage.compressToJpeg(new Rect(0, 0, yuvImage.getWidth(), yuvImage.getHeight()),90,fileOutputStream);

    } catch (IOException e) {
        e.printStackTrace();
      }

  }
}