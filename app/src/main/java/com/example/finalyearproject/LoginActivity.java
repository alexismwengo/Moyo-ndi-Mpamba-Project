package com.example.finalyearproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private EditText email, password;
    private Button loginButton;
    private TextView sign_up, emergency;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();

        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        loginButton = (Button)findViewById(R.id.login);

        performLogIn();
    }
    public void performLogIn(){

        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                if(fieldsNotEmpty(email.getText().toString(), password.getText().toString())){

                    final Bundle bundle = new Bundle();
                    bundle.putString("email", email.getText().toString());
                    bundle.putString("password", password.getText().toString());

                    final Intent intent = new Intent(LoginActivity.this, Home.class);

                    intent.putExtras(bundle);

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://41.70.35.58/loginQuerry.php",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    if(response.equals("Login Unsuccessful")){
                                        Toast.makeText(LoginActivity.this, "Incorrect email or password.", Toast.LENGTH_LONG).show();
                                        password.setText("");
                                        password.requestFocus();
                                    }
                                    else {

                                        startActivity(intent);

                                        email.setText("");
                                        password.setText("");

                                    }
                                }
                            }
                            , new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error){
                            Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                            error.printStackTrace();
                            startActivity(intent);
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {

                            Map<String, String> params = new HashMap<String, String>();
                            params.put("email", email.getText().toString().trim());
                            params.put("password", password.getText().toString().trim());
                            return params;
                        }
                    };

                    MySingleton.getInstance(LoginActivity.this).addTorequestque(stringRequest);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Provide both email and password", Toast.LENGTH_SHORT).show();
                }

            }
        });

        sign_up = (TextView)findViewById(R.id.sign_up);
        sign_up.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(LoginActivity.this, ActivitySignUp.class));
            }
        });

        emergency = (TextView)findViewById(R.id.emergency);
        emergency.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(LoginActivity.this, Emergency.class);

                Bundle b = new Bundle();
                b.putString("activity", "login_activity");
                intent.putExtras(b);

                startActivity(intent);
            }
        });
    }
    public boolean fieldsNotEmpty(String one, String two){
        Boolean toReturn = false;
        if (one.length() != 0 && two.length() != 0){
            toReturn = true;
        }
        return toReturn;
    }

}
