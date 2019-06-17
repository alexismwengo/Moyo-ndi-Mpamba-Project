package com.example.finalyearproject;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
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
import android.widget.ImageView;
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
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AppointmentBooker extends AppCompatActivity {
    private Toolbar toolbar;
    Bundle bundle;
    private String dateString, firstname, lastname, city, phone, proffession, days_available, hours, appointment_time;
    private TextView name, location, profession, show_date;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private Button bookingButton;
    private ImageView doc_hospital_image;
    private RadioGroup radioGroup, radioGroup2;
    private EditText patient_name, patient_age, patient_phone, patient_address, additional_comments;
    private Calendar myCalendar;
    private String serverUrl, activityFrom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_booker);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        bundle = getIntent().getExtras();
        serverUrl = bundle.getString("SERVER_URL");
        activityFrom = bundle.getString("activity");

        firstname = bundle.getString("firstname");
        lastname = bundle.getString("lastname");
        city = bundle.getString("city");
        phone = bundle.getString("phone");
        proffession = bundle.getString("proffession");
        days_available = bundle.getString("days_available");
        hours = bundle.getString("hours");
        doc_hospital_image = (ImageView) findViewById(R.id.doc_image);

        name = (TextView)findViewById(R.id.doc_name);
        profession = (TextView)findViewById(R.id.doc_profession);
        location = (TextView)findViewById(R.id.doc_location);

        if(activityFrom.equals("Doctors")){
            name.setText("Dr. "+firstname+" "+lastname);
            profession.setText(proffession);
        }
        else if(activityFrom.equals("HealthCentre")){
            name.setText(bundle.getString("hospital_name"));
            doc_hospital_image.setImageResource(R.drawable.image_hosp);

            profession.setText(days_available);
            Drawable img = getApplicationContext().getResources().getDrawable( R.drawable.ic_date2);
            profession.setCompoundDrawablesWithIntrinsicBounds( img, null, null, null);
        }
        location.setText(city);

        show_date = (TextView) findViewById(R.id.show_date);

        myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        show_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AppointmentBooker.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

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

                //Toast.makeText(getApplicationContext(), appointment_time, Toast.LENGTH_SHORT).show();
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
        patient_name.setText(bundle.getString("USERNAME"));

        patient_age = findViewById(R.id.patient_age);
        patient_address = findViewById(R.id.patient_address);
        patient_phone = findViewById(R.id.patient_phone);
        additional_comments = findViewById(R.id.patient_message);

        bookingButton = findViewById(R.id.booking_button);
        bookingButton.setOnClickListener(new View.OnClickListener() {
            AlertDialog.Builder builder;
            @Override
            public void onClick(View v) {

                if(show_date.getText().toString().equals(null)){
                    Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                }

                builder = new AlertDialog.Builder(AppointmentBooker.this);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, serverUrl+"appointmentBooker.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(final String response) {

                                if(response.equals("Booking Successful")){
                                    builder.setMessage(response+"\nAwait Doctor Confirmation.");
                                }
                                else{
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
                                            onBackPressed();
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
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> params = new HashMap<String, String>();
                        params.put("patient_age", patient_age.getText().toString().trim());
                        params.put("patient_name", bundle.getString("USERNAME"));
                        params.put("patient_address", patient_address.getText().toString().trim());
                        params.put("patient_phone", patient_phone.getText().toString().trim());
                        params.put("patient_message", additional_comments.getText().toString().trim());
                        params.put("date", dateString);
                        params.put("appointment_time", appointment_time);
                        params.put("city", city);
                        params.put("phone", phone);

                        if(activityFrom.equals("Doctors")){
                            params.put("sent_to", "Private Doctor");
                            params.put("name", firstname+" "+lastname);
                        }
                        else if(activityFrom.equals("HealthCentre")){
                            params.put("sent_to", "Health Facility");
                            params.put("name", bundle.getString("hospital_name"));
                        }
                        return params;
                    }
                };

                MySingleton.getInstance(AppointmentBooker.this).addTorequestque(stringRequest);
            }
        });
    }

    private void updateLabel() {
        String myFormat = "E, MMMM dd yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        show_date.setText(sdf.format(myCalendar.getTime()));

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        dateString = format.format(myCalendar.getTime());
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
            onBackPressed();
        }
        if(menuItem.getItemId() == R.id.photo){
            Toast.makeText(getApplicationContext(),"Photo added.", Toast.LENGTH_LONG).show();
        }
        return true;
    }
}
