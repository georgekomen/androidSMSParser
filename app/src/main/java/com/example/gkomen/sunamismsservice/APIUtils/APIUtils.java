package com.example.gkomen.sunamismsservice.APIUtils;

import com.example.gkomen.sunamismsservice.APIInterface.SMSServiceInterface;
import com.example.gkomen.sunamismsservice.RetrofitInstance.RetrofitClient;

/**
 * Created by g.komen on 03/02/2018.
 */

public class APIUtils {
    private APIUtils() {}

    public static final String BASE_URL = "http://api.sunamiapp.net/api/customers/";

    public static SMSServiceInterface sMSServiceInterface() {
        return RetrofitClient.getClient(BASE_URL).create(SMSServiceInterface.class);
    }
}
