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
import android.widget.ListView;
import android.widget.TableLayout;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

public class LocalHospitals extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tablayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;
    private MaterialSearchView materialSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_hospitals);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);*/

        tablayout = (TabLayout) findViewById(R.id.tablayout_id);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar_id);
        viewPager = (ViewPager) findViewById(R.id.viewpager_id);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentSurroundingHealthcentres(), "Around You");
        adapter.addFragment(new FragmentAllHealthcentres(), "All Hospitals");
        adapter.addFragment(new FragmentLocateHealthcentres(), "Locate");

        viewPager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewPager);

    }
    /*@Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        if(menuItem.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return true;
    }*/
}
