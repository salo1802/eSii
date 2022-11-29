package com.example.esii;

import org.json.JSONException;
import org.json.JSONObject;

public class Message {
    public String from,message,time,sender;

    public Message() {
    }

    public Message(String from, String message, String time) throws JSONException {
        this.message = message;
        this.time = time;
        this.from = from;
        String jsonString = this.from;
        JSONObject jsonObject = new JSONObject(jsonString);
        this.sender = jsonObject.getString("username");
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
