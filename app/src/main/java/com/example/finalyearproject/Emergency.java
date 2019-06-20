package com.example.finalyearproject;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GravityCompat;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
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

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.os.Environment.getExternalStoragePublicDirectory;

public class Emergency extends AppCompatActivity {
    private Toolbar toolbar;
    private ImageView photoAttached;
    private ImageButton photoButton;
    private RadioGroup genderButtons;
    private RadioButton male, female;
    private BottomNavigationView bottomNavigationView;
    private EditText textSubmitted;
    private CheckBox ambulanceOperator;
    private Button emergencySender;
    private Spinner available_centres, patient_gender;
    private Bundle bun;
    private String activity_from, pathToFile, genderOfPatient = "", health_centre_selected = "", ambulance_operator_id = "", image_submitted = "";
    private Location gps_loc = null, network_loc = null, final_loc = null;
    private Double latitude = 0.0, longitude = 0.0;
    private String [] name, hospital_name;
    String user_name;
    String serverUrl, address = "", city = "", state = "", country = "", postal_code = "", known_name = "", premises = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);

        if(Build.VERSION.SDK_INT >= 23){
            requestPermissions(new String[] {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
        }

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        bun = getIntent().getExtras();
        serverUrl = bun.getString("SERVER_URL");
        activity_from = bun.getString("activity");

        photoAttached = (ImageView) findViewById(R.id.attached_photo);
        photoButton = (ImageButton) findViewById(R.id.camera);
        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capturePicture();
            }
        });

        textSubmitted = (EditText) findViewById(R.id.additional_text);

        ambulanceOperator = (CheckBox) findViewById(R.id.ambulance_op_check);
        if(activity_from.equals("home_activity")){
           ambulanceOperator.setClickable(false);
           ambulanceOperator.setTextColor(Color.GRAY);
        }
        ambulanceOperator.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    AlertDialog.Builder builder = new AlertDialog.Builder(Emergency.this);
                    builder.setTitle("Aunthentication");
                    builder.setIcon(R.drawable.ic_info);
                    builder.setMessage("Please provide your Employee Id:\n");

                    final EditText input = new EditText(Emergency.this);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT);
                    lp.setMargins(20, 2, 20, 2);
                    input.setLayoutParams(lp);
                    builder.setView(input);

                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(input.getText().toString().length()>0){
                                ambulance_operator_id = input.getText().toString();
                            }
                            else {
                                ambulance_operator_id = "";
                            }
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ambulance_operator_id = "";
                            ambulanceOperator.setChecked(false);
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                else {
                    ambulance_operator_id = "";
                }
            }
        });

        emergencySender = (Button) findViewById(R.id.emergency_sender);
        emergencySender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(activity_from.equals("login_activity")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(Emergency.this);
                    //builder.setIcon(R.drawable.ic_info);
                    builder.setTitle("Sender's Name (Optional)");
                    builder.setMessage("Provide name below");

                    final EditText input = new EditText(Emergency.this);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT);
                    lp.setMargins(20, 2, 20, 2);
                    input.setLayoutParams(lp);
                    builder.setView(input);

                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(input.getText().toString().length()>0){
                                emergencySender.setText(input.getText().toString());
                            }
                            else {

                            }
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            emergencySender.setText("SENDER: (ANONYMOUS)");
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                if(activity_from.equals("home_activity")){

                    if(bun.containsKey("USERNAME")){
                        user_name = bun.getString("USERNAME");
                    }else {
                        user_name = "User Name";
                    }
                    //user_name = "User Name";
                    //user_name = bun.getString("USERNAME");

                    AlertDialog.Builder builder = new AlertDialog.Builder(Emergency.this);
                    builder.setTitle("Sender's Name (Optional)");
                    builder.setMessage("\n\t"+user_name);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            emergencySender.setText(user_name); //input from user
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            emergencySender.setText("SENDER: (ANONYMOUS)");
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        });

        patient_gender = (Spinner) findViewById(R.id.patient_gender);

        /*RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, serverUrl+"accessHealthCentres.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = null;
                            hospital_name = new String[jsonArray.length()];
                            for(int i=0; i<jsonArray.length(); i++){
                                jsonObject = jsonArray.getJSONObject(i);
                                hospital_name[i] = jsonObject.getString("hospital_name");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }); queue.add(stringRequest);*/

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.patient_gender_array,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        patient_gender.setAdapter(adapter);
        patient_gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (parent.getItemAtPosition(position).toString()){
                    case "Male":
                        genderOfPatient = "Male";
                        break;
                    case "Female":
                        genderOfPatient = "Female";
                        break;
                    default:
                        genderOfPatient = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                genderOfPatient = null;
            }
        });


        available_centres = (Spinner) findViewById(R.id.available_centres);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.hospitals_array,
                android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        available_centres.setAdapter(adapter1);
        available_centres.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                health_centre_selected = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                health_centre_selected = null;
            }
        });


        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED){

            //Toast.makeText(getApplicationContext(), "Not Granted", Toast.LENGTH_SHORT).show();
        }
        else {
            //Toast.makeText(getApplicationContext(), "Granted", Toast.LENGTH_SHORT).show();
        }
        try {
            gps_loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            network_loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }catch(Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        if(gps_loc != null){
            final_loc = gps_loc;
            latitude = final_loc.getLatitude();
            longitude = final_loc.getLongitude();
        }
        else if(network_loc != null){
            final_loc = network_loc;
            latitude = final_loc.getLatitude();
            longitude = final_loc.getLongitude();
        }
        else{
            latitude = 0.0;
            longitude = 0.0;
        }

        try {
            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if(addresses != null && addresses.size()>0){
                address = addresses.get(0).getAddressLine(0);
                city = addresses.get(0).getLocality();
                state = addresses.get(0).getAdminArea();
                country = addresses.get(0).getCountryName();
                postal_code = addresses.get(0).getPostalCode();
                known_name = addresses.get(0).getFeatureName();
                premises = addresses.get(0).getPremises();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }


        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav_emergency);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if(menuItem.getItemId() == R.id.emergency_timee){
                    AlertDialog.Builder builder = new AlertDialog.Builder(Emergency.this);
                    builder.setTitle("Time of Emergency");

                    SimpleDateFormat date = new SimpleDateFormat("E, MMMM dd yyyy");
                    SimpleDateFormat time = new SimpleDateFormat("hh:mm:ss a");
                    builder.setMessage("\nDate:\t" + date.format(new Date())+"\nTime:\t"+time.format(new Date()));

                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                else if(menuItem.getItemId() == R.id.emergency_placee){
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(Emergency.this);
                    builder1.setTitle("Place of Emergency");
                    builder1.setMessage("\nAddress: "+address+", "+state+"\n"+
                            "Place: "+known_name+", "+city+", "+country+"\n"+
                            "Premises: "+ premises);
                    builder1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog alertDialog1 = builder1.create();
                    alertDialog1.show();
                }
                return true;
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if(requestCode == 1){
                Bitmap bitmap = BitmapFactory.decodeFile(pathToFile);
                photoAttached.setImageBitmap(bitmap);
            }
        }
    }

    private void capturePicture() {
        Intent takePic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePic.resolveActivity(getPackageManager()) != null){
            File photo_file  = createPhotoFile();

            if(photo_file != null){
                pathToFile = photo_file.getAbsolutePath();
                Uri photoUri = FileProvider.getUriForFile(Emergency.this,"com.example.finalyearproject", photo_file);
                takePic.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(takePic, 1);
            }
        }
    }

    private File createPhotoFile() {
        String photoName = "MoyoNdiMPamba_"+new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File file_directory = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(photoName, ".jpg", file_directory);
        }
        catch (IOException e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return image;
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
            Intent intent = new Intent(Emergency.this, LoginActivity.class);
            Bundle b = new Bundle();
            b.putString("SERVER_URL", serverUrl);
            b.putString("activity", "Emergency");
            intent.putExtras(b);
            startActivity(intent);
        }

        if(menuItem.getItemId() == R.id.send_emergency_report){
            //Toast.makeText(getApplicationContext(), "Sending emergency...", Toast.LENGTH_LONG).show();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, serverUrl+"PostEmergency.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            AlertDialog.Builder builder = new AlertDialog.Builder(Emergency.this);
                            builder.setTitle("Server Response");
                            builder.setIcon(R.drawable.ic_info);
                            builder.setMessage("\n\t"+response);

                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    textSubmitted.setText("");
                                    ambulanceOperator.setChecked(false);
                                    //photoAttached.setImageBitmap(R.drawable.ic_image);
                                }
                            });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        }
                    }
                    , new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error){
                    Toast.makeText(Emergency.this, error.toString(), Toast.LENGTH_SHORT).show();
                    error.printStackTrace();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    SimpleDateFormat f=new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat time = new SimpleDateFormat("hh:mm:ss a");
                    String thDate=new String(f.format(new Date()));
                    String timee=new String(time.format(new Date()));

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("genderofpatient", genderOfPatient);
                    params.put("health_centre", health_centre_selected);
                    params.put("date_submitted", thDate);
                    params.put("time_submitted", timee);
                    params.put("ambulance_operator_id", ambulance_operator_id);
                    params.put("image_submitted", image_submitted);
                    params.put("emergency_sender", emergencySender.getText().toString());
                    params.put("comments_submitted", textSubmitted.getText().toString());

                    params.put("emerg_address", address);
                    params.put("emerg_known_name", known_name);
                    params.put("emerg_city", city);//****
                    return params;
                }
            };

            MySingleton.getInstance(Emergency.this).addTorequestque(stringRequest);

        }
        return true;
    }
}
