package uk.ac.tees.t7014713.exnihilo_chatapplication;

import android.app.Activity;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import uk.ac.tees.t7014713.exnihilo_chatapplication.Adapter.MessageAdapter;

/**
 * Created by Glenn on 11/03/2019.
 */

public class PopupActivity extends Activity {

    private Button reaction;
    public Button copyText;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_pop_up);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout(width, (int) (height*.3));
        getWindow().setGravity(Gravity.BOTTOM);

        reaction = findViewById(R.id.btnReaction);

        copyText = findViewById(R.id.btnCopy);
        copyText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                //clipboard.setText(getText());
            }
        });
    }
}
