package uk.ac.tees.t7014713.exnihilo_chatapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import uk.ac.tees.t7014713.exnihilo_chatapplication.Adapter.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private static final int TIME_DELAY = 2000;
    private static long backPressed;
    private Button btnUser;

    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.viewPagerId);
        setupViewPager(mViewPager);

        TabLayout tabs = (TabLayout) findViewById(R.id.tabsId);
        tabs.setupWithViewPager(mViewPager);

        btnUser = (Button) findViewById(R.id.btnUser);
        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                startActivity(new Intent(MainActivity.this, UserActivity.class));
            }
        });
    }

    /**
     * SectionPagerAdapter so that the Conversations and Users tabs are displayed on the main screen
     * @param viewPager
     */
    private void setupViewPager(ViewPager viewPager) {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ConversationsFragment(), "Conversations");
        adapter.addFragment(new ChatUsersFragment(), "Users");
        viewPager.setAdapter(adapter);
    }

    /**
     * Asks the user to press the back button twice before closing the application
     */
    @Override
    public void onBackPressed() {
        if (backPressed + TIME_DELAY > System.currentTimeMillis()) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this,"Press BACK again to exit", Toast.LENGTH_SHORT).show();
        }
        backPressed = System.currentTimeMillis();
    }
}
