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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String address;
    private String IMEI;
    private String status;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
