package es.mrjon.droidvision;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.List;

public class AnnotatedCameraView extends SurfaceView implements SurfaceHolder.Callback {

  private SurfaceHolder surfaceHolder;
  private Camera camera;

  public AnnotatedCameraView(Camera camera, Context context) {
    super(context);

    surfaceHolder = getHolder();
    surfaceHolder.addCallback(this);
    surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

    this.camera = camera;
  }

  @Override
  public void surfaceCreated(SurfaceHolder holder) {
    try {
      camera.setPreviewDisplay(holder);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void surfaceDestroyed(SurfaceHolder holder) {
    camera.stopPreview();
    camera.release();
    camera = null;
  }

  @Override
  public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
  }

  @Override
  public void draw(Canvas canvas) {
    super.draw(canvas);
    Paint p = new Paint(Color.RED);
    Log.d("AndroidVision - CameraView", "draw");
    canvas.drawText("PREVIEW", canvas.getWidth() / 2,
                    canvas.getHeight() / 2, p);
  }
}
