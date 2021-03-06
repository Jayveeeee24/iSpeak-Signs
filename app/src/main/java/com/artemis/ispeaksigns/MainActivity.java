package com.artemis.ispeaksigns;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private AppBarConfiguration mAppBarConfiguration;
    private AppBarLayout mAppbarLayout;
    public Toolbar mtoolbar;
    public CollapsingToolbarLayout collapseToolbar;
    private DrawerLayout drawer;
    private NavController navController;
    private NavigationView navigationView;

    FunctionHelper functionHelper = new FunctionHelper();
    DBHelper DB;
    Boolean profileState;
    Boolean searchState;
    String languageCode = "";

    //for userUpdate
    String userName = "";
    int longestStreak = 0;
    int currentStreak = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //sets the app locale when changing the language via the option on navigation menu
        //DO NOT REMOVE OR ELSE THE LANGUAGE WON'T UPDATE WHEN CHANGING LANGUAGE IN THE NAVIGATION MENU
        functionHelper.setAppLocale(this);

        DB = new DBHelper(this);
        onUserLogin();//this triggers the function when the user logs in
        setContentView(R.layout.activity_main);

        initializeTrivia();
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
        collapseToolbar = findViewById(R.id.collapseToolbar);
        FloatingActionButton mainFab = findViewById(R.id.fab);
        RelativeLayout aboutToolbar = findViewById(R.id.about_toolbar);
        RelativeLayout defaultToolbar = findViewById(R.id.default_toolbar);
        ImageView collapseToolbarImage = findViewById(R.id.collapse_toolbar_image);
        ImageView isBadgeImage = findViewById(R.id.isBadgeImage);
        //////End of Definitions

        mainFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Fab - floating action button (camera button)
                startActivity(new Intent(MainActivity.this, RecognizeActivity.class));
            }
        });

        setSupportActionBar(mtoolbar);
        //Setup for Navigation Drawer and AppBar (lists all top navigation for appbar)
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,
                R.id.nav_learn,
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
            getUserStreak();
            //this changes the toolbar title as it has a bug of displaying only "home" title
            collapseToolbar.setTitle(navDestination.getLabel());
            isBadgeImage.setVisibility(View.INVISIBLE);

            editSearchParent.setVisibility(View.VISIBLE);
            mainFab.setVisibility(View.INVISIBLE);
            aboutToolbar.setVisibility(View.INVISIBLE);
            defaultToolbar.setVisibility(View.VISIBLE);

            //toolbar bg for curved green and the cvsu bg
            collapseToolbar.setBackgroundResource(R.drawable.appbar_bg);
            //bg for mag-aral and favorite toolbar bg
            collapseToolbarImage.setImageDrawable(null);

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
                if (functionHelper.isBadgeVisible(MainActivity.this) == 1){
                    isBadgeImage.setVisibility(View.VISIBLE);
                    isBadgeImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(MainActivity.this, getResources().getString(R.string.badge_toast), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                mainFab.setVisibility(View.VISIBLE);

                mtoolbar.setNavigationIcon(R.drawable.ic_menu);
                drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                mtoolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        drawer.openDrawer(GravityCompat.START);
                    }
                });
            } else if (navDestination.getId() == R.id.nav_learn) {
                txtHomeGreeting1.setText(getResources().getString(R.string.learn_greeting1_label));
                txtHomeGreeting1.setTextSize(18);
                txtHomeGreeting2.setText("");
                txtSearch.setText(getResources().getString(R.string.edit_text_hint_learn));
                toolbarImage1.setVisibility(View.INVISIBLE);
                collapseToolbarImage.setImageResource(R.drawable.ic_magaral_bg);

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
                collapseToolbarImage.setImageResource(R.drawable.ic_favorite_bg);

                setExpandedEnabled(true);
            } else if (navDestination.getId() == R.id.nav_about) {
                setExpandedEnabled(true);
                defaultToolbar.setVisibility(View.INVISIBLE);
                aboutToolbar.setVisibility(View.VISIBLE);
                collapseToolbar.setBackgroundResource(R.drawable.cvsu);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //this function is triggered when the acitivity is started or restarted
        // (ex. from VideoActivity to the MainActivity)
        functionHelper.setAppLocale(this);
    }

    public void InitializeMenuSetting()
    {
        MenuItem fslWord = navigationView.getMenu().findItem(R.id.nav_fsl_wotd);
        fslWord.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                String[] getFavorite = functionHelper.getWordOfTheDay(MainActivity.this);
                String word = "";
                word = getFavorite[0];
                Bundle bundle = new Bundle();
                bundle.putString("learn_word_item", word);
                navController.navigate(R.id.learn_word_item, bundle);

                return true;
            }
        });

        MenuItem language = navigationView.getMenu().findItem(R.id.language);
        language.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                final Dialog changeLanguage = new Dialog(MainActivity.this);
                changeLanguage.setContentView(R.layout.popup_change_language);
                changeLanguage.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
                changeLanguage.setCanceledOnTouchOutside(true);
                changeLanguage.setCancelable(true);

                final Spinner changeLanguageSpinner = (Spinner) changeLanguage.findViewById(R.id.change_language_spinner);
                final Button changeLanguageSave = (Button) changeLanguage.findViewById(R.id.change_language_save);
                List<String> languages = new ArrayList<>();
                languages.add("Filipino");
                languages.add("English");

                ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, languages);
                changeLanguageSpinner.setAdapter(dataAdapter);
                changeLanguageSpinner.setOnItemSelectedListener(MainActivity.this);
                changeLanguage.show();

                changeLanguageSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean checkInsertLanguage = DB.updateSingleData(languageCode, 0, "Language");
                        if (checkInsertLanguage){
                            Log.i("Splash Activity", "Update Language Success!");
                        }else{
                            Log.i("Splash Activity", "Update Language Failed!");
                        }
                        changeLanguage.dismiss();
                        restartApp();
                    }
                });
                return true;
            }
        });
    }

    private void initializeTrivia(){
        int min = 0;
        int random = (int)(Math.random() * (9 - min+1)+min);

        String[] trivia = new String[]{
                getString(R.string.trivia1),
                getString(R.string.trivia2),
                getString(R.string.trivia3),
                getString(R.string.trivia4),
                getString(R.string.trivia5),
                getString(R.string.trivia6),
                getString(R.string.trivia7),
                getString(R.string.trivia8),
                getString(R.string.trivia9)
        };
        final Dialog triviaDialog = new Dialog(MainActivity.this);
        triviaDialog.setContentView(R.layout.popup_trivia);
        triviaDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
        triviaDialog.setCanceledOnTouchOutside(true);
        triviaDialog.setCancelable(true);
        CardView triviaOk = triviaDialog.findViewById(R.id.trivia_ok);
        TextView triviaSub = triviaDialog.findViewById(R.id.trivia_sub);

        triviaDialog.show();
        triviaSub.setText(trivia[random]);
        triviaOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                triviaDialog.dismiss();
            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        if (parent.getItemAtPosition(position).toString().equals("Filipino")){
            languageCode = "tl";
        }else if (parent.getItemAtPosition(position).toString().equals("English")){
            languageCode = "en";
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        languageCode = "tl";
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

    public void getUserStreak(){

        Cursor userDataCursor = DB.getUserData("GetNameStreak");

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
                Log.i("LOGIN UPDATE", "USER LOGGED THE SAME DAY");
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
        String lastLogin;
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        lastLogin = sharedPref.getString("last_login_day", null);
        return lastLogin;
    }

    private int getConsecutiveDays() {
        int days;
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