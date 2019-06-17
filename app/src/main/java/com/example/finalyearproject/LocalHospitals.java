package com.example.finalyearproject;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
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

public class LocalHospitals extends AppCompatActivity {
    private Toolbar toolbar;
    private MaterialSearchView materialSearchView;
    private Bundle bundle;
    private  String serverUrl;
    private ListView listView;
    private String[]hospital_name, city, phone, email, quick_description, days_open, hours_open;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_hospitals);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        bundle = getIntent().getExtras();
        serverUrl = bundle.getString("SERVER_URL");

        listView = (ListView) findViewById(R.id.listofhosp);

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, serverUrl+"accessHealthCentres.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            JSONObject jsonObject = null;

                            hospital_name = new String[jsonArray.length()];
                            city =  new String[jsonArray.length()];
                            phone =  new String[jsonArray.length()];
                            email =  new String[jsonArray.length()];
                            quick_description =  new String[jsonArray.length()];
                            days_open =  new String[jsonArray.length()];
                            hours_open =  new String[jsonArray.length()];

                            for(int i=0; i<jsonArray.length(); i++){
                                jsonObject = jsonArray.getJSONObject(i);

                                hospital_name[i] = jsonObject.getString("hospital_name");
                                city[i] = jsonObject.getString("city");
                                phone[i] = jsonObject.getString("phone");
                                email[i] = jsonObject.getString("email");
                                quick_description[i] = jsonObject.getString("quick_description");
                                days_open[i] = jsonObject.getString("days_open");
                                hours_open[i] = jsonObject.getString("hours_open");

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        CustomAdapter customAdapter = new CustomAdapter();
                        listView.setAdapter(customAdapter);

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent(getApplicationContext(), HealthCentreDetails.class);
                                Bundle bundle1 = new Bundle();

                                bundle1.putString("hospitalname", hospital_name[position]);
                                bundle1.putString("city", city[position]);
                                bundle1.putString("phone", phone[position]);
                                bundle1.putString("hosp_email", email[position]);
                                bundle1.putString("quick_info", quick_description[position]);
                                bundle1.putString("days_open", days_open[position]);
                                bundle1.putString("hours", hours_open[position]);

                                bundle1.putString("USERNAME", bundle.getString("USERNAME"));
                                bundle1.putString("SERVER_URL", serverUrl);

                                intent.putExtras(bundle1);
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

        materialSearchView = (MaterialSearchView) findViewById(R.id.search_vieww);
        materialSearchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }
            @Override
            public void onSearchViewClosed() {

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

                }
                else{

                }
                return true;
            }
        });

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

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return hospital_name.length;
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
        public View getView(int position, View view2, ViewGroup parent) {

            view2 = getLayoutInflater().inflate(R.layout.list_layout_allhealthcentres, null, true);

            TextView nameOfHospital = (TextView) view2.findViewById(R.id.hospital_name);
            TextView cityOfHospital = (TextView) view2.findViewById(R.id.hospital_city);
            TextView daysOpen = (TextView) view2.findViewById(R.id.hospital_days_open);

            nameOfHospital.setText(hospital_name[position]);
            daysOpen.setText("Days Open: "+days_open[position]);
            cityOfHospital.setText("City: "+ city[position]);


            return view2;
        }
    }
}
