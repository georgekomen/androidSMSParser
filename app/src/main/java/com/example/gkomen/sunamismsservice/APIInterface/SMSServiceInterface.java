package com.example.gkomen.sunamismsservice.APIInterface;

import com.example.gkomen.sunamismsservice.Model.Message;
import com.example.gkomen.sunamismsservice.Model.MpesaMsg;
import com.example.gkomen.sunamismsservice.Model.SwitchResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by g.komen on 02/02/2018.
 */

public interface SMSServiceInterface {
    @POST("recordSwitchResponse")
    Call<SwitchResponse> recordSwitchResponse(@Body SwitchResponse switchResponse);

    // http://api.sunamiapp.net/api/customers/postReceive_mpesa/
    @POST("postReceive_mpesa")
    Call<String> postMpesaMsg(@Body List<MpesaMsg> mpesaMsgs);

}
