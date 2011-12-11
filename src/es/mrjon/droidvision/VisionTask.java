package es.mrjon.droidvision;

import android.app.Activity;
import android.graphics.Point;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import java.util.HashSet;
import java.util.Set;

public class VisionTask extends AsyncTask {

  private final byte[] data;
  private final int width;
  private final int height;
  private final Overlay overlay;
  private final ProcessingQueue queue;

  public VisionTask(byte[] data, int width, int height, Overlay overlay, ProcessingQueue queue) {
    this.data = data;
    this.width = width;
    this.height = height;
    this.overlay = overlay;
    this.queue = queue;
  }

  @Override
  public Object doInBackground(Object... unused) {
    Log.i("AndroidVision", "VISION TASK");
    Set<Point> lightPoints = new HashSet<Point>();

    for (int i = 0; i < width * height; i++) {
      int x = i % width;
      int y = i / width;

      int pixel = data[i] & 0xff;
      if (pixel < 0 || pixel > 255) {
        Log.i("AndroidVision", "sad value at " + x + ", " + y + " = " + data[i]);
        break;
      }
      if (pixel > 150) {
        lightPoints.add(new Point(height - y, x));
      }
    }

    Log.i("AndroidVision", "Asking overlay to color " + lightPoints.size() + " points");
    overlay.setPoints(lightPoints);
    
    return null;
  }

  public void onPostExecute(Object unused) {
    overlay.postInvalidate();
    queue.markComplete();
  }
}
