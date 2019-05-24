package com.example.finalyearproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
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

public class MyAppointments extends AppCompatActivity {
    private Toolbar toolbar;
    private ListView listView;
    private String[] name;
    private String theName, app_id;
    private String [] user_name, user_age, date, appointment_id, user_address, user_message, appointment_state, appointment_time, doctor_firstname, user_phone, doctor_lastname, doc_city;
    private TextView patientName, appointments_total, appointments_confirmed, docName, address, book_title, book_date, pat_age, pat_phone, pat_address, idd;
    private TextView deleteApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_appointments);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        listView = (ListView) findViewById(R.id.list_appointment);

        Bundle bu = getIntent().getExtras();
        theName = bu.getString("name");

        patientName = (TextView) findViewById(R.id.patient_name);

        StringRequest stringRequest3 = new StringRequest(Request.Method.POST, "http://41.70.35.58/AccessUserAppointments.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(response.equals("No Appointments for such user")){
                            patientName.setText(theName);
                            String name = "No Appointments for "+theName.toUpperCase();
                            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                        }
                        else{
                            try {
                                JSONArray jsonArray = new JSONArray(response);

                                JSONObject jsonObject = null;

                                //appointment_time = new String[jsonArray.length()];
                                //appointment_state =  new String[jsonArray.length()];

                                user_name = new String[jsonArray.length()];
                                user_age =  new String[jsonArray.length()];
                                date =  new String[jsonArray.length()];
                                appointment_id =new String[jsonArray.length()];
                                user_address =  new String[jsonArray.length()];
                                user_message =  new String[jsonArray.length()];
                                doctor_firstname =  new String[jsonArray.length()];
                                user_phone =  new String[jsonArray.length()];
                                doctor_lastname =  new String[jsonArray.length()];
                                doc_city =  new String[jsonArray.length()];

                                for(int i=0; i<jsonArray.length(); i++){
                                    jsonObject = jsonArray.getJSONObject(i);

                                    user_name[i] = jsonObject.getString("user_name");
                                    user_age[i] = jsonObject.getString("user_age");
                                    date[i] = jsonObject.getString("date");
                                    appointment_id[i] = jsonObject.getString("appointment_id");
                                    user_address[i] = jsonObject.getString("user_address");
                                    user_message[i] = jsonObject.getString("user_message");

                                    //appointment_time[i] = jsonObject.getString("appointment_time");
                                    // appointment_state[i] =  jsonObject.getString("appointment_state");

                                    doctor_firstname[i] = jsonObject.getString("doctor_firstname");
                                    user_phone[i] = jsonObject.getString("user_phone");
                                    doctor_lastname[i] = jsonObject.getString("doctor_lastname");
                                    doc_city[i] = jsonObject.getString("doc_city");

                                }
                                //Toast.makeText(getApplicationContext(), doctor_firstname[1], Toast.LENGTH_SHORT).show();

                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }

                            CustomAdapter customAdapter = new CustomAdapter();
                            listView.setAdapter(customAdapter);

                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                    final String i_d = idd.getText().toString().substring(idd.getText().toString().lastIndexOf(" "));

                                    deleteApp = findViewById(R.id.delete_appointment);
                                    deleteApp.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Toast.makeText(getApplicationContext(), "Appointment #: "+i_d, Toast.LENGTH_LONG).show();

                                            AlertDialog.Builder builder = new AlertDialog.Builder(MyAppointments.this);
                                            builder.setTitle("Confirm Delete");
                                            builder.setMessage("Appointment id no. "+i_d+" will be deleted from the system.");
                                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    StringRequest stringRequest4 = new StringRequest(Request.Method.POST, "http://41.70.35.58/DeleteAppointment.php",
                                                            new Response.Listener<String>() {
                                                                @Override
                                                                public void onResponse(final String response) {
                                                                    AlertDialog.Builder builder1 = new AlertDialog.Builder(MyAppointments.this);
                                                                    builder1.setTitle("Server Response");
                                                                    builder1.setMessage(response);

                                                                    builder1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(DialogInterface dialog, int which) {
                                                                            Toast.makeText(getApplicationContext(),"Delete Successfull", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    });
                                                                    AlertDialog alertDialog1 = builder1.create();
                                                                    alertDialog1.show();
                                                                }
                                                            }
                                                            , new Response.ErrorListener(){
                                                        @Override
                                                        public void onErrorResponse(VolleyError error){
                                                            Toast.makeText(getApplicationContext(), "Connection Error"+error.getMessage(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    }){
                                                        @Override
                                                        protected Map<String, String> getParams() throws AuthFailureError {

                                                            Map<String, String> params = new HashMap<String, String>();
                                                            params.put("appointment_id", i_d);

                                                            return params;
                                                        }
                                                    };

                                                    MySingleton.getInstance(MyAppointments.this).addTorequestque(stringRequest4);
                                                }
                                            });
                                            AlertDialog alertDialog = builder.create();
                                            alertDialog.show();
                                        }
                                    });

                                }
                            });
                        }


                    }
                }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                params.put("user_name", theName);
                return params;
            }
        };

        MySingleton.getInstance(getApplicationContext()).addTorequestque(stringRequest3);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){

        if(menuItem.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return true;
    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return user_name.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {

            view = getLayoutInflater().inflate(R.layout.list_layout, null, true);

            patientName = (TextView) findViewById(R.id.patient_name);
            appointments_total = (TextView) findViewById(R.id.all_appointments);
            appointments_confirmed = (TextView) findViewById(R.id.confirmed);
            docName = (TextView) view.findViewById(R.id.booked_doc);
            address = (TextView) view.findViewById(R.id.booked_doc_address);
            book_title = (TextView) view.findViewById(R.id.booking_title);
            book_date = (TextView) view.findViewById(R.id.booking_date);
            pat_age = (TextView) view.findViewById(R.id.age);
            pat_phone = (TextView) view.findViewById(R.id.patientphone);
            pat_address = (TextView) view.findViewById(R.id.patientaddress);
            idd = (TextView) view.findViewById(R.id.app_id);

            //TextView appointments_time = (TextView) findViewById(R.id.booked_time);
            //TextView app_state = (TextView) findViewById(R.id.app_state);
            //app_state.setText(appointment_state[position]);
            //appointments_time.setText(appointment_time[position]);

            patientName.setText(theName);
            appointments_total.setText("All Appointments ("+this.getCount()+")");

            docName.setText("Dr. "+doctor_firstname[position] + " " + doctor_lastname[position]);

            app_id = appointment_id[position];

            idd.setText("Appointment Id:\t"+ appointment_id[position]);

            String title = user_message[position];
            if(title.length() > 12){
                title = title.substring(0, 12);
                book_title.setText(title+"...");
            }else {
                book_title.setText(user_message[position]);
            }

            book_date.setText(date[position]);
            address.setText(doc_city[position]);
            pat_age.setText("Age: "+user_age[position]);
            pat_phone.setText(user_phone[position]);
            pat_address.setText(user_address[position]);

            return view;
        }
    }
}
