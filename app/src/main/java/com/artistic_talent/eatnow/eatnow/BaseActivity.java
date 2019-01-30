package com.artistic_talent.eatnow.eatnow;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

public class BaseActivity extends AppCompatActivity {

    FirebaseAuth auth = FirebaseAuth.getInstance();
    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    public boolean onNavigationItemSelected(MenuItem item) {
                        // Handle navigation view item clicks here.
                        int id = item.getItemId();

                        Intent i = new Intent();

                        if (id == R.id.nav_menu) {
                            i = new Intent(getApplicationContext(), MenuActivity.class);
                        } else if (id == R.id.nav_cart) {
                            i = new Intent(getApplicationContext(), CartActivity.class);
                        } else if (id == R.id.nav_orders) {

                            i = new Intent(getApplicationContext(),OrderActivity.class);


                        } else if (id == R.id.nav_qr) {
                            i = new Intent(getApplicationContext(), QRCodeActivity.class);
                        } else if (id == R.id.nav_logout) {
                            signOut();
                            i = new Intent(getApplicationContext(), MainActivity.class);
                        }

                        startActivity(i);


                        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                        drawer.closeDrawer(GravityCompat.START);
                        return true;
                    }
                });

        // Set the toolbar as the action bar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
    }

    // Open the navigation drawer when the hamburger button is clicked
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawer.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void signOut()
    {
        auth.signOut();
        finish();
    }
}
