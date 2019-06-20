package com.example.finalyearproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Home extends AppCompatActivity {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private TextView userEmail, userName;
    private CardView localHospital, privateDoctor, myAppointments, healthTips, e_mergency;
    private Bundle bundle, bundle2;
    private String serverUrl;
    private String name[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

        bundle = getIntent().getExtras();
        serverUrl = bundle.getString("SERVER_URL");

        userEmail = (TextView) findViewById(R.id.user_email);
        userName = (TextView) findViewById(R.id.user_name);

        localHospital = (CardView) findViewById(R.id.local_hospital);
        localHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, LocalHospitals.class);
                Bundle b = new Bundle();

                b.putString("USERNAME", bundle.getString("USERNAME"));
                b.putString("SERVER_URL", serverUrl);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        privateDoctor = (CardView) findViewById(R.id.private_doctor);
        privateDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, PrivateDoctors.class);
                Bundle b = new Bundle();

                b.putString("USERNAME", bundle.getString("USERNAME"));
                b.putString("SERVER_URL", serverUrl);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        myAppointments = (CardView) findViewById(R.id.my_appointments);
        myAppointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Home.this, MyAppointments.class);
                Bundle b = new Bundle();

                b.putString("USERNAME", bundle.getString("USERNAME"));
                b.putString("SERVER_URL", serverUrl);
                intent.putExtras(b);

                startActivity(intent);

            }
        });

        healthTips = (CardView) findViewById(R.id.health_tips);
        healthTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, HealthTips.class);
                Bundle b = new Bundle();

                b.putString("SERVER_URL", serverUrl);
                b.putString("USERNAME", bundle.getString("USERNAME"));
                intent.putExtras(b);

                startActivity(intent);
            }
        });

        e_mergency = (CardView) findViewById(R.id.e_mergency);
        e_mergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Emergency.class);
                Bundle b = new Bundle();

                b.putString("activity", "home_activity");
                b.putString("SERVER_URL", serverUrl);
                //the following if statements aren't needed.
                //code needed is b.putString("USERNAME", bundle.getString("USERNAME"));
                if(bundle.containsKey("USERNAME")){
                    b.putString("USERNAME", bundle.getString("USERNAME"));
                }
                else {
                    b.putString("USERNAME", "User Name");
                }
                //b.putString("USERNAME", bundle.getString("USERNAME"));
                intent.putExtras(b);

                startActivity(intent);
            }
        });

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.user_name);
        navUsername.setText(bundle.getString("USERNAME"));
        TextView navUserEmail = (TextView) headerView.findViewById(R.id.user_email);
        navUserEmail.setText(bundle.getString("USEREMAIL"));

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                TextView username = findViewById(R.id.user_name);
                username.setText(bundle.getString("USERNAME"));
                switch (menuItem.getItemId()){
                    case R.id.home:
                        menuItem.setChecked(true);
                        new Home();
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.settings:
                        menuItem.setChecked(true);
                        //something here
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.help:
                        menuItem.setChecked(true);
                        //something here
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.about:
                        menuItem.setChecked(true);
                        //something here
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.logout:
                        menuItem.setChecked(true);
                        Intent intent = new Intent(Home.this, LoginActivity.class);
                        Bundle b = new Bundle();
                        b.putString("SERVER_URL", serverUrl);
                        b.putString("activity", "Home");
                        intent.putExtras(b);
                        startActivity(intent);
                        return true;
                }
                return false;
            }
        });

        //userEmail.setText(email);
        //userName = (TextView) findViewById(R.id.user_name);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.homemenu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        if(menuItem.getItemId() == android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        if(menuItem.getItemId() == R.id.customise){
            Toast.makeText(getApplication(), "Unable to customise layout", Toast.LENGTH_SHORT).show();
            return true;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            //drawer is open so close it
            drawerLayout.closeDrawers();
        }
        else{
            Intent intent = new Intent(Home.this, LoginActivity.class);
            Bundle b = new Bundle();
            b.putString("SERVER_URL", serverUrl);
            b.putString("activity", "Home");
            intent.putExtras(b);
            startActivity(intent);
        }
    }
}
