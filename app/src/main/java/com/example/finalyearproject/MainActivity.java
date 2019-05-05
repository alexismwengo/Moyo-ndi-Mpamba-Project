package com.example.finalyearproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Thread thread=new Thread(){
            public void run(){
                try{
                    sleep(2900);
                    startNewActivity();

                }catch(InterruptedException e){}
            }
        };
        thread.start();
    }
    public void startNewActivity(){
        startActivity(new Intent(this, LoginActivity.class));
    }
}
