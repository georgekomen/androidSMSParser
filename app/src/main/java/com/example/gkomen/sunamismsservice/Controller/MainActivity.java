package com.example.gkomen.sunamismsservice.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.gkomen.sunamismsservice.APIUtils.constants;
import com.example.gkomen.sunamismsservice.R;
import com.example.gkomen.sunamismsservice.Service.SMSService;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        constants con = new constants(this);

        //start mpesa service
        Intent intent = new Intent(getApplicationContext(),SMSService.class);
        startService(intent);

//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//                //start mpesa service
//                Intent intent = new Intent(getApplicationContext(),SMSService.class);
//                startService(intent);
//            }
//        });
    }
}
