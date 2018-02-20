package com.example.gkomen.sunamismsservice.Service;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import com.example.gkomen.sunamismsservice.APIInterface.SMSServiceInterface;
import com.example.gkomen.sunamismsservice.APIUtils.APIUtils;
import com.example.gkomen.sunamismsservice.Model.Message;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SMSService extends Service {
    private SMSServiceInterface smsServiceInterface;
    private Handler handler;

    public SMSService() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        smsServiceInterface = APIUtils.sMSServiceInterface();
        getMessages();
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void getMessages() {
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                smsServiceInterface.loadMessages().enqueue(new Callback<List<Message>>() {

                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                        if (response.isSuccessful()) {
                            List<Message> changesList = response.body();
                            changesList.forEach(change -> {

                            });
                        } else {
                            System.out.println(response.errorBody());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Message>> call, Throwable t) {

                    }
                });
                handler.postDelayed(this, 60000);
            }
        }, 2000);
    }

    private void markMessageAsSent(Message message) {
        smsServiceInterface.markMessageAsSent(message).enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if (response.isSuccessful()) {
                    System.out.println(response.raw());
                } else {
                    System.out.println(response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {

            }
        });
    }
}