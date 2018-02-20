package com.example.gkomen.sunamismsservice.APIInterface;

import com.example.gkomen.sunamismsservice.Model.Message;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by g.komen on 02/02/2018.
 */

public interface SMSServiceInterface {
    @GET("getMessages")
    Call<List<Message>> loadMessages();

    @POST("markMessageAsSent")
    Call<Message> markMessageAsSent(@Body Message message);
}
