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
import java.util.List;
import java.util.Set;

public class Overlay extends View {

  private SurfaceHolder surfaceHolder;
  private Set<Point> points;

  public Overlay(Context context) {
    super(context);
  }

  public void setPoints(Set<Point> points) {
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
      for (Point p : points) {
        canvas.drawPoint(p.x, p.y, red);
      }
    }
  }
}
