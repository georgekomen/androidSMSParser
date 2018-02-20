package com.example.gkomen.sunamismsservice.Controller;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.gkomen.sunamismsservice.APIInterface.SMSServiceInterface;
import com.example.gkomen.sunamismsservice.APIUtils.APIUtils;
import com.example.gkomen.sunamismsservice.Model.Message;
import com.example.gkomen.sunamismsservice.R;
import com.example.gkomen.sunamismsservice.Service.SMSService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                //start mpesa service
                Intent intent = new Intent(getApplicationContext(),SMSService.class);
                startService(intent);
            }
        });
    }
}
