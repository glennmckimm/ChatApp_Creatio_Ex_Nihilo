package uk.ac.tees.t7014713.exnihilo_chatapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import uk.ac.tees.t7014713.exnihilo_chatapplication.Model.User;

/**
 * Created by Glenn on 04/03/2019.
 */

public class MessageActivity extends AppCompatActivity {

    ImageView profile_image; //Will eventually attempt to implement profile images
    TextView username;

    FirebaseUser fuser;
    DatabaseReference databaseReference;

    ImageButton btnSend;
    EditText txtSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        username = findViewById(R.id.username);
        txtSend = findViewById(R.id.txtSend);
        btnSend = findViewById(R.id.btnSend);

        Intent intent = getIntent();
        final String userID = intent.getStringExtra("username");

        fuser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("user").child(userID);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                username.setText(user.getUsername());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = txtSend.getText().toString();
                if (msg != null) {
                    sendMessage(fuser.getUid(), userID, msg);
                } else {
                    Toast.makeText(MessageActivity.this, "You can't send empty messages", Toast.LENGTH_SHORT).show();
                }
                txtSend.setText("");
            }
        });
    }

    private void sendMessage(String sender, String receiver, String message) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);

        databaseReference.child("chats").push().setValue(hashMap);
    }
}
