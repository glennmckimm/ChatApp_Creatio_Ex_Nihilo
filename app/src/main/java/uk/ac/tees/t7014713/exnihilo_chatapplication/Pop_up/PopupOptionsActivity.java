package uk.ac.tees.t7014713.exnihilo_chatapplication.Pop_up;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;


import uk.ac.tees.t7014713.exnihilo_chatapplication.CameraActivity;
import uk.ac.tees.t7014713.exnihilo_chatapplication.R;

/**
 * Created by Glenn on 11/03/2019.
 */

public class PopupOptionsActivity extends Activity {

    private Button btnGif;
    private Button btnCamera;
    private Button btnGallery;

    private static final int GALLERY_INTENT = 2;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_options_pop_up);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout(width, (int) (height*.07));
        getWindow().setGravity(Gravity.BOTTOM);

        btnGif = findViewById(R.id.btnGifs);
        btnGif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnCamera = findViewById(R.id.btnCamera);
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Consider looking in CAMERA API 2
                startActivity(new Intent(getApplicationContext(), CameraActivity.class));
            }
        });

        btnGallery = findViewById(R.id.btnGallery);
        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(Intent.ACTION_PICK).setType("image/*"), GALLERY_INTENT);
            }
        });
    }
}
