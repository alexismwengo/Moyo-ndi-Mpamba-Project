package com.example.finalyearproject;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AppointmentBooker extends AppCompatActivity {
    private Toolbar toolbar;
    Bundle bundle;
    private String firstname, lastname, email, city, phone, doc_info, second_phone, proffession, message, days_available, hours, appointment_time;
    private TextView doc_name, doc_location, doc_profession, show_date;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    private Button bookingButton;

    private RadioGroup radioGroup, radioGroup2;
    private EditText patient_name, patient_age, patient_phone, patient_address, additional_comments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_booker);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        bundle = getIntent().getExtras();

        //Toast.makeText(getApplicationContext(), bundle.getString("user_name"), Toast.LENGTH_SHORT).show();

        firstname = bundle.getString("firstname");
        lastname = bundle.getString("lastname");
        email = bundle.getString("email");
        city = bundle.getString("city");
        phone = bundle.getString("phone");
        proffession = bundle.getString("proffession");
        days_available = bundle.getString("days_available");
        hours = bundle.getString("hours");

        doc_name = (TextView)findViewById(R.id.doc_name);
        doc_name.setText("Dr. "+firstname+" "+lastname);

        doc_profession = (TextView)findViewById(R.id.doc_profession);
        doc_profession.setText(proffession);

        doc_location = (TextView)findViewById(R.id.doc_location);
        doc_location.setText(city);

        show_date = (TextView) findViewById(R.id.show_date);

        show_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DATE);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getApplicationContext(),
                        dateSetListener, year, month, day);
                datePickerDialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = dayOfMonth+" / "+month+" / "+year;
                show_date.setText(date);
            }
        };

        radioGroup = (RadioGroup) findViewById(R.id.radio_button);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if(radioGroup2.isSelected()){
                    radioGroup2.setSelected(false);
                }

                int selectedId = radioGroup.getCheckedRadioButtonId();
                RadioButton rdbtn = (RadioButton)findViewById(selectedId);
                appointment_time = rdbtn.getText().toString()+" am";

                Toast.makeText(getApplicationContext(), appointment_time, Toast.LENGTH_SHORT).show();
            }
        });
        radioGroup2 = (RadioGroup) findViewById(R.id.radio_button2);
        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if(radioGroup.isSelected()){
                    radioGroup.setSelected(false);
                }

                int selectedId = radioGroup2.getCheckedRadioButtonId();
                RadioButton rdbtn = (RadioButton)findViewById(selectedId);
                appointment_time = rdbtn.getText().toString()+" pm";

                Toast.makeText(getApplicationContext(), appointment_time, Toast.LENGTH_SHORT).show();
            }
        });

        patient_name = findViewById(R.id.patient_name);
        patient_name.setText(bundle.getString("user_name"));

        patient_age = findViewById(R.id.patient_age);
        patient_address = findViewById(R.id.patient_address);
        patient_phone = findViewById(R.id.patient_phone);
        additional_comments = findViewById(R.id.patient_message);

        bookingButton = findViewById(R.id.booking_button);
        bookingButton.setOnClickListener(new View.OnClickListener() {
            AlertDialog.Builder builder;
            @Override
            public void onClick(View v) {

                builder = new AlertDialog.Builder(AppointmentBooker.this);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.137.1/appointmentBooker.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(final String response) {

                                builder.setTitle("Server Response");

                                if(response.equals("Booking Successful")){
                                    builder.setMessage(response+"\nAwait Doctor Confirmation.");
                                }
                                else if(response.equals("Booking Unsuccessful")){
                                    builder.setMessage(response+" \nTry again later.");
                                }

                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if(response.equals("Booking Successful")){
                                            patient_age.setText("");
                                            patient_address.setText("");
                                            patient_phone.setText("");
                                            additional_comments.setText("");
                                            radioGroup.setSelected(false);
                                            radioGroup2.setSelected(false);
                                            startActivity(new Intent(AppointmentBooker.this, Home.class));
                                        }
                                    }
                                });
                                AlertDialog alertDialog = builder.create();
                                //alertDialog.setIcon(R.drawable.ic_info_outline);
                                alertDialog.show();
                            }
                        }
                        , new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Toast.makeText(getApplicationContext(), "You have to select a time for the Appointment", Toast.LENGTH_SHORT).show();
                        radioGroup.requestFocus();
                        error.printStackTrace();
                        //startActivity(new Intent(getApplicationContext(), Home.class));
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        String dateString = format.format(new Date());

                        Map<String, String> params = new HashMap<String, String>();
                        params.put("patient_age", patient_age.getText().toString().trim());
                        params.put("patient_name", bundle.getString("user_name"));
                        params.put("patient_address", patient_address.getText().toString().trim());
                        params.put("patient_phone", patient_phone.getText().toString().trim());
                        params.put("patient_message", additional_comments.getText().toString().trim());
                        params.put("date", dateString);
                        params.put("appointment_time", appointment_time);
                        params.put("doc_city", city);
                        params.put("doctor_firstname", firstname);
                        params.put("doctor_lastname", lastname);

                        return params;
                    }
                };

                MySingleton.getInstance(AppointmentBooker.this).addTorequestque(stringRequest);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.booking_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        if(menuItem.getItemId() == android.R.id.home){
            Intent i = new Intent(getApplicationContext(), PrivateDoctors.class);
            startActivity(i);
        }
        if(menuItem.getItemId() == R.id.photo){
            Toast.makeText(getApplicationContext(),"Photo added.", Toast.LENGTH_LONG).show();
        }
        return true;
    }
}
