package com.message.generator;


import org.json.JSONObject;

/**
 * Created by ioana.diaconu on 2/27/2015.
 */
public class Message {
    private final String date;
    private final String content;

    public Message(String date, String content) {
        this.date = date;
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }
}
