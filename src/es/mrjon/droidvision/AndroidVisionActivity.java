package es.mrjon.droidvision;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.TextView;

public class AndroidVisionActivity extends Activity {

  private AnnotatedCameraView cameraView;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    Camera camera = Camera.open();

    cameraView = new AnnotatedCameraView(camera, this);
    //((FrameLayout) findViewById(R.id.preview)).addView(cameraView);

    final TextView text = new TextView(this);
    text.setText("test");
    // ((FrameLayout) findViewById(R.id.preview)).addView(text);
    
    final Overlay overlay = new Overlay(this);
    
//    ((FrameLayout) findViewById(R.id.preview)).addView(overlay);
    setContentView(cameraView);

    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
      LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);

//    addContentView(text, layoutParams);
    addContentView(overlay, layoutParams);

    Camera.Parameters parameters = camera.getParameters();
    parameters.set("rotation", 90);
    parameters.set("orientation", "portrait");
    camera.setParameters(parameters);

    final Camera.Size size = parameters.getPreviewSize();
    final ProcessingQueue queue = new ProcessingQueue(overlay);

    camera.setPreviewCallback(new PreviewCallback() {
        public void onPreviewFrame(byte[] data, Camera camera) {
//          Log.i("AndroidVision", "Got " + data.length + " bytes");
          queue.offer(data, size.width, size.height);
        }
      });

    camera.startPreview();
  }

  
}
