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
import java.util.HashMap;
import java.util.Map;

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
                StringRequest stringRequest = new StringRequest(Request.Method.POST, serverUrl+"postFavourites.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if(!response.equals("Error!") && !response.equals("Tip already in Favourites")){
                                    //tips_fav.setImageResource(R.drawable.ic_favorite2);
                                    Toast.makeText(HealthTips.this, "Added to Favourites", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(HealthTips.this, response, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        , new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Toast.makeText(HealthTips.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> params = new HashMap<String, String>();

                        if(tipText.getText().toString().length()>0 && tipTitle.getText().toString().length()>0){
                            params.put("title", tipTitle.getText().toString());
                            params.put("text", tipText.getText().toString());
                            params.put("user", bundle.getString("USERNAME"));
                        }
                        else {
                            Toast.makeText(HealthTips.this, "Nothing in Favourites", Toast.LENGTH_SHORT).show();
                        }
                        return params;
                    }
                };
                MySingleton.getInstance(HealthTips.this).addTorequestque(stringRequest);
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
                    phpFile = "accessGenTips.php";
                    getTips();
                }
                else if(menuItem.getItemId() == R.id.tips_favs){
                    phpFile = "getFavourites.php";
                    getTips();
                    //Toast.makeText(getApplicationContext(),"favs", Toast.LENGTH_SHORT).show();
                }
                else if(menuItem.getItemId() == R.id.tips_filter){
                    AlertDialog.Builder builder = new AlertDialog.Builder(HealthTips.this);
                    builder.setTitle("Choose Tips to Show");
                    Spinner sorter = new Spinner(HealthTips.this);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(20, 2, 20, 2);
                    sorter.setLayoutParams(lp);
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.sort_tips_array,
                            android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sorter.setAdapter(adapter);

                    AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            switch (parent.getItemAtPosition(position).toString()){
                                case "All Tips":
                                    phpFile = "accessGenTips.php";
                                    bottomNavigationView.getMenu().findItem(R.id.tips_filter).setTitle( "All Tips");
                                    break;
                                case "Food & Diets":
                                    phpFile = "accessFoodTips.php";
                                    bottomNavigationView.getMenu().findItem(R.id.tips_filter).setTitle( "Food & Diets");
                                    break;
                                case "Fitness":
                                    phpFile = "accessFitnessTips.php";
                                    bottomNavigationView.getMenu().findItem(R.id.tips_filter).setTitle( "Fitness");
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
        StringRequest stringRequest = new StringRequest(Request.Method.POST, serverUrl.concat(phpFile),
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

                                tipTitles[i] = jsonObject.getString("title");
                                tipTexts[i] = jsonObject.getString("text");
                                tip_image[i] = jsonObject.getString("image");

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        int index = (int)(Math.random()*tipTitles.length);
                        tipTitle.setText(tipTitles[index]);
                        tipText.setText(tipTexts[index]);
                    }
                }
                , new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
            }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                params.put("user", bundle.getString("USERNAME"));

                return params;
            }
        };

        MySingleton.getInstance(HealthTips.this).addTorequestque(stringRequest);

    }
}
