package com.example.gkomen.sunamismsservice.Service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.example.gkomen.sunamismsservice.APIInterface.SMSServiceInterface;
import com.example.gkomen.sunamismsservice.APIUtils.APIUtils;
import com.example.gkomen.sunamismsservice.APIUtils.constants;
import com.example.gkomen.sunamismsservice.Model.MpesaMsg;
import com.example.gkomen.sunamismsservice.Model.SwitchResponse;

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
    private String imei;

    public SMSService() {

    }

    @SuppressLint("MissingPermission")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        TelephonyManager tm = (TelephonyManager) SMSService.this.getSystemService(Context.TELEPHONY_SERVICE);
        imei = tm.getDeviceId();
        smsServiceInterface = APIUtils.sMSServiceInterface();
        readSms();
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void readSms() {
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                cursor = getContentResolver().query(Uri.parse("content://sms/inbox"), null, null, null, null);
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        List<MpesaMsg> mpesaMsgs = new ArrayList<>();
                        for (int idx = 0; idx < cursor.getCount(); idx++) {
                            String msg = cursor.getString(cursor.getColumnIndexOrThrow("body"));
                            String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
                            String person = cursor.getString(cursor.getColumnIndexOrThrow("person"));
                            Log.d("-----------------------", msg);
                            //*1sunami#1 *2888888#2 *31#3
                            if(msg.contains("*1sunami#1")) {
//                                switchResponse = new SwitchResponse();
//
//                                int imeiSI = msg.indexOf("*2",0)+2;
//                                int imeiEI = msg.indexOf("#2",0);
//                                String imei = msg.substring(imeiSI, imeiEI);
//                                switchResponse.setImei(imei);
//
//                                int valueSI = msg.indexOf("*3", 0)+2;
//                                int valueEI = msg.indexOf("#3", 0);
//                                String value = msg.substring(valueSI,valueEI);
//                                switchResponse.setStatus(value);
//
//                                switchResponse.setAddress(address);
//
//                                recordSwitchResponse(switchResponse);
                            } else {
                                MpesaMsg mpesaMsg = new MpesaMsg(imei, msg, address, person);
                                mpesaMsgs.add(mpesaMsg);
                            }
                            cursor.moveToNext();
                        }
                        postMpesaMsg(mpesaMsgs);
                        cursor.close();
                    }
                }
                handler.postDelayed(this, 10000);
            }
        }, 10000);
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

    private void postMpesaMsg(List<MpesaMsg> mpesaMsgs){
        smsServiceInterface.postMpesaMsg(mpesaMsgs).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                constants.showAlert(response.body() + "\n" + response.message());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                constants.showAlert(call.toString());
            }
        });
    }
}