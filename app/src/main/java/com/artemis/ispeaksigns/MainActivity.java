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
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.switchmaterial.SwitchMaterial;


public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    private AppBarLayout mAppbarLayout;
    public Toolbar mtoolbar;
    public CollapsingToolbarLayout collapseToolbar;
    private DrawerLayout drawer;
    private SwitchCompat drawerSwitch;
    int NightMode;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ///////Variable Definitions and Initializations
        ImageView toolbarImage1 = findViewById(R.id.toolbarImage1);
        TextView txtHomeGreeting1 = findViewById(R.id.txtHomeGreeting1);
        TextView txtHomeGreeting2 = findViewById(R.id.txtHomeGreeting2);
        mAppbarLayout = findViewById(R.id.appbar_layout);
        EditText editSearch = findViewById(R.id.editSearch);
        CardView editSearchParent = findViewById(R.id.editSearchParent);
        mtoolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        greetingTimeDay greeting = new greetingTimeDay();
        collapseToolbar = findViewById(R.id.collapseToolbar);
        FloatingActionButton mainFab = findViewById(R.id.fab);
        //////End of Definitions

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
                R.id.nav_mini_game)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        drawerSwitch = (SwitchCompat) navigationView.getMenu().findItem(R.id.darkmode).getActionView();
        drawerSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });

        //Destination Listener setup
        navController.addOnDestinationChangedListener((navController1, navDestination, bundle) -> {
            //this changes the toolbar title as it has a bug of displaying only "home" title
            collapseToolbar.setTitle(navDestination.getLabel());
            editSearch.setVisibility(View.VISIBLE);
            editSearchParent.setVisibility(View.VISIBLE);
            mainFab.setVisibility(View.INVISIBLE);

            mtoolbar.setNavigationIcon(R.drawable.ic_back);
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            mtoolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });

            if (navDestination.getId() == R.id.nav_home) {
                drawer.closeDrawer(GravityCompat.START);
                txtHomeGreeting1.setText(getResources().getString(R.string.home_greeting1_label));
                txtHomeGreeting1.setTextSize(24);
                txtHomeGreeting2.setText(greeting.getGreeting(this));
                editSearch.setHint(getResources().getString(R.string.edit_text_hint));
                editSearch.setText("");
                toolbarImage1.setVisibility(View.VISIBLE);

                setExpandedEnabled(true);

                mainFab.setVisibility(View.VISIBLE);
                mtoolbar.setNavigationIcon(R.drawable.ic_menu);
                drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                mtoolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        drawer.openDrawer(GravityCompat.START);
                    }
                });
            }
            else if (navDestination.getId() == R.id.nav_fsl_wotd) {
                setExpandedEnabled(false);
            }
            else if (navDestination.getId() == R.id.nav_learn) {
                txtHomeGreeting1.setText(getResources().getString(R.string.learn_greeting1_label));
                txtHomeGreeting1.setTextSize(18);
                txtHomeGreeting2.setText("");
                editSearch.setHint(getResources().getString(R.string.edit_text_hint_learn));
                editSearch.setText("");
                toolbarImage1.setVisibility(View.INVISIBLE);

                setExpandedEnabled(true);
            }
            else if (navDestination.getId() == R.id.nav_favorites) {
                toolbarImage1.setVisibility(View.INVISIBLE);
                editSearch.setVisibility(View.INVISIBLE);
                editSearchParent.setVisibility(View.INVISIBLE);

                setExpandedEnabled(true);
            }
            else if (navDestination.getId() == R.id.learn_category_word){
                setExpandedEnabled(false);
            }
            else if (navDestination.getId() == R.id.learn_category_video){
                setExpandedEnabled(false);
            }
            else if (navDestination.getId() == R.id.nav_cvsu){
                setExpandedEnabled(false);
            }
            else if (navDestination.getId() == R.id.nav_mini_game){
                setExpandedEnabled(false);
            }
        });
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

}