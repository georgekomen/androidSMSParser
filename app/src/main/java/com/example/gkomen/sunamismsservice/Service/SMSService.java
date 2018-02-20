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

    private int sent = 0;
    public SMSService() {
        smsServiceInterface = APIUtils.sMSServiceInterface();
        getMessages();
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

    private void getMessages(){
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                smsServiceInterface.loadMessages().enqueue(new Callback<List<Message>>() {

                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                        if(response.isSuccessful()) {
                            List<Message> changesList = response.body();
                            changesList.forEach(change -> {
                                System.out.println(change.getMessage());
                                if(sent < 1){
                                    sendSMS("+254714749630","unataka credo?");
                                    sent ++;
                                }
                                try {
                                    Thread.sleep(5000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
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

    private void markMessageAsSent(Message message){
        smsServiceInterface.markMessageAsSent(message).enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if(response.isSuccessful()) {
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

    public void sendSMS(String phoneNumber,String message) {
        SmsManager smsManager = SmsManager.getDefault();

        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        SmsManager sms = SmsManager.getDefault();
        ArrayList<String> parts = sms.divideMessage(message);
        int messageCount = parts.size();

//        Log.i("Message Count", "Message Count: " + messageCount);

        ArrayList<PendingIntent> deliveryIntents = new ArrayList<PendingIntent>();
        ArrayList<PendingIntent> sentIntents = new ArrayList<PendingIntent>();

        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);
        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0, new Intent(DELIVERED), 0);

        for (int j = 0; j < messageCount; j++) {
            sentIntents.add(sentPI);
            deliveryIntents.add(deliveredPI);
        }

        // ---when the SMS has been sent---
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Log.i("Message Count", "-------------sent");
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:

                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:

                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:

                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:

                        break;
                }
            }
        }, new IntentFilter(SENT));

        // ---when the SMS has been delivered---
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Log.i("Message Count", "-------------delivered");
                        break;
                    case Activity.RESULT_CANCELED:

                        break;
                }
            }
        }, new IntentFilter(DELIVERED));
        smsManager.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
//        sms.sendMultipartTextMessage(phoneNumber, null, parts, sentIntents, deliveryIntents);
    }
}
