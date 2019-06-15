package com.example.finalyearproject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PrivateDoctors extends AppCompatActivity {
    private Toolbar toolbar;

    String [] firstname, lastname, email, city, phone, doc_info, second_phone, proffession, message, days_available, hours;
    ArrayList <String> firstnameArray, lastnameArray, proffessionArray, cityArray;
    ListView listView;
    List<String> lstFound;
    MaterialSearchView materialSearchView;
    private Bundle bundle;
    String serverUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_doctors);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        bundle = getIntent().getExtras();
        serverUrl = bundle.getString("SERVER_URL");

        listView = (ListView) findViewById(R.id.lview);

        lastnameArray = new ArrayList<String>();
        proffessionArray = new ArrayList<String>();
        cityArray = new ArrayList<String>();

        materialSearchView = (MaterialSearchView) findViewById(R.id.search_view);
        materialSearchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {
                listView = (ListView) findViewById(R.id.lview);
                CustomAdapter customAdapter = new CustomAdapter();
                listView.setAdapter(customAdapter);
            }
        });
        materialSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText != null && !newText.isEmpty() && newText.length()>0){
                    /*lstFound = new ArrayList<String>();
                    for(String item: firstname){
                        if(item.contains(newText)){
                            //lstFound.add(item);

                            //Toast.makeText(getApplicationContext(), lstFound.get(0), Toast.LENGTH_SHORT).show();

                            //int index = firstnameArray.indexOf(item);
                            //cityArray.add(city[index]);
                            //lastnameArray.add(lastname[index]);
                            //proffessionArray.add(proffession[index]);
                        }
                        //Toast.makeText(getApplicationContext(), cityArray.get(0), Toast.LENGTH_SHORT).show();
                        //Toast.makeText(getApplicationContext(), lstFound.get(0), Toast.LENGTH_SHORT).show();
                    }
                    //CustomAdapter2 customAdapter2 = new CustomAdapter2();
                    //listView.setAdapter(customAdapter2);*/
                }
                else{
                    //CustomAdapter customAdapter = new CustomAdapter();
                    //listView.setAdapter(customAdapter);
                }
                return true;
            }
        });

        final Bundle main_bundle = getIntent().getExtras();

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, serverUrl+"accessDocs.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            JSONObject jsonObject = null;

                            firstname = new String[jsonArray.length()];
                            lastname =  new String[jsonArray.length()];
                            proffession =  new String[jsonArray.length()];
                            city =  new String[jsonArray.length()];
                            phone =  new String[jsonArray.length()];
                            email =  new String[jsonArray.length()];
                            doc_info =  new String[jsonArray.length()];
                            message =  new String[jsonArray.length()];
                            second_phone =  new String[jsonArray.length()];
                            days_available =  new String[jsonArray.length()];
                            hours =  new String[jsonArray.length()];

                            for(int i=0; i<jsonArray.length(); i++){
                                jsonObject = jsonArray.getJSONObject(i);

                                firstname[i] = jsonObject.getString("firstname");
                                lastname[i] = jsonObject.getString("lastname");
                                proffession[i] = jsonObject.getString("proffession");
                                city[i] = jsonObject.getString("city");
                                phone[i] = jsonObject.getString("phone");
                                email[i] = jsonObject.getString("email");
                                doc_info[i] = jsonObject.getString("doc_info");
                                message[i] = jsonObject.getString("message");
                                second_phone[i] = jsonObject.getString("second_phone");
                                days_available[i] = jsonObject.getString("days_available");
                                hours[i] = jsonObject.getString("hours");

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        CustomAdapter customAdapter = new CustomAdapter();
                        listView.setAdapter(customAdapter);

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent(getApplicationContext(), DoctorDetails.class);
                                Bundle bundle = new Bundle();

                                TextView textView = (TextView) view.findViewById(R.id.profile_name);
                                String text = textView.getText().toString();
                                String first[] = text.split(" ");
                                String fname = first[1];

                                ArrayList<String> list =new ArrayList<String>(Arrays.asList(firstname));
                                int index = list.indexOf(fname);

                                bundle.putString("firstname", firstname[index]);
                                bundle.putString("lastname", lastname[index]);
                                bundle.putString("proffession", proffession[index]);
                                bundle.putString("city", city[index]);
                                bundle.putString("phone", phone[index]);
                                bundle.putString("doc_email", email[index]);
                                bundle.putString("doc_info", doc_info[index]);
                                bundle.putString("message", message[index]);
                                bundle.putString("second_phone", second_phone[index]);
                                bundle.putString("days_available", days_available[index]);
                                bundle.putString("hours", hours[index]);

                                bundle.putString("USERNAME", main_bundle.getString("USERNAME"));
                                bundle.putString("SERVER_URL", serverUrl);

                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        });
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        });

        queue.add(stringRequest);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.private_doctors_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        materialSearchView.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){

        if(menuItem.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return true;
    }

    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return firstname.length;
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

            view = getLayoutInflater().inflate(R.layout.layout, null, true);

            ImageView imageView = (ImageView) view.findViewById(R.id.image);

            TextView docName = (TextView) view.findViewById(R.id.profile_name);
            TextView profession = (TextView) view.findViewById(R.id.proffession);
            TextView ciTy = (TextView) view.findViewById(R.id.city);

            docName.setText("Dr. "+firstname[position] + " " + lastname[position]);
            profession.setText("Specialty:\t"+proffession[position]);
            ciTy.setText("City:\t"+ city[position]);


            return view;
        }
    }

}
