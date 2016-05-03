package com.bitlabs.sep_mobileapp.data;

/**
 * Created by Chamin on 5/3/2016.
 */
public class Reminder {

    private boolean alarmOn;
    private String notificationType;
    private String alarmTone;

    public Reminder(boolean alarmOn, String notificationType, String alarmTone) {
        this.alarmOn = alarmOn;
        this.notificationType = notificationType;
        this.alarmTone = alarmTone;
    }

    public boolean isAlarmOn() {
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
