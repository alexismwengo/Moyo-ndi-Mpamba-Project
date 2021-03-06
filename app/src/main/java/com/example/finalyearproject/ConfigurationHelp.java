package com.example.finalyearproject;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

public class ConfigurationHelp extends AppCompatActivity {
    private TextView firstTextView, secondTextView, fourthTextView;
    private Toolbar toolbar;
    private Bundle bundle;
    private String serverUrl, activity_from;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration_help);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        bundle = getIntent().getExtras();
        serverUrl = bundle.getString("SERVER_URL");
        activity_from = bundle.getString("activity");

        firstTextView = (TextView) findViewById(R.id.first_textview);
        firstTextView.setText("Internet & Database Connection Configurations");

        String help = "The following resources must be available:\n\n\t"+
                "a) the moyondimpamba.zip file that contains the database schema\n\n\t"+
                "b) phpscripts.zip file\n\n"+
                "Below are a sequence of steps explaining how to configure Apaches XAMPP (localhost) server to host the Moyo-ndi-Mpamba system database:\n\n\t"+
                "1) Ensure that you use the default user settings for your databases in phpMyAdmin (username = 'root', password = ' ')\n\n\t"+
                "2) Create a new database in phpMyAdmin by importing the \"moyondimpamba.zip\" file\n\n\t"+
                "3) Extract the \"phpscripts.zip\" to get the individual files.\n\n\t"+
                "4) Place all the newly extracted files into \"htdocs\" folder (General Path on a Windows Machine: 'C:\\xampp\\htdocs')";

        secondTextView = (TextView) findViewById(R.id.second_textview);
        secondTextView.setText(help);

        final String link = "https://github.com/alexismwengo/Moyo-ndi-Mpamba-Project.git";

        fourthTextView = (TextView) findViewById(R.id.fourth_textview);
        fourthTextView.setText(link);
        fourthTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri someUri = Uri.parse(link);
                Intent i = new Intent(Intent.ACTION_VIEW, someUri);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        if(menuItem.getItemId() == android.R.id.home){
            Intent intent = null;
            if(activity_from.equals("LoginActivity")){
                intent = new Intent(ConfigurationHelp.this, LoginActivity.class);
            }
            else if(activity_from.equals("ActivitySignUp")){
                intent = new Intent(ConfigurationHelp.this, ActivitySignUp.class);
            }

            Bundle b = new Bundle();
            b.putString("SERVER_URL", serverUrl);
            b.putString("activity", "ConfigurationHelp");
            intent.putExtras(b);

            startActivity(intent);
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = null;
        if(activity_from.equals("LoginActivity")){
            intent = new Intent(ConfigurationHelp.this, LoginActivity.class);
        }
        else if(activity_from.equals("ActivitySignUp")){
            intent = new Intent(ConfigurationHelp.this, ActivitySignUp.class);
        }

        Bundle b = new Bundle();
        b.putString("SERVER_URL", serverUrl);
        b.putString("activity", "ConfigurationHelp");
        intent.putExtras(b);

        startActivity(intent);
    }
}
