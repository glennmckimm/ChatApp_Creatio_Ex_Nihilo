package uk.ac.tees.t7014713.exnihilo_chatapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import uk.ac.tees.t7014713.exnihilo_chatapplication.Model.User;

/**
 * Created by Glenn on 03/03/2019.
 */

public class RegisterUserActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private EditText username;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        username = findViewById(R.id.username);
        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupUser(view);
            }
        });
    }

    public void setupUser(View view) {
        if (username.getText().toString().isEmpty()) {
            Toast.makeText(RegisterUserActivity.this, "You can't leave the username field empty", Toast.LENGTH_SHORT).show();
        } else if (username.getText().toString().length() < 3 || username.getText().toString().length() > 25) {
            Toast.makeText(RegisterUserActivity.this, "Your username needs to be between 3 and 25 characters", Toast.LENGTH_SHORT).show();
        } else {
            final FirebaseUser fa = FirebaseAuth.getInstance().getCurrentUser();

            User user = new User(fa.getUid(), username.getText().toString());

            databaseReference.child("user").child(fa.getUid()).setValue(user, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    if (databaseError != null) {
                        Toast.makeText(RegisterUserActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RegisterUserActivity.this, "Profile created", Toast.LENGTH_SHORT).show();
                    }
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            });
        }
    }
}