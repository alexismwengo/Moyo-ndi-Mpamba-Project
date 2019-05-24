package com.example.finalyearproject;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.os.Environment.getExternalStoragePublicDirectory;

public class Emergency extends AppCompatActivity {
    private Toolbar toolbar;
    private ImageView photoAttached;
    private ImageButton photoButton;
    private RadioGroup genderButtons;
    private RadioButton male, female;
    private EditText textSubmitted;
    private CheckBox ambulanceOperator;
    private Button emergencyTime, emergencyLocation;
    private Spinner available_centres, patient_gender;
    private Bundle bun;
    private String activity_from;
    String pathToFile;
    String genderOfPatient = "", health_centre_selected = "", ambulance_operator_id = "", image_submitted = "";

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
        activity_from = bun.getString("activity");

        SimpleDateFormat f=new SimpleDateFormat("MMMM dd yyyy  hh:mm:ss a");
        String thDate=new String(f.format(new Date()));

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
                    builder.setIcon(R.drawable.ic_report2);
                    builder.setMessage("\nPlease provide your Employee Id:\n\neditText here...");

                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ambulance_operator_id = "";
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

        emergencyTime = (Button) findViewById(R.id.emergency_time);
        emergencyTime.setText(thDate);
        emergencyLocation = (Button) findViewById(R.id.emergency_location);

        patient_gender = (Spinner) findViewById(R.id.patient_gender);
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
            if(activity_from.equals("login_activity")){
                onBackPressed();
            }
            else{
                onBackPressed();
            }
        }
        if(menuItem.getItemId() == R.id.send_emergency_report){
            //Toast.makeText(getApplicationContext(), "Sending emergency...", Toast.LENGTH_LONG).show();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://41.70.35.58/PostEmergency.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            AlertDialog.Builder builder = new AlertDialog.Builder(Emergency.this);
                            builder.setTitle("Server Response");
                            builder.setIcon(R.drawable.ic_info);
                            builder.setMessage(response);

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

                    SimpleDateFormat f=new SimpleDateFormat("MMMM dd yyyy  hh:mm:ss a");
                    String thDate=new String(f.format(new Date()));

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("genderofpatient", genderOfPatient);
                    params.put("health_centre", health_centre_selected);
                    params.put("date_submitted", thDate);
                    params.put("ambulance_operator_id", ambulance_operator_id);
                    params.put("image_submitted", image_submitted);
                    params.put("comments_submitted", textSubmitted.getText().toString()); //****
                    return params;
                }
            };

            MySingleton.getInstance(Emergency.this).addTorequestque(stringRequest);

        }
        return true;
    }
}
