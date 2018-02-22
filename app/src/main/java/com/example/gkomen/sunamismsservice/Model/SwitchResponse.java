package com.example.gkomen.sunamismsservice.Model;

/**
 * Created by g.komen on 20/02/2018.
 */

public class SwitchResponse {
    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String address;
    private String imei;
    private String status;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
