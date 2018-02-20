package com.example.gkomen.sunamismsservice.Model;

import java.util.Date;

/**
 * Created by g.komen on 02/02/2018.
 */

public class Message {

    private String Name;
    private String Message;
    private String Date;

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }
}
