package uk.ac.tees.t7014713.exnihilo_chatapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

import uk.ac.tees.t7014713.exnihilo_chatapplication.Model.User;

/**
 * Created by Glenn on 03/03/2019.
 */

public class RegisterUserActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private final String userID = UUID.randomUUID().toString();
    private Uri profileImage;
    private Button btnUploadProfileImage;
    private static final int GALLERY_INTENT = 2;
    private EditText username;
    private Button btnRegister;
    private ProgressDialog progressDialog;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();

        username = findViewById(R.id.username);
        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupUser(view);
            }
        });

        btnUploadProfileImage = findViewById(R.id.uploadImage);
        btnUploadProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INTENT);
            }
        });
        progressDialog = new ProgressDialog(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {
            progressDialog.setMessage("Uploading...");
            progressDialog.show();

            profileImage = data.getData();

            StorageReference filePath = storageReference.child("profileImages").child(userID);
            filePath.putFile(profileImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(RegisterUserActivity.this, "Upload completed", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
        }
    }

    /**
     * If the user is not registered with a username, the user will require one. And there is also
     * the option to set the profile image too
     *
     * @param view
     */
    public void setupUser(View view) {

        if (username.getText().toString().isEmpty()) {
            Toast.makeText(RegisterUserActivity.this, "You can't leave the username field empty", Toast.LENGTH_SHORT).show();
        } else if (username.getText().toString().length() < 3 || username.getText().toString().length() > 25) {
            Toast.makeText(RegisterUserActivity.this, "Your username needs to be between 3 and 25 characters", Toast.LENGTH_SHORT).show();
        } else if (profileImage == null) {
            Toast.makeText(RegisterUserActivity.this, "Upload a profile image", Toast.LENGTH_SHORT).show();
        } else {
            progressDialog.setMessage("Creating user profile...");
            progressDialog.show();

            final FirebaseUser fa = FirebaseAuth.getInstance().getCurrentUser();

            User user = new User(fa.getUid(), username.getText().toString(), username.getText().toString().toLowerCase(), userID);

            databaseReference.child("user").child(fa.getUid()).setValue(user, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    if (databaseError != null) {
                        Toast.makeText(RegisterUserActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RegisterUserActivity.this, "Profile created", Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            });
        }
    }
}