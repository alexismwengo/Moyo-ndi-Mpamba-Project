package com.example.finalyearproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class HealthTips extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView tipTitle, tipText, healthTips;
    private ImageButton tips_share, tips_fav;
    private BottomNavigationView bottomNavigationView;
    private String [] tipTitles, tipTexts, tip_image;
    private Bundle bundle;
    private String serverUrl, phpFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_tips);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        bundle = getIntent().getExtras();
        serverUrl = bundle.getString("SERVER_URL");

        //phpFile = "accessGenTips.php";
        phpFile = "accessGenTips.php";

        tipText = (TextView) findViewById(R.id.tip_text);
        tipTitle = (TextView) findViewById(R.id.ttle);

        getTips();

        tips_share = (ImageButton) findViewById(R.id.share_icon);
        tips_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_SEND);
                i.putExtra(Intent.EXTRA_TEXT, tipText.getText().toString());
                i.setType("text/plain");
                startActivity(i);
            }
        });

        tips_fav = (ImageButton) findViewById(R.id.fav_icon);
        tips_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Added to favourites", Toast.LENGTH_SHORT).show();
                //tips_fav.setImageIcon(R.drawable.ic_favorite2);
            }
        });

        healthTips = (TextView) findViewById(R.id.healthtips_com);
        healthTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri someUri = Uri.parse("https://www.thegoodbody.com/"); //tips site
                Intent i = new Intent(Intent.ACTION_VIEW, someUri);
                startActivity(i);
            }
        });

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if(menuItem.getItemId() == R.id.tips_home){
                    new HealthTips();
                }
                else if(menuItem.getItemId() == R.id.tips_favs){
                    Toast.makeText(getApplicationContext(), "Comming soon...", Toast.LENGTH_SHORT).show();
                }
                else if(menuItem.getItemId() == R.id.tips_filter){
                    AlertDialog.Builder builder = new AlertDialog.Builder(HealthTips.this);
                    builder.setTitle("Select Tips to Show\n\n");

                    Spinner sorter = new Spinner(HealthTips.this);
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.sort_tips_array,
                            android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sorter.setAdapter(adapter);

                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);

                    sorter.setLayoutParams(lp);

                    AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            switch (parent.getItemAtPosition(position).toString()){
                                case "All Tips":
                                    phpFile = "accessGenTips.php";
                                    break;
                                case "Food & Diets":
                                    phpFile = "accessFoodTips.php";
                                    break;
                                case "Fitness":
                                    phpFile = "accessFitnessTips.php";
                                    break;
                                default:
                                    phpFile = "accessGenTips.php";
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    };
                    sorter.setOnItemSelectedListener(onItemSelectedListener);

                    builder.setView(sorter);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getTips();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                return true;
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.health_tips_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        if(menuItem.getItemId() == android.R.id.home){
            onBackPressed();
        }
        if(menuItem.getItemId() == R.id.health_tips_terms){
            Uri someUri = Uri.parse("http://www.moyondimpamba.com/borrowedtips.php");
            Intent i = new Intent(Intent.ACTION_VIEW, someUri);
            startActivity(i);
            return true;
        }
        return true;
    }

    public void getTips(){
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, serverUrl+""+phpFile,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            JSONObject jsonObject = null;

                            tipTitles = new String[jsonArray.length()];
                            tipTexts =  new String[jsonArray.length()];
                            tip_image =  new String[jsonArray.length()];

                            for(int i=0; i<jsonArray.length(); i++){
                                jsonObject = jsonArray.getJSONObject(i);

                                tipTitles[i] = jsonObject.getString("tip_title");
                                tipTexts[i] = jsonObject.getString("tip_string");
                                tip_image[i] = jsonObject.getString("image");

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        int index = (int)(Math.random()*tipTitles.length);
                        tipTitle.setText(tipTitles[index]);
                        tipText.setText(tipTexts[index]);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        queue.add(stringRequest);
    }
}
