package com.example.finalyearproject;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class HealthTips extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView healthTips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_tips);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        healthTips = (TextView) findViewById(R.id.healthtips_com);
        healthTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri someUri = Uri.parse("http://www.healthtips.com"); //tips site
                Intent i = new Intent(Intent.ACTION_VIEW, someUri);
                startActivity(i);
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
        if(menuItem.getItemId() == R.id.refresh_tips){
            startActivity(new Intent(getApplicationContext(), HealthTips.class));
            return true;
        }
        if(menuItem.getItemId() == R.id.share_tip){
            Toast.makeText(getApplicationContext(), "To be emplemented soon...", Toast.LENGTH_SHORT).show();
            return true;
        }
        return true;
    }
}
