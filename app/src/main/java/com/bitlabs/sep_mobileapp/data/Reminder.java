package com.bitlabs.sep_mobileapp.data;

import java.util.Date;

/**
 * Created by Chamin on 5/3/2016.
 */
public class Reminder {

    private int id;
    private boolean alarmOn;
    private String notificationType;
    private String alarmTone;
    private String title;
    private Date time;
    private String reccurenceType;

    public Reminder(int id, boolean alarmOn, String notificationType, String alarmTone, String title, Date time, String reccurenceType) {
        this.id = id;
        this.alarmOn = alarmOn;
        this.notificationType = notificationType;
        this.alarmTone = alarmTone;
        this.title = title;
        this.time = time;
        this.reccurenceType = reccurenceType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReccurenceType() {
        return reccurenceType;
    }

    public void setReccurenceType(String reccurenceType) {
        this.reccurenceType = reccurenceType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public boolean getAlarmOn() {
        return alarmOn;
    }

    public void setAlarmOn(boolean alarmOn) {
        this.alarmOn = alarmOn;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public String getAlarmTone() {
        return alarmTone;
    }

    public void setAlarmTone(String alarmTone) {
        this.alarmTone = alarmTone;
    }
}
