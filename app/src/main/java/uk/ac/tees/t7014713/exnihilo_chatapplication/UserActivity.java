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

import uk.ac.tees.t7014713.exnihilo_chatapplication.Adapter.SectionsPagerAdapter;

/**
 * Created by Glenn on 03/03/2019.
 */

public class UserActivity extends AppCompatActivity {

    private static final String TAG = "UserActivity";

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    private TextView profileUsername;

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

        /**
         * Get username to show in profileUsername
         * Look up the fa.getUId and then set it
         */
        profileUsername = findViewById(R.id.profileUsername);
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new UserProfileFragment(), "Profile");
        adapter.addFragment(new UserSettingsFragment(), "Settings");
        viewPager.setAdapter(adapter);
    }

    public void logout(View view) {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
    }
}