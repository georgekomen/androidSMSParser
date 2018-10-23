package com.example.gkomen.sunamismsservice.Model;

public class MpesaMsg {
    private String imei;
    private String msg;
    private String address;
    private String person;


    public MpesaMsg(String imei, String msg, String address, String person) {
        this.imei = imei;
        this.msg = msg;
        this.address = address;
        this.person = person;
    }

    public MpesaMsg() {

    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }
}
