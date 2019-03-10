package uk.ac.tees.t7014713.exnihilo_chatapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import uk.ac.tees.t7014713.exnihilo_chatapplication.Adapter.UserAdapter;
import uk.ac.tees.t7014713.exnihilo_chatapplication.Model.ConversationList;
import uk.ac.tees.t7014713.exnihilo_chatapplication.Model.Message;
import uk.ac.tees.t7014713.exnihilo_chatapplication.Model.User;

/**
 * Created by Glenn on 04/03/2019.
 */

public class ConversationsFragment extends Fragment {

    private static final String TAG = "ConversationsFragment";
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<User> mUser;
    private List<ConversationList> userList;
    FirebaseUser fuser;
    DatabaseReference databaseReference;

    public ConversationsFragment() {
        // empty public constructor
    }

    /**
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ConversationsFragment started");
        View view = inflater.inflate(R.layout.fragment_conversations, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        userList = new ArrayList<>();
        fuser = FirebaseAuth.getInstance().getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("ConversationList").child(fuser.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    ConversationList cl = ds.getValue(ConversationList.class);
                    userList.add(cl);
                }
                conversationList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }

    /**
     * Displays the users that they have either sent messages to or received messages from
     */
    private void conversationList() {
        mUser = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("user");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUser.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    User user = ds.getValue(User.class);
                    for (ConversationList cl : userList) {
                        if (user.getUserUID().equals(cl.getId())) {
                            mUser.add(user);
                        }
                    }
                }
                userAdapter = new UserAdapter(getContext(), mUser);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
