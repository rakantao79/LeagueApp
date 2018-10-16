package com.app.league.goldenboys.leagueapp.Customer.Data;

public class DataUserNotificationFinal {

    private String notifTitle;
    private String notifMatch;
    private String notfiDate;
    private String notifKey;

    public DataUserNotificationFinal() {
    }

    public DataUserNotificationFinal(String notifTitle, String notifMatch, String notfiDate, String notifKey) {
        this.notifTitle = notifTitle;
        this.notifMatch = notifMatch;
        this.notfiDate = notfiDate;
        this.notifKey = notifKey;
    }

    public String getNotifTitle() {
        return notifTitle;
    }

    public void setNotifTitle(String notifTitle) {
        this.notifTitle = notifTitle;
    }

    public String getNotifMatch() {
        return notifMatch;
    }

    public void setNotifMatch(String notifMatch) {
        this.notifMatch = notifMatch;
    }

    public String getNotfiDate() {
        return notfiDate;
    }

    public void setNotfiDate(String notfiDate) {
        this.notfiDate = notfiDate;
    }

    public String getNotifKey() {
        return notifKey;
    }

    public void setNotifKey(String notifKey) {
        this.notifKey = notifKey;
    }
}
