package com.example.gkomen.sunamismsservice.Model;

/**
 * Created by g.komen on 20/02/2018.
 */

public class SwitchResponse {
    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    private String IMEI;
    private int status;
}
