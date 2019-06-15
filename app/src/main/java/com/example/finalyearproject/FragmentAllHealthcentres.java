package com.example.finalyearproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class FragmentAllHealthcentres extends Fragment {
    View view;
    private ListView listViewOfAllCentres;
    private String [] hospitalName, email, city, phone, quick_info, message, days_open, hours;

    public FragmentAllHealthcentres() {

    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        /*RequestQueue queue = Volley.newRequestQueue(getContext());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://192.168.137.1/accessHealthCentres.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            JSONObject jsonObject = null;

                            hospitalName = new String[jsonArray.length()];
                            city =  new String[jsonArray.length()];
                            phone =  new String[jsonArray.length()];
                            email =  new String[jsonArray.length()];
                            quick_info =  new String[jsonArray.length()];
                            days_open =  new String[jsonArray.length()];
                            hours =  new String[jsonArray.length()];

                            for(int i=0; i<jsonArray.length(); i++){
                                jsonObject = jsonArray.getJSONObject(i);

                                hospitalName[i] = jsonObject.getString("hospital_name");
                                city[i] = jsonObject.getString("city");
                                phone[i] = jsonObject.getString("phone");
                                email[i] = jsonObject.getString("email");
                                quick_info[i] = jsonObject.getString("quick_description");
                                days_open[i] = jsonObject.getString("days_open");
                                hours[i] = jsonObject.getString("hours_open");

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        FragmentAllHealthcentres.CustomAdapter customAdapter = new FragmentAllHealthcentres.CustomAdapter();
                        listViewOfAllCentres.setAdapter(customAdapter);

                        listViewOfAllCentres.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent(getContext(), HealthCentreDetails.class);
                                Bundle bundle = new Bundle();

                                bundle.putString("hospitalname", hospitalName[position]);
                                bundle.putString("city", city[position]);
                                bundle.putString("phone", phone[position]);
                                bundle.putString("hosp_email", email[position]);
                                bundle.putString("quick_info", quick_info[position]);
                                bundle.putString("days_open", days_open[position]);
                                bundle.putString("hours", hours[position]);

                                //bundle.putString("USERNAME", main_bundle.getString("USERNAME"));
                                //bundle.putString("SERVER_URL", serverUrl);

                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        });
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        });

        queue.add(stringRequest);*/

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.all_healthcentres_fragment, container, false);

        listViewOfAllCentres = (ListView)view.findViewById(R.id.all_health_centres_view);


        RequestQueue queue = Volley.newRequestQueue(getContext());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://41.70.44.250/accessHealthCentres.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            JSONObject jsonObject = null;

                            hospitalName = new String[jsonArray.length()];
                            city =  new String[jsonArray.length()];
                            phone =  new String[jsonArray.length()];
                            email =  new String[jsonArray.length()];
                            quick_info =  new String[jsonArray.length()];
                            days_open =  new String[jsonArray.length()];
                            hours =  new String[jsonArray.length()];

                            for(int i=0; i<jsonArray.length(); i++){
                                jsonObject = jsonArray.getJSONObject(i);

                                hospitalName[i] = jsonObject.getString("hospital_name");
                                city[i] = jsonObject.getString("city");
                                phone[i] = jsonObject.getString("phone");
                                email[i] = jsonObject.getString("email");
                                quick_info[i] = jsonObject.getString("quick_description");
                                days_open[i] = jsonObject.getString("days_open");
                                hours[i] = jsonObject.getString("hours_open");

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        FragmentAllHealthcentres.CustomAdapter customAdapter = new FragmentAllHealthcentres.CustomAdapter();
                        listViewOfAllCentres.setAdapter(customAdapter);

                        listViewOfAllCentres.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent(getContext(), HealthCentreDetails.class);
                                Bundle bundle = new Bundle();

                                bundle.putString("hospitalname", hospitalName[position]);
                                bundle.putString("city", city[position]);
                                bundle.putString("phone", phone[position]);
                                bundle.putString("hosp_email", email[position]);
                                bundle.putString("quick_info", quick_info[position]);
                                bundle.putString("days_open", days_open[position]);
                                bundle.putString("hours", hours[position]);

                                //bundle.putString("USERNAME", main_bundle.getString("USERNAME"));
                                //bundle.putString("SERVER_URL", serverUrl);

                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        });
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        });

        queue.add(stringRequest);


        return view;
    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return hospitalName.length;
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

            nameOfHospital.setText("Name:\t"+hospitalName[position]);
            daysOpen.setText("Days Open:\t"+days_open[position]);
            cityOfHospital.setText("City:\t"+ city[position]);


            return view;
        }
    }

}
