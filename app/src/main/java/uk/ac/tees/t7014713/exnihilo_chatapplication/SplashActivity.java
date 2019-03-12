package uk.ac.tees.t7014713.exnihilo_chatapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Glenn on 02/03/2019.
 */

public class SplashActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;

    /**
     * Basic splash screen that is displayed each time to app is loaded
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        Thread myThread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(2000);
                    checkUsersAccount();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();
    }

    private void checkUsersAccount() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            final FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();

            databaseReference.child("user").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    DataSnapshot ds = dataSnapshot.child(fUser.getUid()).child("username");

                    if (ds.exists() && ds.getValue(String.class) != null) {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    } else {
                        startActivity(new Intent(getApplicationContext(), RegisterUserActivity.class));
                    }
                    finish();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }
    }
}
