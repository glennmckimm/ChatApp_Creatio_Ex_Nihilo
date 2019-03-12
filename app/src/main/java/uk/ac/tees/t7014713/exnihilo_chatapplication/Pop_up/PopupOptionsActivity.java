package uk.ac.tees.t7014713.exnihilo_chatapplication.Pop_up;

import android.app.Activity;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import uk.ac.tees.t7014713.exnihilo_chatapplication.R;

/**
 * Created by Glenn on 11/03/2019.
 */

public class PopupOptionsActivity extends Activity {

    private Button btnGif;
    private Button btnCamera;
    private Button btnGallery;

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
        btnCamera = findViewById(R.id.btnCamera);
        btnGallery = findViewById(R.id.btnGallery);
    }
}
