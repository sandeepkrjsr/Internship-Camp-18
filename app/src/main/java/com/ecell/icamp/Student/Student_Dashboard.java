package com.ecell.icamp.Student;

import android.os.Bundle;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.ecell.icamp.Main.Fragment_Message_List;
import com.ecell.icamp.R;

import java.lang.reflect.Field;

/**
 * Created by Niklaus on 28-Feb-17.
 */

public class Student_Dashboard extends AppCompatActivity {

    private TextView username;

    public static String id, user;

    private Student_Dashboard.SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("id");
        user = bundle.getString("user");

        username = (TextView)findViewById(R.id.username);
        username.setText("Hello " + user + "!");

        mSectionsPagerAdapter = new Student_Dashboard.SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.beginFakeDrag();

        bottomNavigation = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        bottomNavigation.inflateMenu(R.menu.navbar_student);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();
                switch (id){
                    case R.id.db_company: fragmentChange(0); break;
                    case R.id.db_student: fragmentChange(1); break;
                    case R.id.db_message: fragmentChange(2); break;
                }
                return true;
            }
        });
        //disableShiftMode(bottomNavigation);

    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: return new Fragment_Company_List();
                case 1: return new Fragment_Profile();
                case 2: return new Fragment_Message_List();
                default:return null;
            }
        }
        @Override
        public int getCount() {
            return 3;
        }
    }

    void fragmentChange(int position) {
        mViewPager.setCurrentItem(position, true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.menu_share:
                break;
            case R.id.menu_procedure:
                break;
            case R.id.menu_tnc:
                break;
            case R.id.menu_faq:
                break;
            case R.id.menu_aboutus:
        }
        return super.onOptionsItemSelected(item);
    }

    public static void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                item.setShiftingMode(false);
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("ERROR NO SUCH FIELD", "Unable to get shift mode field");
        } catch (IllegalAccessException e) {
            Log.e("ERROR ILLEGAL ALG", "Unable to change value of shift mode");
        }
    }
}