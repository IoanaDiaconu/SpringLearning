package com.message.generator;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

/**
 * Created by ioana.diaconu on 2/27/2015.
 */
@Component
public class MessageBean {
    public Message generateMessage(){
        final String date = Time.getDate();
        return new Message(date,"just some info");
    }

}
