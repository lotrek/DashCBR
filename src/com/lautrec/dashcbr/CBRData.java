package com.lautrec.dashcbr;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by lotrek on 18.03.14.
 */
public class CBRData {
    private Float latestRecord;
    private Float previousRecord;
    private Date latestDate;
    private Date previousDate;
    private String latestRecordString;
    private String previousRecordString;
    private String latestDateString;
    private String previousDateString;

    public static final String downArrow = "▼";
    public static final String upArrow = "▲";

    public float getLatestRecord() {
        latestRecord = Float.parseFloat(latestRecordString.replace(",", "."));
        return latestRecord;
    }



    public float getPreviousRecord() {
        previousRecord = Float.parseFloat(previousRecordString.replace(",", "."));
        return previousRecord;
    }



    public Date getLatestDate() {
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
        try {
            latestDate = df.parse(latestDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return latestDate;
    }


    public Date getPreviousDate() {
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
        try {
            latestDate = df.parse(previousDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }return previousDate;
    }



    public void addData(String value, String date){
        if(latestDateString != null && latestRecordString != null){
            previousDateString = latestDateString;
            previousRecordString = latestRecordString;
        }
        latestDateString = date;
        latestRecordString = value;
    }
public String getResult(){
    if(getLatestRecord() < getPreviousRecord()) return latestRecordString + downArrow;
    if(getLatestRecord() > getPreviousRecord()) return latestRecordString +   upArrow;
    return latestRecordString;
}
}
