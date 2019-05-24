package com.example.finalyearproject;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.service.autofill.Validator;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.view.View.*;

public class ActivitySignUp extends AppCompatActivity {
    private EditText nameField, emailField, passwordField, confirmPasswordField;
    private CheckBox checkbox;
    private Button signupButton;
    private TextView terms, policy;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        OnClickListener toPrivacy= new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Uri someUri = Uri.parse("http://www.moyondimpamba.com");
                Intent i = new Intent(Intent.ACTION_VIEW, someUri);
                startActivity(i);
            }
        };

        terms = (TextView) findViewById(R.id.privacy_terms);
        terms.setOnClickListener(toPrivacy);

        policy = (TextView) findViewById(R.id.terms_conditions);
        policy.setOnClickListener(toPrivacy);

        nameField = (EditText) findViewById(R.id.name_field);
        emailField = (EditText) findViewById(R.id.email_field);
        passwordField = (EditText) findViewById(R.id.password_field);
        confirmPasswordField = (EditText) findViewById(R.id.confirmpassword_field);

        signupButton = (Button) findViewById(R.id.signup_button);
        signupButton.setEnabled(false);
        signupButton.setOnClickListener(new View.OnClickListener(){

            String signerUrl = "http://41.70.35.58/registerQuerry.php";  //192.168.137.146
            AlertDialog.Builder builder;

            @Override
            public void onClick(View view){

                builder = new AlertDialog.Builder(ActivitySignUp.this);

                if(validPassword(passwordField.getText().toString().trim(), confirmPasswordField.getText().toString().trim())){


                    final String name = nameField.getText().toString().trim();
                    final String email = emailField.getText().toString().trim();
                    final String password = passwordField.getText().toString().trim();

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, signerUrl,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    builder.setTitle("Server Response");

                                    builder.setMessage(response);
                                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            nameField.setText("");
                                            emailField.setText("");
                                            passwordField.setText("");
                                            confirmPasswordField.setText("");
                                            startActivity(new Intent(ActivitySignUp.this, LoginActivity.class));
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
                            Toast.makeText(ActivitySignUp.this, error.toString(), Toast.LENGTH_SHORT).show();
                            error.printStackTrace();
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {

                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                            String dateString = format.format(new Date());

                            Map<String, String> params = new HashMap<String, String>();
                            params.put("name", name);
                            params.put("password", password);
                            params.put("email", email);
                            params.put("date", dateString);
                            return params;
                        }
                    };

                    MySingleton.getInstance(ActivitySignUp.this).addTorequestque(stringRequest);

                }
                else if(!validPassword(passwordField.getText().toString(), confirmPasswordField.getText().toString())){
                    Toast.makeText(ActivitySignUp.this,"Passwords did not match!",
                            Toast.LENGTH_SHORT).show();
                    confirmPasswordField.requestFocus();

                }
            }
        });

        checkbox = (CheckBox) findViewById(R.id.checkBox);
        checkbox.setSelected(true);
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    signupButton.setEnabled(true);
                }
                else {
                    signupButton.setEnabled(false);
                }
            }
        });
    }
    public boolean validPassword(String pass, String conf_pass){
        boolean toReturn = false;
        if(pass.equals(conf_pass)){
            toReturn = true;
        }
        return toReturn;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.signup_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        if(menuItem.getItemId() == R.id.terms){
            Uri someUri = Uri.parse("http://www.google.com");
            Intent i = new Intent(Intent.ACTION_VIEW, someUri);
            startActivity(i);

            //startActivity(new Intent(ActivitySignUp.this, AppointmentBooker.class));
        }
        if(menuItem.getItemId() == android.R.id.home){
            onBackPressed();
        }
        if(menuItem.getItemId() == R.id.have_account){
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
        }
        return true;
    }


}

