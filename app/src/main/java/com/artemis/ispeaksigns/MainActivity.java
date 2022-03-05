package com.artemis.ispeaksigns;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;


public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    private AppBarLayout mAppbarLayout;
    public Toolbar mtoolbar;
    public CollapsingToolbarLayout collapseToolbar;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ///////Definitions
        ImageView toolbarImage1 = findViewById(R.id.toolbarImage1);
        TextView txtHomeGreeting1 = findViewById(R.id.txtHomeGreeting1);
        TextView txtHomeGreeting2 = findViewById(R.id.txtHomeGreeting2);
        mAppbarLayout = findViewById(R.id.appbar_layout);
        EditText editSearch = findViewById(R.id.editSearch);
        mtoolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        greetingTimeDay greeting = new greetingTimeDay();
        collapseToolbar = findViewById(R.id.collapseToolbar);
        //////End of Definitions

        FloatingActionButton mainFab = findViewById(R.id.fab);
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
                R.id.act_recognize)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //Destination Listener setup
        navController.addOnDestinationChangedListener((navController1, navDestination, bundle) -> {
            //this changes the toolbar title as it has a bug of displaying only "home" title
            collapseToolbar.setTitle(navDestination.getLabel());
            editSearch.setVisibility(View.VISIBLE);
            mainFab.setVisibility(View.INVISIBLE);

            if (navDestination.getId() == R.id.nav_home) {
                drawer.closeDrawer(GravityCompat.START);
                txtHomeGreeting1.setText(getResources().getString(R.string.home_greeting1_label));
                txtHomeGreeting1.setTextSize(24);
                txtHomeGreeting2.setText(greeting.getGreeting(this));
                editSearch.setHint(getResources().getString(R.string.edit_text_hint));
                editSearch.setText("");
                toolbarImage1.setVisibility(View.VISIBLE);

                setExpandedEnabled(true);
                setToolbarSetup(true);

                mainFab.setVisibility(View.VISIBLE);
            }
            else if (navDestination.getId() == R.id.nav_fsl_wotd) {
                setExpandedEnabled(false);
                setToolbarSetup(false);
            }
            else if (navDestination.getId() == R.id.nav_learn) {
                txtHomeGreeting1.setText(getResources().getString(R.string.learn_greeting1_label));
                txtHomeGreeting1.setTextSize(18);
                txtHomeGreeting2.setText("");
                editSearch.setHint(getResources().getString(R.string.edit_text_hint_learn));
                editSearch.setText("");
                toolbarImage1.setVisibility(View.INVISIBLE);

                setExpandedEnabled(true);
                setToolbarSetup(false);
            }
            else if (navDestination.getId() == R.id.nav_favorites) {
                toolbarImage1.setVisibility(View.INVISIBLE);
                editSearch.setVisibility(View.INVISIBLE);

                setExpandedEnabled(true);
                setToolbarSetup(true);
            }
            else if (navDestination.getId() == R.id.learn_category_word){
                setExpandedEnabled(false);
                setToolbarSetup(false);
            }
        });
    }

    public void setToolbarSetup(boolean isMenu)
    {
        //This setups the toolbar icon menu to either menu or back function
        if (isMenu) {
            mtoolbar.setNavigationIcon(R.drawable.ic_menu);
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            mtoolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    drawer.openDrawer(GravityCompat.START);
                }
            });
        }else {
            mtoolbar.setNavigationIcon(R.drawable.ic_back);
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            mtoolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        }
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