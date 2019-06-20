package com.example.finalyearproject;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.service.autofill.Validator;
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
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
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
    private  Bundle bundle;
    private Toolbar toolbar;
    private String serverUrl, ip_address;
    private  int errorCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        bundle = getIntent().getExtras();
        serverUrl = bundle.getString("SERVER_URL");

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
            String signerUrl = serverUrl+"registerQuerry.php";
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
                                    builder.setMessage(response);
                                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            nameField.setText("");
                                            emailField.setText("");
                                            passwordField.setText("");
                                            confirmPasswordField.setText("");

                                            Intent intent = new Intent(ActivitySignUp.this, LoginActivity.class);
                                            Bundle b = new Bundle();
                                            b.putString("SERVER_URL", serverUrl);
                                            b.putString("activity", "ActivitySignUp");
                                            intent.putExtras(b);
                                            startActivity(intent);

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
                            if(error.toString().startsWith("com.android.volley.TimeoutError")){
                                errorCount++;
                                if(errorCount == 2){
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ActivitySignUp.this);
                                    builder.setTitle("Connection Unspecified");
                                    builder.setIcon(R.drawable.ic_cancel);
                                    builder.setMessage("Looks like you haven't specified a route (IP Address) to your server\n"+
                                            "Click on \"Specify Server\" in the menu bar.");
                                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            errorCount = 0;
                                        }
                                    });

                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.show();
                                }
                            }
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
        }
        if(menuItem.getItemId() == android.R.id.home){
            Intent intent = new Intent(ActivitySignUp.this, LoginActivity.class);
            Bundle b = new Bundle();

            b.putString("SERVER_URL", serverUrl);
            b.putString("activity", "ActivitySignUp");
            intent.putExtras(b);
            startActivity(intent);
        }
        if(menuItem.getItemId() == R.id.have_account){
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
        }
        if(menuItem.getItemId() == R.id.configure_internet){
            getServerAddress();
            //return true;
        }
        if(menuItem.getItemId() == R.id.configuration_help){

            return true;
        }
        return true;
    }

    private void getServerAddress() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivitySignUp.this);
        builder.setTitle("Specify Server");
        builder.setIcon(R.drawable.ic_edit);
        builder.setMessage("\nProvide Server's IP (v4) address (Address is of the form: xxx.xxx.xxx.xxx):\n");


        final EditText input = new EditText(ActivitySignUp.this);
        input.setHint(serverUrl.substring(7, serverUrl.lastIndexOf("/")));
        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        lp2.setMargins(20, 2, 20, 2);
        input.setLayoutParams(lp2);
        builder.setView(input);

        /*input.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                return false;
            }
        });*/

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(input.getText().toString().trim().length() > 0){
                    ip_address = input.getText().toString().trim();
                    String arr [] = ip_address.split("[.]");
                    if(arr.length == 4){
                        serverUrl = "http://"+input.getText().toString().trim()+"/";
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Wrong IP address", Toast.LENGTH_LONG).show();
                        //input.setCompoundDrawables(null, null, android.drawable.ic_info_outline, null);
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "No address provided!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(getApplicationContext(), "No IP address provided!", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNeutralButton("More Help", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ActivitySignUp.this, LoginActivity.class);
        Bundle b = new Bundle();

        b.putString("SERVER_URL", serverUrl);
        b.putString("activity", "ActivitySignUp");
        intent.putExtras(b);
        startActivity(intent);
    }
}

