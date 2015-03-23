package com.springapp.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ioana.diaconu on 2/27/2015.
 */
public class AboutInfo {
    public static long generateTimeInfo() {
        return System.currentTimeMillis();
    }

    public String logInfo(String info, List<String> authors){
        StringBuilder information =  new StringBuilder();
      //  information.append("This class is used to ");
        List<String> about = logInfoAbout(info,authors);
        for(String s:about){
            information.append(s);
        }
        return information.toString();
    }

    private List<String> logInfoAbout(String info, List<String> authors) {
        int count = 0;
        List<String> information = new ArrayList<String>();
        information.add(String.valueOf(generateTimeInfo()));
        information.add(info);
        for(String author:authors){
            information.add(++count + " " +author);
        }
        return information;
    }

    public boolean createLogFile(String path){
        File logFile = new File(path);
        if(logFile.exists()){
            throw new IllegalArgumentException();
        }

        return logFile.mkdirs();
    }

    public String createLogMessage(){
        StringBuilder message = new StringBuilder();
        message.append("Logging " + generateTimeInfo()+"\n");
        message.append("Author @Ioana \n");
        return message.toString() ;
    }

}
