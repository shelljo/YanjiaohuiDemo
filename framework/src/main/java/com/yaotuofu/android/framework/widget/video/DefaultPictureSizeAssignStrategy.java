package com.yaotuofu.android.framework.widget.video;

import android.hardware.Camera.Size;
import android.util.Pair;

import java.util.List;

/**
 * @author apolloyujl on 12/18/14.
 */
public class DefaultPictureSizeAssignStrategy implements SizeAssignStrategy {

  @Override
  public Pair<Integer, Integer> assign(List<Size> sizes, int desiredW, int desiredH) {
    for (Size size : sizes) {
      double desiredRatio = (double) size.height / size.width;
    }

    // Preview Size
    Pair<Integer, Integer> retval = assignExactMatch(sizes, desiredW, desiredH);
    // 1920*1080
    if (retval == null) {
      retval = assignExactMatch(sizes, ImageSizeConstraint.W1920, ImageSizeConstraint.H1080);
    }
    // 1280*720
    if (retval == null) {
      retval = assignExactMatch(sizes, ImageSizeConstraint.W1280, ImageSizeConstraint.H720);
    }

    // Try to find image w/ same aspect ratio
    if (retval == null) {
      retval = assignMinimalSizeWithSameAspectRatio(sizes, desiredW, desiredH);
    }

    return retval;
  }

  private Pair<Integer, Integer> assignExactMatch(List<Size> sizes, int w, int h) {
    Pair<Integer, Integer> retval = null;
    for (Size s : sizes) {
      if (s.width == w && s.height == h) {
        retval = new Pair<Integer, Integer>(w, h);
        break;
      }
    }
    return retval;
  }

  private Pair<Integer, Integer> assignMinimalSizeWithSameAspectRatio(List<Size> sizes,
      int desiredW, int desiredH) {

    int currentMinimumW = Integer.MAX_VALUE;
    int currentH = 0;

    double desiredRatio = (double) desiredW / desiredH;
    for (Size s : sizes) {
      if (s.width < currentMinimumW && s.width >= desiredW) {
        // Check aspect ratio
        double ratio = (double) s.height / s.width;
        if (Math.abs(ratio - desiredRatio) < 0.001) {
          currentMinimumW = s.width;
          currentH = s.height;
        }
      }
    }

    Pair<Integer, Integer> retval = null;
    if (currentMinimumW < Integer.MAX_VALUE) {
      retval = new Pair<Integer, Integer>(currentMinimumW, currentH);
    }

    return retval;
  }
}
