package uk.ac.tees.t7014713.exnihilo_chatapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import uk.ac.tees.t7014713.exnihilo_chatapplication.Adapter.SectionsPagerAdapter;
import uk.ac.tees.t7014713.exnihilo_chatapplication.Model.User;

/**
 * Created by Glenn on 03/03/2019.
 */

public class UserActivity extends AppCompatActivity {

    private static final String TAG = "UserActivity";

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    private TextView profileUsername;

    FirebaseUser fuser;
    DatabaseReference databaseReference;

    /**
     * Displays the necessary information in the UserActivity
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: UserActivityStarted");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.viewPagerId);
        setupViewPager(mViewPager);

        TabLayout tabs = (TabLayout) findViewById(R.id.tabsId);
        tabs.setupWithViewPager(mViewPager);

        profileUsername = findViewById(R.id.profileUsername);
        setProfileUsername();
    }

    /**
     * Reads the FirebaseDatabase checking the current users Id
     * then looks for the username is sets it accordingly
     */
    private void setProfileUsername() {
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("user").child(fuser.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                profileUsername.setText(user.getUsername());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     * Uses SectionPagerAdapter and adds the fragments to the page
     *
     * @param viewPager
     */
    private void setupViewPager(ViewPager viewPager) {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new UserProfileFragment(), "Profile");
        adapter.addFragment(new UserSettingsFragment(), "Settings");
        viewPager.setAdapter(adapter);
    }

    /**
     * @param view
     */
    public void logout(View view) {
        AuthUI.getInstance()
              .signOut(this)
              .addOnCompleteListener(new OnCompleteListener<Void>() {
                  public void onComplete(@NonNull Task<Void> task) {
                      startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                      finish();
                  }
              });
    }
}