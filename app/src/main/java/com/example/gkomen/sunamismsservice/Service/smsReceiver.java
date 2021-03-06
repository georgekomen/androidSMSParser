package com.example.gkomen.sunamismsservice.Service;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.example.gkomen.sunamismsservice.APIInterface.SMSServiceInterface;
import com.example.gkomen.sunamismsservice.APIUtils.APIUtils;
import com.example.gkomen.sunamismsservice.Model.MpesaMsg;
import com.example.gkomen.sunamismsservice.Model.SwitchResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class smsReceiver extends BroadcastReceiver {
    private SMSServiceInterface smsServiceInterface;
    private SwitchResponse switchResponse;
    private String imei;
    @SuppressLint("MissingPermission")
    @Override
    public void onReceive(Context context, Intent intent) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        imei = tm.getDeviceId();

        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            smsServiceInterface = APIUtils.sMSServiceInterface();

            Bundle bundle = intent.getExtras();//---get the SMS message passed in---
            SmsMessage[] msgs = null;
            String msg_from;
            String msgBody;
            if (bundle != null){
                //---retrieve the SMS message received---
                try{
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];
                    for(int i=0; i<msgs.length; i++){
                        msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                        msg_from = msgs[i].getOriginatingAddress();
                        msgBody = msgs[i].getMessageBody();
                        Log.d("-----------------------", msgBody);
                        //*1sunami#1 *2888888#2 *31#3
                        if(msgBody.contains("*1sunami#1")) {
//                            switchResponse = new SwitchResponse();
//
//                            int imeiSI = msgBody.indexOf("*2",0)+2;
//                            int imeiEI = msgBody.indexOf("#2",0);
//                            String imei = msgBody.substring(imeiSI, imeiEI);
//                            switchResponse.setImei(imei);
//
//                            int valueSI = msgBody.indexOf("*3", 0)+2;
//                            int valueEI = msgBody.indexOf("#3", 0);
//                            String value = msgBody.substring(valueSI,valueEI);
//                            switchResponse.setStatus(value);
//
//                            switchResponse.setAddress(msg_from);
//
//                            recordSwitchResponse(switchResponse);
                        } else {
                            List<MpesaMsg> mpesaMsgs = new ArrayList<>();
                            mpesaMsgs.add(new MpesaMsg(imei, msgBody, msg_from, ""));
                            smsServiceInterface.postMpesaMsg(mpesaMsgs);
                        }
                    }
                }catch(Exception e){
                    //Log.d("Exception caught",e.getMessage());
                }
            }
        }
    }

    private void recordSwitchResponse(SwitchResponse switchResponse){
        Log.d("-----------------------", switchResponse.getAddress());
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
