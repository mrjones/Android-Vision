package es.mrjon.droidvision;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

public class ProcessingQueue {

  private static final int MAX_FRAMES_IN_PARALLEL = 1;

  private Object framelock = new Object();

  private int framesInProgress = 0;
  private int framesTaken = 0;
  private int framesSkipped = 0;

  public boolean shouldTake() {
    synchronized(framelock) {
      if (framesInProgress < MAX_FRAMES_IN_PARALLEL) {
        framesInProgress++;
        framesTaken++;
        return true;
      } else {
        framesSkipped++;
        return false;
      }
    }
  }

  public void markComplete() {
    synchronized(framelock) {
      framesInProgress--;
    }
  }

  public void offer(byte[] data, int width, int height) {
    if (shouldTake()) {
      Log.i("AndroidVision - ProcessingQueue", "taking frame " + framesTaken);
    } else {
      Log.i("AndroidVision - ProcessingQueue", "ignoring frame " + framesSkipped);
      return;
    }

    for (int i = 0; i < width * height; i++) {
      int x = i % width;
      int y = i / width;

      if (data[i] < 0 || data[i] > 255) {
        Log.i("AndroidVision: sad value at " + x + ", " + y + " = " + data[i]);
        break;
      }
    }

    markComplete();
  }
}
