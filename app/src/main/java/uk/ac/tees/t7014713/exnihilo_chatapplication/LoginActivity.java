package uk.ac.tees.t7014713.exnihilo_chatapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Glenn on 02/03/2019.
 */

public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            authentication();
        }
    }

    /**
     * Chooses an authentication provider then creates and launches the sign-in intent
     * @param view
     */
    public void login(View view) {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.GoogleBuilder().build());

        startActivityForResult(
                AuthUI.getInstance()
                      .createSignInIntentBuilder()
                      .setAvailableProviders(providers)
                      .build(),
                       RC_SIGN_IN);
    }

    /**
     * Either sends the user to the next activity, or shows an error message
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                authentication();
            } else {
                Toast.makeText(LoginActivity.this, "Sign in failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Checks to see whether the user has a username or not
     */
    private void authentication() {
        final FirebaseUser fa = FirebaseAuth.getInstance().getCurrentUser();

        databaseReference.child("user").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot ds = dataSnapshot.child(fa.getUid()).child("username");
                if (ds.exists() && ds.getValue(String.class) != null) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                } else {
                    startActivity(new Intent(getApplicationContext(), RegisterUserActivity.class));
                }
                finish();
            }
            @Override public void onCancelled(DatabaseError databaseError) { }
        });
    }
}