package com.app.league.goldenboys.leagueapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.app.league.goldenboys.leagueapp.Admin.Activities.AdminAddOrganizerActivity;
import com.app.league.goldenboys.leagueapp.Admin.Fragments.TabHostAdminFragment;
import com.app.league.goldenboys.leagueapp.Customer.Fragments.TabHostCustomerFragment;
import com.app.league.goldenboys.leagueapp.Fragments.AboutUsFragment;
import com.app.league.goldenboys.leagueapp.Fragments.ProfileFragment;
import com.app.league.goldenboys.leagueapp.Organizer.Fragments.TabHostOrganizerFragment;
import com.app.league.goldenboys.leagueapp.OtherModule.SelectItemsActivity;
import com.app.league.goldenboys.leagueapp.OtherModule.UploadImageActivity;
import com.app.league.goldenboys.leagueapp.Receptionist.Fragments.TabHostReceptionistFragment;
import com.google.firebase.auth.FirebaseAuth;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String usertype;
    private FirebaseAuth mAuth;

    MenuItem navHomeCustomer;
    MenuItem navHomeOrganizer;
    MenuItem navHomeReceptionist;
    MenuItem navHomeAdmin;
    MenuItem navReservation;
    MenuItem navActivityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        mAuth = FirebaseAuth.getInstance();

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Menu sideMenu = navigationView.getMenu();

        usertype = getIntent().getStringExtra("Usertype");

        navHomeCustomer = sideMenu.findItem(R.id.nav_home_customer);
        navHomeOrganizer = sideMenu.findItem(R.id.nav_home_organizer);
        navHomeReceptionist = sideMenu.findItem(R.id.nav_home_receptionist);
        navHomeAdmin = sideMenu.findItem(R.id.nav_home_admin);

        navReservation = sideMenu.findItem(R.id.nav_reservation);
        navActivityManager = sideMenu.findItem(R.id.nav_activitymanager);

        navHomeCustomer.setVisible(false);
        navHomeOrganizer.setVisible(false);
        navHomeReceptionist.setVisible(false);
        navHomeAdmin.setVisible(false);
        navReservation.setVisible(false);
        navActivityManager.setVisible(false);

        if (usertype.equals("customer")) {
            Toast.makeText(NavigationActivity.this, "Login as " + usertype, Toast.LENGTH_SHORT).show();

            loginAsCustomer();

        } else if (usertype.equals("organizer")) {
            Toast.makeText(NavigationActivity.this, "Login as " + usertype, Toast.LENGTH_SHORT).show();

            loginAsOrganizer();

        } else if (usertype.equals("admin")) {
            Toast.makeText(NavigationActivity.this, "Login as " + usertype, Toast.LENGTH_SHORT).show();

            loginAsAdmin();

        } else if (usertype.equals("receptionist")) {
            Toast.makeText(NavigationActivity.this, "Login as " + usertype, Toast.LENGTH_SHORT).show();

            loginAsReceptionist();
        }

//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.container_main_drawer, new TabHostFragment())
//                .commit();

    }

    private void loginAsReceptionist() {

        getSupportActionBar().setTitle("Receptionist");

        navHomeReceptionist.setVisible(true);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_main_drawer, new TabHostReceptionistFragment())
                .commit();

    }

    private void loginAsAdmin() {
        getSupportActionBar().setTitle("Admin");

        navHomeAdmin.setVisible(true);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_main_drawer, new TabHostAdminFragment())
                .commit();

    }

    private void loginAsOrganizer() {
        getSupportActionBar().setTitle("Organizer");

        navHomeOrganizer.setVisible(true);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_main_drawer, new TabHostOrganizerFragment())
                .commit();
    }

    private void loginAsCustomer() {
        getSupportActionBar().setTitle("Customer");

        navHomeCustomer.setVisible(true);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_main_drawer, new TabHostCustomerFragment())
                .commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);

        MenuItem menuAdminAddOrganizer = menu.findItem(R.id.admin_menu_register_organizer);

        menuAdminAddOrganizer.setVisible(false);

        if (usertype.equals("admin")) {
            menuAdminAddOrganizer.setVisible(true);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //startActivity(new Intent(NavigationActivity.this, UploadImageActivity.class));
            startActivity(new Intent(NavigationActivity.this, SelectItemsActivity.class));
            return true;
        } else if (id == R.id.admin_menu_register_organizer) {
            startActivity(new Intent(getApplicationContext(), AdminAddOrganizerActivity.class));
        } else if (id == R.id.action_logout) {
            startActivity(new Intent(NavigationActivity.this, LoginActivity.class));
            mAuth.signOut();
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home_customer) {
            // Handle the camera action
            getSupportActionBar().setTitle("Customer");

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container_main_drawer, new TabHostCustomerFragment())
                    .commit();

        } else if (id == R.id.nav_home_organizer) {

            getSupportActionBar().setTitle("Organizer");

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container_main_drawer, new TabHostOrganizerFragment())
                    .commit();

        } else if (id == R.id.nav_home_receptionist) {

            getSupportActionBar().setTitle("Receptionist");

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container_main_drawer, new TabHostReceptionistFragment())
                    .commit();

        } else if (id == R.id.nav_home_admin) {

            getSupportActionBar().setTitle("Admin");

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container_main_drawer, new TabHostAdminFragment())
                    .commit();

        } else if (id == R.id.nav_activitymanager) {

        } else if (id == R.id.nav_reservation) {

        } else if (id == R.id.nav_profile) {

            getSupportActionBar().setTitle("Profile");

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container_main_drawer, new ProfileFragment())
                    .commit();

        } else if (id == R.id.nav_aboutus) {

            getSupportActionBar().setTitle("About Us");

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container_main_drawer, new AboutUsFragment())
                    .commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}