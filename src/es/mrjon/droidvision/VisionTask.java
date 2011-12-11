package es.mrjon.droidvision;

import android.app.Activity;
import android.graphics.Point;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.ArrayList;
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
//    Collection<Point> lightPoints = new ArrayList<Point>(width * height / 10);
    float[] rawPoints = new float[(width * height) / 10];
    int rawPointer = 0;

      // if (pixel < 0 || pixel > 255) {
      //   Log.i("AndroidVision", "sad value at " + x + ", " + y + " = " + data[i]);
      //   break;
      // }
      // if (pixel > 150) {
      //   lightPoints.add(new Point(height - y, x));
      // }

      //   double horizGrad = Math.pow(pixelAt(x, y) - pixelAt(x, y-1), 2);
      //   double vertGrad = Math.pow(pixelAt(x, y) - pixelAt(x-1, y), 2);
      //   double gradient = Math.sqrt(horizGrad + vertGrad);

    int pixel, leftPixel, topPixel, x, y, hGrad, vGrad;;
    double gradient;

    long start = System.currentTimeMillis();
    for (int i = 0; i < width * height; i++) {
      x = i % width;
      y = i / width;

      if (x > 0 && y > 0) { 
        pixel = data[i] & 0xff;
        leftPixel = data[i - 1] & 0xff;
        topPixel = data[i - width] & 0xff;

//        gradient = Math.sqrt(Math.pow(pixel - topPixel, 2) + Math.pow(pixel - leftPixel, 2));
        hGrad = pixel - topPixel;
        vGrad = pixel - leftPixel;
        if (hGrad > 25 || hGrad < -25 || vGrad > 25 || vGrad < -25) {
//          lightPoints.add(new Point(height - y, x));
          rawPoints[rawPointer++] = height - y;
          rawPoints[rawPointer++] = .9f * x;
        } 
      }
    }

    long end = System.currentTimeMillis();
//    Log.i("AndroidVision", "Asking overlay to color " + lightPoints.size() + " points");
    Log.i("AndroidVision", " * * * * * * * * * * * * * VISION TASK TOOK : " + (end-start) + "ms");
//    overlay.setPoints(lightPoints);
    overlay.setRawPoints(rawPoints);
    
    return null;
  }

  private int pixelAt(int x, int y) {
    int i = y * width + x;
    int pixel = data[i] & 0xff;

    return pixel;
  }

  public void onPostExecute(Object unused) {
    overlay.postInvalidate();
    queue.markComplete();
  }
}
