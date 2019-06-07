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
import android.os.Handler;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private EditText email, password;
    private Button loginButton;
    private TextView sign_up, emergency;
    private String ServerUrl;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();

        ServerUrl = "http://41.70.47.134/";

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

                    final Intent intent = new Intent(LoginActivity.this, Home.class);
                    final Bundle bundle = new Bundle();
                    bundle.putString("SERVER_URL", ServerUrl);

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerUrl+"testLogin.php",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    if(response.equals("Login Unsuccessful")){
                                        Toast.makeText(LoginActivity.this, "Incorrect email or password.", Toast.LENGTH_LONG).show();
                                        password.setText("");
                                        password.requestFocus();
                                    }
                                    else {

                                        String r = response.substring(response.indexOf("["));
                                        String [] name = null;
                                        try {
                                            JSONArray jsonArray = new JSONArray(r);

                                            JSONObject jsonObject = null;

                                            name = new String[jsonArray.length()];

                                            for(int i=0; i<jsonArray.length(); i++){
                                                jsonObject = jsonArray.getJSONObject(i);

                                                name[i] = jsonObject.getString("name");
                                            }

                                        } catch (JSONException e) {
                                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                            e.printStackTrace();
                                        }

                                        bundle.putString("USERNAME", name[0]);
                                        intent.putExtras(bundle);
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

                            //following lines will be removed
                            intent.putExtras(bundle);
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
                Intent intent = new Intent(LoginActivity.this, ActivitySignUp.class);
                Bundle bund = new Bundle();
                bund.putString("SERVER_URL", ServerUrl);
                intent.putExtras(bund);
                startActivity(intent);
            }
        });

        emergency = (TextView)findViewById(R.id.emergency);
        emergency.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(LoginActivity.this, Emergency.class);

                Bundle b = new Bundle();
                b.putString("activity", "login_activity");
                b.putString("SERVER_URL", ServerUrl);
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

    boolean twice = false;
    @Override
    public void onBackPressed() {
        if(twice == true){
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            System.exit(0);
        }
        Toast.makeText(LoginActivity.this, "Press Back Again to Exit...", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                twice = false;
            }
        }, 3000);
        twice = true;
    }
}
