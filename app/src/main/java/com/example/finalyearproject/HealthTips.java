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
import android.widget.ImageButton;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_tips);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        tipText = (TextView) findViewById(R.id.tip_text);
        final StringBuilder stringBuilder = new StringBuilder("");
        /*Thread getTipTextThread = new Thread(new Runnable() {
            @Override
            public void run() {*/

                try {
                    Document healthTips = Jsoup.connect("https://www.thegoodbody.com/health-facts/").get();
                    Elements headings = healthTips.select("a");
                    for (Element element: headings){
                        stringBuilder.append(element+"\n");
                    }
                    tipText.setText(stringBuilder.toString());

                }catch(Exception e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                /*runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tipText.setText(stringBuilder.toString());
                    }
                });

            }
        });
        getTipTextThread.start();*/

        tipTitle = (TextView) findViewById(R.id.ttle);

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
                //tips_fav.setImageIcon(R.drawable.ic_share);
            }
        });

        healthTips = (TextView) findViewById(R.id.healthtips_com);
        healthTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri someUri = Uri.parse("http://www.healthtips.com"); //tips site
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
                    Toast.makeText(getApplicationContext(), "Comming soon as well", Toast.LENGTH_SHORT).show();
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

    class FetchData extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            URL tipsUrl = null;
            try {
                /*tipsUrl = new URL("https://www.thegoodbody.com/health-facts/");
                HttpURLConnection httpURLConnection = (HttpURLConnection) tipsUrl.openConnection();

                StringBuilder builder = new StringBuilder("");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String line = "";
                while ((line = bufferedReader.readLine()) != null){
                    builder.append(bufferedReader.readLine());
                }
                tipTitle.setText("heghggg");*/

                StringBuilder stringBuilder = new StringBuilder();

                Document healthTips = Jsoup.connect("https://www.thegoodbody.com/health-facts/").get();
                Elements headings = healthTips.select("h2");
                for (Element elements: headings){
                    stringBuilder.append(headings+"\n");
                }

                tipText.setText(stringBuilder.toString());

            }/* catch (MalformedURLException e) {
                e.printStackTrace();//Shouldn't happen
            }*/ catch (Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
