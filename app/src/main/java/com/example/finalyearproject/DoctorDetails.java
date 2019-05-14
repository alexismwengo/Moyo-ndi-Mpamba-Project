package com.example.finalyearproject;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DoctorDetails extends AppCompatActivity {

    Button bookAppointments;
    TextView doc_name, doc_email, doc_phone, doc_specialty, doc_city, doc_days, doc_hours;
    LinearLayout email_container, phone_container, location_container;
    LinearLayout posts;
    Bundle bundle;
    String[] name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);

        bookAppointments = (Button) findViewById(R.id.appointment_button);
        bookAppointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "Appointment Booked", Toast.LENGTH_SHORT).show();
                appointmentsBooker();
            }
        });

        doc_name = (TextView) findViewById(R.id.doc_name);
        doc_email = (TextView) findViewById(R.id.doc_email);
        doc_phone = (TextView) findViewById(R.id.doc_phone);
        doc_specialty = (TextView) findViewById(R.id.doc_specialty);
        doc_city = (TextView) findViewById(R.id.doc_city);
        doc_days = (TextView) findViewById(R.id.doc_days);
        doc_hours = (TextView) findViewById(R.id.doc_hours);

        bundle = getIntent().getExtras();

        doc_name.setText("Dr. "+bundle.getString("firstname")+" "+bundle.getString("lastname"));
        doc_email.setText(bundle.getString("doc_email"));
        doc_phone.setText(bundle.getString("phone"));
        doc_specialty.setText(bundle.getString("proffession"));
        doc_city.setText(bundle.getString("city"));
        doc_days.setText(bundle.getString("days_available"));
        doc_hours.setText(bundle.getString("hours"));

        posts= (LinearLayout) findViewById(R.id.posts);
        posts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), bundle.getString("doc_info"), Toast.LENGTH_LONG).show();
            }
        });


        email_container = (LinearLayout) findViewById(R.id.email_container);
        email_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_url = doc_email.getText().toString().trim();
                if(email_url.length() > 0){
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", email_url, null));
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                    emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
                    startActivity(Intent.createChooser(emailIntent, "Send email..."));
                }

            }
        });

        phone_container = (LinearLayout) findViewById(R.id.phone_container);
        phone_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone_num = doc_phone.getText().toString().trim();
                if(phone_num.length() > 0){
                    Uri myUri = Uri.parse("tel:"+phone_num);
                    startActivity(new Intent(Intent.ACTION_DIAL, myUri));
                }

            }
        });

        location_container = (LinearLayout) findViewById(R.id.location_container);
        location_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String location = doc_city.getText().toString().trim();

            }
        });

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
                            e.printStackTrace();
                        }
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

    private void appointmentsBooker() {

        Intent intent = new Intent(getApplicationContext(), AppointmentBooker.class);
        Bundle outgoing_bundle;

        outgoing_bundle = getIntent().getExtras();

        outgoing_bundle.putString("username", doc_name.getText().toString());
        outgoing_bundle.putString("proffession", doc_specialty.getText().toString());
        outgoing_bundle.putString("city", doc_city.getText().toString());
        outgoing_bundle.putString("phone", doc_phone.getText().toString());
        outgoing_bundle.putString("email", doc_email.getText().toString());
        outgoing_bundle.putString("days_available", doc_days.getText().toString());
        outgoing_bundle.putString("hours", doc_hours.getText().toString());

        outgoing_bundle.putString("user_name", name[0]);

        intent.putExtras(outgoing_bundle);
        startActivity(intent);

    }
}