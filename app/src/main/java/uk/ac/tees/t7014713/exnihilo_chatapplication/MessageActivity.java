package uk.ac.tees.t7014713.exnihilo_chatapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import uk.ac.tees.t7014713.exnihilo_chatapplication.Adapter.MessageAdapter;
import uk.ac.tees.t7014713.exnihilo_chatapplication.Model.Message;
import uk.ac.tees.t7014713.exnihilo_chatapplication.Model.User;

/**
 * Created by Glenn on 04/03/2019.
 */

public class MessageActivity extends AppCompatActivity {

    private TextView username;
    private ImageButton btnSend;
    private EditText txtSend;
    private Button btnOpenGallery;
    private static final int GALLERY_INTENT = 2;

    private FirebaseUser fUser;
    private DatabaseReference databaseReference;

    private MessageAdapter messageAdapter;
    private List<Message> mMessage;
    private RecyclerView recyclerView;

    /**
     * When a user is clicked on, this activity is called and displays the sent messages on screen
     *
     * @param savedInstanceState
     */
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

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        username = findViewById(R.id.username);
        txtSend = findViewById(R.id.txtSend);

        Intent intent = getIntent();
        final String userID = intent.getStringExtra("username");

        fUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("user").child(userID);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                username.setText(user.getUsername());

                readMessage(fUser.getUid(), userID);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnSend = findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = txtSend.getText().toString();
                if (!msg.equals("")) {
                    sendMessage(fUser.getUid(), userID, msg);
                } else {
                    Toast.makeText(MessageActivity.this, "You can't send empty messages", Toast.LENGTH_SHORT).show();
                }
                txtSend.setText("");
            }
        });

        btnOpenGallery = findViewById(R.id.openGallery);
        btnOpenGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INTENT);
            }
        });
    }

    /**
     * Hashmap for messages, sending the data to FirebaseDatabase then later being read
     *
     * There are databaseReferences so that the users conversations are added into the arraylist
     * and displayed in the ConversationsFragment - unless they're already there then it does nothing
     *
     * @param sender
     * @param receiver
     * @param message
     */
    private void sendMessage(String sender, final String receiver, String message) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);

        //ConversationList - the current user is placed in the receiver's conversation tab and vice versa
        databaseReference.child("chats").push().setValue(hashMap);
        final DatabaseReference msgReference = FirebaseDatabase.getInstance()
                                                               .getReference("ConversationList")
                                                               .child(fUser.getUid())
                                                               .child(receiver);
        msgReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    msgReference.child("id").setValue(receiver);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final DatabaseReference msgReferenceReceiver = FirebaseDatabase.getInstance()
                                                                  .getReference("ConversationList")
                                                                  .child(receiver)
                                                                  .child(fUser.getUid());
        msgReferenceReceiver.child("id").setValue(fUser.getUid());
    }

    /**
     * As the method name says - ReadMessages -
     * allows the users to be able to see the message sent and received
     *
     * Will also have profileImage added to it later
     *
     * @param senderID
     * @param receiverID
     */
    private void readMessage(final String senderID, final String receiverID) {
        mMessage = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("chats");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mMessage.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Message message = ds.getValue(Message.class);
                    if(message.getReceiver().equals(senderID) && message.getSender().equals(receiverID) ||
                            message.getReceiver().equals(receiverID) && message.getSender().equals(senderID)) {
                        mMessage.add(message);
                    }

                    messageAdapter = new MessageAdapter(MessageActivity.this, mMessage);
                    recyclerView.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
