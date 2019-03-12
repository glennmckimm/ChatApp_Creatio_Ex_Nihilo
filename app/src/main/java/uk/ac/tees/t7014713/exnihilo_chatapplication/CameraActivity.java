package uk.ac.tees.t7014713.exnihilo_chatapplication;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

/**
 * Created by Glenn on 12/03/2019.
 */

public class CameraActivity extends AppCompatActivity {

    private SurfaceView surfaceView;
    private ImageButton goBack;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_camera);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                                  WindowManager.LayoutParams.FLAG_FULLSCREEN);

        surfaceView = findViewById(R.id.cameraPreview);
        surfaceView.setZOrderMediaOverlay(true);

        goBack = findViewById(R.id.goBack);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });
    }
}
