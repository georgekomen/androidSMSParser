package com.example.gkomen.sunamismsservice.Service;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
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
import com.example.gkomen.sunamismsservice.Model.SwitchResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SMSService extends Service {
    private SMSServiceInterface smsServiceInterface;
    private Handler handler;
    private Cursor cursor;
    private SwitchResponse switchResponse;

    public SMSService() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        smsServiceInterface = APIUtils.sMSServiceInterface();
        readSms();
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

    public void readSms() {
        // public static final String INBOX = "content://sms/inbox";
        // public static final String SENT = "content://sms/sent";
        // public static final String DRAFT = "content://sms/draft";
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                cursor = getContentResolver().query(Uri.parse("content://sms/inbox"), null, null, null, null);
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        for (int idx = 0; idx < cursor.getCount(); idx++) {
                            String msg = cursor.getString(cursor.getColumnIndexOrThrow("body"));
                            String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
                            String person = cursor.getString(cursor.getColumnIndexOrThrow("person"));
                            Log.d("-----------------------", msg);

                            //*1sunami#1 *2888888#2 *31#3
                            if(msg.contains("*1sunami#1")){
                                switchResponse = new SwitchResponse();

                                int imeiSI = msg.indexOf("*2",0)+2;
                                int imeiEI = msg.indexOf("#2",0);
                                String imei = msg.substring(imeiSI, imeiEI);
                                switchResponse.setIMEI(imei);

                                int valueSI = msg.indexOf("*3", 0)+2;
                                int valueEI = msg.indexOf("#3", 0);
                                String value = msg.substring(valueSI,valueEI);
                                switchResponse.setStatus(value);

                                switchResponse.setAddress(address);

                                recordSwitchResponse(switchResponse);
                            }
                        }
                        cursor.close();
                    }
                }
                handler.postDelayed(this, 1000);
            }
        }, 1000);
    }

    private void recordSwitchResponse(SwitchResponse switchResponse){
        smsServiceInterface.recordSwitchResponse(switchResponse).enqueue(new Callback<SwitchResponse>() {
            @Override
            public void onResponse(Call<SwitchResponse> call, Response<SwitchResponse> response) {

            }

            @Override
            public void onFailure(Call<SwitchResponse> call, Throwable t) {

            }
        });
    }
}