package es.mrjon.droidvision;

import android.app.Activity;
import android.graphics.Point;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import java.util.HashSet;
import java.util.Set;

public class ProcessingQueue {

  private static final int MAX_FRAMES_IN_PARALLEL = 1;

  private final Overlay overlay;

  private Object framelock = new Object();

  private int framesInProgress = 0;
  private int framesTaken = 0;
  private int framesSkipped = 0;

  public ProcessingQueue(Overlay overlay) {
    this.overlay = overlay;
  }

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
//      Log.i("AndroidVision - ProcessingQueue", "ignoring frame " + framesSkipped);
      return;
    }

    VisionTask task = new VisionTask(data, width, height, overlay, this);
    task.execute();

    // move to post execute?
//    markComplete();
  }
}
