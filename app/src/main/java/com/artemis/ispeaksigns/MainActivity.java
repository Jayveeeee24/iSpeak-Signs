package com.artemis.ispeaksigns;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.artemis.ispeaksigns.main_fragments.nav_fsl_wotd;
import com.artemis.ispeaksigns.main_fragments.nav_home;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;


public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    private AppBarLayout mAppbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ///////Definitions
        ImageView toolbarImage1 = findViewById(R.id.toolbarImage1);
        TextView txtHomeGreeting1 = (TextView) findViewById(R.id.txtHomeGreeting1);
        TextView txtHomeGreeting2 = (TextView) findViewById(R.id.txtHomeGreeting2);
        mAppbarLayout = findViewById(R.id.appbar_layout);
        EditText editSearch = findViewById(R.id.editSearch);
        Toolbar mtoolbar = findViewById(R.id.toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        greetingTimeDay greeting = new greetingTimeDay();
        CollapsingToolbarLayout collapseToolbar = findViewById(R.id.collapseToolbar);
        //////End of Definitions

        FloatingActionButton mainFab = findViewById(R.id.fab);
        mainFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Papalitan ko pa, weyt lang", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
            mtoolbar.setNavigationIcon(R.drawable.ic_back);
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            //this changes the toolbar title as it has a bug of displaying only "home" title
            collapseToolbar.setTitle(navDestination.getLabel());

            mtoolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    drawer.openDrawer(GravityCompat.START);
                }
            });

            if (navDestination.getId() == R.id.nav_home) {
                drawer.closeDrawer(GravityCompat.START);
                txtHomeGreeting1.setText(getResources().getString(R.string.home_greeting1_label));
                txtHomeGreeting1.setTextSize(24);
                txtHomeGreeting2.setText(greeting.getGreeting());
                editSearch.setVisibility(View.VISIBLE);
                editSearch.setHint(getResources().getString(R.string.edit_text_hint));
                editSearch.setText("");
                toolbarImage1.setVisibility(View.VISIBLE);
                mAppbarLayout.setExpanded(true);
                mainFab.setVisibility(View.VISIBLE);
                mtoolbar.setNavigationIcon(R.drawable.ic_menu);
                drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            }
            else if (navDestination.getId() == R.id.nav_fsl_wotd) {
                mAppbarLayout.setExpanded(false);
                mainFab.setVisibility(View.INVISIBLE);

                mtoolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onBackPressed();
                    }
                });
            }
            else if (navDestination.getId() == R.id.nav_learn) {
                txtHomeGreeting1.setText(getResources().getString(R.string.learn_greeting1_label));
                txtHomeGreeting1.setTextSize(18);
                txtHomeGreeting2.setText("");
                editSearch.setVisibility(View.VISIBLE);
                editSearch.setHint(getResources().getString(R.string.edit_text_hint_learn));
                editSearch.setText("");
                toolbarImage1.setVisibility(View.INVISIBLE);
                mAppbarLayout.setExpanded(true);
                mainFab.setVisibility(View.INVISIBLE);

                mtoolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onBackPressed();
                    }
                });
            }
            else if (navDestination.getId() == R.id.nav_favorites) {
                toolbarImage1.setVisibility(View.INVISIBLE);
                editSearch.setVisibility(View.INVISIBLE);

                mAppbarLayout.setExpanded(true);
                mainFab.setVisibility(View.INVISIBLE);
                mtoolbar.setNavigationIcon(R.drawable.ic_menu);
                drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            }
            else if (navDestination.getId() == R.id.act_recognize) {
                mAppbarLayout.setExpanded(false);
                mainFab.setVisibility(View.INVISIBLE);
            }

        });
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