package com.example.finalyearproject;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Emergency extends AppCompatActivity {
    private Toolbar toolbar;
    private ImageView photoAttached;
    private ImageButton photoButton;
    private RadioGroup genderButtons;
    private RadioButton male, female;
    private EditText textSubmitted;
    private CheckBox ambulanceOperator;
    private Button emergencyTime, emergencyLocation;
    private Spinner available_centres;
    TextView sampleTextColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        SimpleDateFormat f=new SimpleDateFormat("MMMM dd yyyy  hh:mm:ss a");
        String thDate=new String(f.format(new Date()));

        sampleTextColor = (TextView) findViewById(R.id.gender_label);
        int textColor = sampleTextColor.getCurrentTextColor();

        photoAttached = (ImageView) findViewById(R.id.attached_photo);
        photoButton = (ImageButton) findViewById(R.id.camera);
        genderButtons = (RadioGroup) findViewById(R.id.gender_radiogroup);

        male = (RadioButton) findViewById(R.id.male);
        male.setTextColor(textColor);
        female = (RadioButton) findViewById(R.id.female);
        female.setTextColor(textColor);

        textSubmitted = (EditText) findViewById(R.id.additional_text);

        ambulanceOperator = (CheckBox) findViewById(R.id.ambulance_op_check);
        ambulanceOperator.setTextColor(textColor);

        emergencyTime = (Button) findViewById(R.id.emergency_time);
        emergencyTime.setText(thDate);




        emergencyLocation = (Button) findViewById(R.id.emergency_location);


        available_centres = (Spinner) findViewById(R.id.available_centres);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.hospitals_array,
                android.R.layout.simple_spinner_item);//
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        available_centres.setAdapter(adapter);
        available_centres.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.emergency_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        if(menuItem.getItemId() == android.R.id.home){
            startActivity(new Intent(getApplicationContext(), Home.class));
        }
        if(menuItem.getItemId() == R.id.send_emergency_report){
            Toast.makeText(getApplicationContext(), "Sending emergency...", Toast.LENGTH_LONG).show();
        }
        return true;
    }
}
