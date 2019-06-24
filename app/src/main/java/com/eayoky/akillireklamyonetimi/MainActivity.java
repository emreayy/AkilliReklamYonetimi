package com.eayoky.akillireklamyonetimi;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;
    private FirebaseAuth.AuthStateListener authListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById (R.id.toolbar);
        setSupportActionBar (toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById (R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle (
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener (toggle);
        toggle.syncState ( );

        NavigationView navigationView = (NavigationView) findViewById (R.id.nav_view);
        navigationView.setNavigationItemSelectedListener (this);

        auth = FirebaseAuth.getInstance ( );
        firebaseUser = FirebaseAuth.getInstance ( ).getCurrentUser ( );

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById (R.id.drawer_layout);
        if (drawer.isDrawerOpen (GravityCompat.START)) {
            drawer.closeDrawer (GravityCompat.START);
        } else {
            super.onBackPressed ( );
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId ( );

        if (id == R.id.pp) {
            item.setTitle (auth.getCurrentUser ( ).getEmail ( ));
            Toast.makeText (MainActivity.this, auth.getCurrentUser ( ).getEmail ( ), Toast.LENGTH_SHORT).show ( );

        } else if (id == R.id.key) {
            Intent intent = new Intent (MainActivity.this, ChangePassword.class);
            startActivity (intent);
        } else if (id == R.id.quit) {
            Intent intent2 = new Intent (MainActivity.this, Login.class);
            startActivity (intent2);
            finish ( );
        } else if (id == R.id.kampanya) {
            Intent intent = new Intent (MainActivity.this, EntryData.class);
            startActivity (intent);

        } else if (id == R.id.show) {
            Intent intent = new Intent (MainActivity.this, ShowData.class);
            startActivity (intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById (R.id.drawer_layout);
        drawer.closeDrawer (GravityCompat.START);
        return true;
    }

}
