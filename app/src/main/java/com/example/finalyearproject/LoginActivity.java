package com.example.finalyearproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
    private String ServerUrl, ip_address;
    private Toolbar toolbar;
    private Bundle bundle;
    private int errorCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();

        ServerUrl = "http://192.168.43.159/";

        bundle = getIntent().getExtras();
        String activity_from = bundle.getString("activity");
        if(activity_from.equals("ActivitySignUp") || activity_from.equals("Emergency") || activity_from.equals("Home") || activity_from.equals("ConfigurationHelp")){
            ServerUrl = bundle.getString("SERVER_URL");
        }

        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        loginButton = (Button)findViewById(R.id.login);

        performLogIn();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.login_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        if(menuItem.getItemId() == R.id.configure_internet){
            getServerAddress();
            //return true;
        }
        if(menuItem.getItemId() == R.id.configuration_help){
            Intent intent = new Intent(LoginActivity.this, ConfigurationHelp.class);
            Bundle b = new Bundle();
            b.putString("SERVER_URL", ServerUrl);
            b.putString("activity", "LoginActivity");
            intent.putExtras(b);
            startActivity(intent);
            return true;
        }
        return true;
    }

    private void getServerAddress() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("Specify Server");
        builder.setIcon(R.drawable.ic_edit);
        builder.setMessage("\nProvide Server's IP (v4) address (Address is of the form: xxx.xxx.xxx.xxx):\n");


        final EditText input = new EditText(LoginActivity.this);
        input.setHint(ServerUrl.substring(7, ServerUrl.lastIndexOf("/")));
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
                        ServerUrl = "http://"+input.getText().toString().trim()+"/";
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
                Intent intent = new Intent(LoginActivity.this, ConfigurationHelp.class);
                Bundle b = new Bundle();
                b.putString("SERVER_URL", ServerUrl);
                b.putString("activity", "LoginActivity");
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
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
                                    errorCount = 0;

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
                                        bundle.putString("USEREMAIL", email.getText().toString());
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
                            if(error.toString().startsWith("com.android.volley.TimeoutError")){
                                errorCount++;
                                if(errorCount == 2){
                                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
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
