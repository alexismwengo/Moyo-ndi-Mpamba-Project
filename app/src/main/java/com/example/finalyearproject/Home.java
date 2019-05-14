package com.example.finalyearproject;

import android.content.DialogInterface;
import android.content.Intent;
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

        userEmail = (TextView) findViewById(R.id.user_email);
        userName = (TextView) findViewById(R.id.user_name);

        localHospital = (CardView) findViewById(R.id.local_hospital);
        localHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, LocalHospitals.class));
            }
        });

        privateDoctor = (CardView) findViewById(R.id.private_doctor);
        privateDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, PrivateDoctors.class);

                Bundle bun = getIntent().getExtras();

                Bundle b = new Bundle();
                b.putString("email", bun.getString("email"));
                b.putString("password", bun.getString("password"));
                intent.putExtras(b);
                startActivity(intent);
            }
        });



        myAppointments = (CardView) findViewById(R.id.my_appointments);
        myAppointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.137.1/AccessUserInfo.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONArray jsonArray = new JSONArray(response);

                                    JSONObject jsonObject = null;

                                    name = new String[jsonArray.length()];

                                    for(int i=0; i<jsonArray.length(); i++){
                                        jsonObject = jsonArray.getJSONObject(i);

                                        name[i] = jsonObject.getString("name");
                                    }

                                } catch (JSONException e) {
                                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                    e.printStackTrace();
                                }

                                Intent intent = new Intent(Home.this, MyAppointments.class);
                                Bundle bun = getIntent().getExtras();

                                Bundle b = new Bundle();
                                b.putString("name", name[0]);
                                intent.putExtras(b);

                                startActivity(intent);

                                //Toast.makeText(getApplicationContext(), name[0], Toast.LENGTH_SHORT).show();
                            }
                        }
                        , new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> params = new HashMap<String, String>();
                        params.put("email", bundle.getString("email"));
                        params.put("password", bundle.getString("password"));
                        return params;
                    }
                };
                MySingleton.getInstance(getApplicationContext()).addTorequestque(stringRequest);

            }
        });

        healthTips = (CardView) findViewById(R.id.health_tips);
        healthTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, HealthTips.class));
            }
        });

        e_mergency = (CardView) findViewById(R.id.e_mergency);
        e_mergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, Emergency.class));
            }
        });

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.home:
                        menuItem.setChecked(true);
                        //something here
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.profile:
                        menuItem.setChecked(true);
                        //something here
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.notifications:
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
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
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
}
