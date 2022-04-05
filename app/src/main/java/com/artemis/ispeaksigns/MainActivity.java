package com.artemis.ispeaksigns;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    private AppBarLayout mAppbarLayout;
    public Toolbar mtoolbar;
    public CollapsingToolbarLayout collapseToolbar;
    private DrawerLayout drawer;
    private SwitchCompat drawerSwitch;
    private NavController navController;
    private NavigationView navigationView;
    DBHelper DB;
    Boolean profileState;
    Boolean searchState;

    //for userUpdate
    String userName = "";
    int longestStreak = 0;
    int currentStreak = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        sharedPref = new SharedPref(this);
//        if (sharedPref.loadNightModeState() == true){
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//        }else{
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DB = new DBHelper(this);

        ///////Variable Definitions and Initializations
        ImageView toolbarImage1 = findViewById(R.id.toolbarImage1);
        TextView txtHomeGreeting1 = findViewById(R.id.txtHomeGreeting1);
        TextView txtHomeGreeting2 = findViewById(R.id.txtHomeGreeting2);
        mAppbarLayout = findViewById(R.id.appbar_layout);
        TextView txtSearch = findViewById(R.id.search_hint);
        CardView editSearchParent = findViewById(R.id.editSearchParent);
        mtoolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        FunctionHelper functionHelper = new FunctionHelper();
        collapseToolbar = findViewById(R.id.collapseToolbar);
        FloatingActionButton mainFab = findViewById(R.id.fab);
        RelativeLayout aboutToolbar = findViewById(R.id.about_toolbar);
        RelativeLayout defaultToolbar = findViewById(R.id.default_toolbar);
        //////End of Definitions

        onUserLogin();
        userStreak();

        mainFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RecognizeActivity.class));
            }
        });
        setSupportActionBar(mtoolbar);

        //Setup for Navigation Drawer and AppBar
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,
                R.id.nav_learn,
                R.id.nav_fsl_wotd,
                R.id.nav_favorites,
                R.id.act_recognize,
                R.id.nav_cvsu,
                R.id.nav_mini_game,
                R.id.nav_about)
                .setOpenableLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        InitializeMenuSetting();

        //Destination Listener setup
        navController.addOnDestinationChangedListener((navController1, navDestination, bundle) -> {

            //this changes the toolbar title as it has a bug of displaying only "home" title
            collapseToolbar.setTitle(navDestination.getLabel());
            editSearchParent.setVisibility(View.VISIBLE);
            mainFab.setVisibility(View.INVISIBLE);

            aboutToolbar.setVisibility(View.INVISIBLE);
            defaultToolbar.setVisibility(View.VISIBLE);
            collapseToolbar.setBackgroundResource(R.drawable.appbar_bg);
            setExpandedEnabled(false);
            profileState = false;
            searchState=false;
            invalidateOptionsMenu();

            mtoolbar.setNavigationIcon(R.drawable.ic_back);
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            mtoolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });

            editSearchParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    navController.navigate(R.id.nav_search);
                }
            });

            if (navDestination.getId() == R.id.nav_home) {
                drawer.closeDrawer(GravityCompat.START);
                txtHomeGreeting1.setText(getResources().getString(R.string.home_greeting1_label, userName));
                txtHomeGreeting1.setTextSize(20);
                txtHomeGreeting2.setTextSize(19);
                txtHomeGreeting2.setText(functionHelper.getGreeting(this));
                txtSearch.setText(getResources().getString(R.string.edit_text_hint));
                toolbarImage1.setVisibility(View.VISIBLE);
                collapseToolbar.setBackgroundResource(R.drawable.cvsu);
                setExpandedEnabled(true);
                profileState = true;
                invalidateOptionsMenu();

                mainFab.setVisibility(View.VISIBLE);

                mtoolbar.setNavigationIcon(R.drawable.ic_menu);
                drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                mtoolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        drawer.openDrawer(GravityCompat.START);
                    }
                });
            }  else if (navDestination.getId() == R.id.nav_learn) {
                txtHomeGreeting1.setText(getResources().getString(R.string.learn_greeting1_label));
                txtHomeGreeting1.setTextSize(18);
                txtHomeGreeting2.setText("");
                txtSearch.setText(getResources().getString(R.string.edit_text_hint_learn));
                toolbarImage1.setVisibility(View.INVISIBLE);

                setExpandedEnabled(true);
                profileState = true;
                invalidateOptionsMenu();
            } else if (navDestination.getId() == R.id.nav_favorites) {
                txtHomeGreeting1.setText(getResources().getString(R.string.favorite_greeting1_label));
                txtHomeGreeting1.setTextSize(18);
                txtHomeGreeting2.setTextSize(15);
                txtHomeGreeting2.setText(getResources().getString(R.string.favorite_greeting2_label));
                toolbarImage1.setVisibility(View.INVISIBLE);
                editSearchParent.setVisibility(View.INVISIBLE);

                setExpandedEnabled(true);
            } else if (navDestination.getId() == R.id.nav_about) {
                setExpandedEnabled(true);
                defaultToolbar.setVisibility(View.INVISIBLE);
                aboutToolbar.setVisibility(View.VISIBLE);
                collapseToolbar.setBackgroundResource(R.drawable.cvsu);
            }
        });
    }

    public void InitializeMenuSetting()
    {
        drawerSwitch = (SwitchCompat) navigationView.getMenu().findItem(R.id.darkmode).getActionView();
//        if (sharedPref.loadNightModeState() == true)
//        {
//            drawerSwitch.setChecked(true);
//        }
        drawerSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//                    sharedPref.setNightModeState(true);
//                    restartApp();
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//                    sharedPref.setNightModeState(false);
//                    restartApp();
                }
            }
        });

        MenuItem language = navigationView.getMenu().findItem(R.id.language);
        language.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (profileState) {
            getMenuInflater().inflate(R.menu.profile_menu, menu);
            return super.onCreateOptionsMenu(menu);
        }
        return false;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.menuProfile)
        {
            Bundle bundle = new Bundle();
            bundle.putString("userName", userName);
            navController.navigate(R.id.nav_profile, bundle);
        }
        return super.onOptionsItemSelected(item);
    }

    public void restartApp()
    {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

    public void setExpandedEnabled(boolean isEnabled)
    {
        //this creates a new appbarlayout scrolling behavior if not present
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) mAppbarLayout.getLayoutParams();
        if (params.getBehavior() == null) {
            params.setBehavior(new AppBarLayout.Behavior());
        }
        AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();

        //this function setups the collapse toolbar to expand/collapse with drag/cannot drag the collapse toolbar
        if (isEnabled) {
            mAppbarLayout.setExpanded(true);
            behavior.setDragCallback(new AppBarLayout.Behavior.DragCallback() {
                @Override
                public boolean canDrag(@NonNull AppBarLayout appBarLayout) {
                    return true;
                }
            });
        }else{
            mAppbarLayout.setExpanded(false);
            behavior.setDragCallback(new AppBarLayout.Behavior.DragCallback() {
                @Override
                public boolean canDrag(@NonNull AppBarLayout appBarLayout) {
                    return false;
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void userStreak(){

        Cursor userDataCursor = DB.getUserData("", "GetNameStreak");

        if (userDataCursor.getCount()==0){
            Toast.makeText(this, "No value on database found!", Toast.LENGTH_SHORT).show();
        }else{
            while (userDataCursor.moveToNext()){
                userName = userDataCursor.getString(0);
                longestStreak = userDataCursor.getInt(1);
            }

        }

        currentStreak = getStreak();

        if (currentStreak>longestStreak){
            longestStreak = currentStreak;
            boolean checkUpdateStreak = DB.UpdateMultipleData(new int[]{currentStreak, longestStreak}, null, "Streak");
            if (checkUpdateStreak){
                Log.i("LONGEST STREAK UPDATE", Integer.toString(longestStreak));
                Log.i("STREAK UPDATE", Integer.toString(currentStreak));
            }else{
                Toast.makeText(MainActivity.this, "Streak Update gone wrong!", Toast.LENGTH_SHORT).show();
            }
        }else{
            boolean checkUpdateStreak = DB.updateSingleData(null, currentStreak, "Streak");
            if (checkUpdateStreak){
                Log.i("STREAK UPDATE", Integer.toString(currentStreak));
            }else{
                Toast.makeText(MainActivity.this, "Streak Update gone wrong!", Toast.LENGTH_SHORT).show();
            }
        }


    }


    //FOR USER STREAK LOGIN
    public void onUserLogin() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Date date = new Date();
        String today = dateFormat.format(date);
        String lastLoginDay = getLastLoginDate();

        String yesterday = getYesterdayDate(dateFormat, date);

        if (lastLoginDay == null) {
            // user logged in for the first time
            updateLastLoginDate(today);
            incrementDays();
        } else {
            if (lastLoginDay.equals(today)) {
                // User logged in the same day , do nothing
            } else if (lastLoginDay.equals(yesterday)) {
                // User logged in consecutive days , add 1
                updateLastLoginDate(today);
                incrementDays();
            } else {
                // It's been more than a day user logged in, reset the counter to 1
                updateLastLoginDate(today);
                resetDays();
            }
        }
    }

    private String getYesterdayDate(DateFormat simpleDateFormat, Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -1);
        return simpleDateFormat.format(calendar.getTime());
    }

    private void updateLastLoginDate(String date) {
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("last_login_day", date);
        editor.apply();
    }

    private String getLastLoginDate() {
        String lastLogin = null;
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        lastLogin = sharedPref.getString("last_login_day", null);
        return lastLogin;
    }

    private int getConsecutiveDays() {
        int days = 0;
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        days = sharedPref.getInt("num_consecutive_days", 0);
        return days;
    }

    private void incrementDays() {
        int days = getConsecutiveDays() + 1;
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("num_consecutive_days", days);
        editor.apply();
    }

    private void resetDays() {
        int days = 1;
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("num_consecutive_days", days);
        editor.apply();
    }

    public int getStreak() {
        return getConsecutiveDays();
    }
}