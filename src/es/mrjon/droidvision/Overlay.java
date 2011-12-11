package es.mrjon.droidvision;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class Overlay extends View {

  private SurfaceHolder surfaceHolder;
  private Collection<Point> points;

  public Overlay(Context context) {
    super(context);
  }

  public void setPoints(Collection<Point> points) {
    this.points = points;
  }

  @Override
  protected void onDraw(Canvas canvas) {
    Log.d("AndroidVision - CameraView", "*** OVERLAY DRAWING ***");

    Paint red = new Paint(Color.RED);
    // canvas.drawText("PREVIEW", canvas.getWidth() / 2,
    //                 canvas.getHeight() / 2, red);

    // canvas.drawRect(100, 100, 200, 200, red);
    if (points != null) {
      Log.d("AndroidVision - CameraView", "*** " + points.size() + " points ***");
      float[] canvasPoints = new float[2 * points.size()];
      int i = 0;
      for (Point p : points) {
        canvasPoints[i++] = p.x;
        canvasPoints[i++] = p.y;
//        canvas.drawPoint(p.x, p.y, red);
      }
      canvas.drawPoints(canvasPoints, red);
    }
  }
}
