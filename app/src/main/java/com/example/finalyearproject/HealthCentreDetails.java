package com.example.finalyearproject;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HealthCentreDetails extends AppCompatActivity {

    Button bookAppointments;
    TextView nameLabel, emailLabel, phoneLabel, cityLabel, daysLabel, hoursLabel, infoLabel;
    LinearLayout posts;
    Bundle bundle;
    private String serverUrl;
    String[] name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_centre_details);

        bookAppointments = (Button) findViewById(R.id.book_appointment_button);
        bookAppointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "Appointment Booked", Toast.LENGTH_SHORT).show();
                appointmentsBooker();
            }
        });

        nameLabel = (TextView) findViewById(R.id.hospital_name_label);
        emailLabel = (TextView) findViewById(R.id.hosp_email_label);
        phoneLabel = (TextView) findViewById(R.id.hosp_phone_label);
        cityLabel = (TextView) findViewById(R.id.hosp_city_label);
        daysLabel = (TextView) findViewById(R.id.days_open_label);
        hoursLabel = (TextView) findViewById(R.id.hours_open_label);
        infoLabel = (TextView) findViewById(R.id.hosp_info_label);

        bundle = getIntent().getExtras();
        serverUrl = bundle.getString("SERVER_URL");

        nameLabel.setText(bundle.getString("hospitalname"));
        emailLabel.setText(bundle.getString("hosp_email"));
        emailLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_url = emailLabel.getText().toString().trim();
                if(email_url.length() > 0){
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", email_url, null));
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                    emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
                    startActivity(Intent.createChooser(emailIntent, "Send email..."));
                }

            }
        });
        phoneLabel.setText(bundle.getString("phone"));
        phoneLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone_num = phoneLabel.getText().toString().trim();
                if(phone_num.length() > 0){
                    Uri myUri = Uri.parse("tel:"+phone_num);
                    startActivity(new Intent(Intent.ACTION_DIAL, myUri));
                }

            }
        });
        //doc_specialty.setText(bundle.getString("proffession"));
        cityLabel.setText(bundle.getString("city"));
        daysLabel.setText(bundle.getString("days_open"));
        hoursLabel.setText(bundle.getString("hours"));

        posts= (LinearLayout) findViewById(R.id.hosp_info_layout);
        posts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), bundle.getString("quick_info"), Toast.LENGTH_LONG).show();
            }
        });

        infoLabel.setText( bundle.getString("quick_info"));

    }

    private void appointmentsBooker() {

        Intent intent = new Intent(getApplicationContext(), AppointmentBooker.class);
        Bundle outgoing_bundle = getIntent().getExtras();

        outgoing_bundle.putString("hospital_name", nameLabel.getText().toString());
        outgoing_bundle.putString("activity", "HealthCentre");
        outgoing_bundle.putString("city", cityLabel.getText().toString());
        outgoing_bundle.putString("days_available", daysLabel.getText().toString());
        outgoing_bundle.putString("hours", hoursLabel.getText().toString());

        outgoing_bundle.putString("USERNAME", bundle.getString("USERNAME"));
        outgoing_bundle.putString("SERVER_URL", serverUrl);

        intent.putExtras(outgoing_bundle);
        startActivity(intent);

    }
}
