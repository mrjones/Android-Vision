package es.mrjon.droidvision;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.io.IOException;
import java.util.List;

public class Overlay extends View {

  private SurfaceHolder surfaceHolder;

  public Overlay(Context context) {
    super(context);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    Log.d("AndroidVision - CameraView", "*** OVERLAY DRAWING ***");
  }
}
