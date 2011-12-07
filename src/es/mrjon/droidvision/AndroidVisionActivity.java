package es.mrjon.droidvision;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

public class AndroidVisionActivity extends Activity {

  private AnnotatedCameraView cameraView;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    Camera camera = Camera.open();

    cameraView = new AnnotatedCameraView(camera, this);
    ((FrameLayout) findViewById(R.id.preview)).addView(cameraView);

    Camera.Parameters parameters = camera.getParameters();
    parameters.set("rotation", 90);
    parameters.set("orientation", "portrait");
    camera.setParameters(parameters);

    camera.setPreviewCallback(new PreviewCallback() {
        public void onPreviewFrame(byte[] data, Camera camera) {
          Log.i("AndroidVision", "Got " + data.length + " bytes");
        }
      });

    camera.startPreview();
  }

  
}
