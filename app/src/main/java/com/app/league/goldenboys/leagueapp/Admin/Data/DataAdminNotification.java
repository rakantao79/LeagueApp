package com.app.league.goldenboys.leagueapp.Admin.Data;

public class DataAdminNotification {

    private String notifTitle;
    private String notifContent;
    private String notifDate;

    public DataAdminNotification() {
    }

    public DataAdminNotification(String notifTitle, String notifContent, String notifDate) {
        this.notifTitle = notifTitle;
        this.notifContent = notifContent;
        this.notifDate = notifDate;
    }

    public String getNotifTitle() {
        return notifTitle;
    }

    public void setNotifTitle(String notifTitle) {
        this.notifTitle = notifTitle;
    }

    public String getNotifContent() {
        return notifContent;
    }

    public void setNotifContent(String notifContent) {
        this.notifContent = notifContent;
    }

    public String getNotifDate() {
        return notifDate;
    }

    public void setNotifDate(String notifDate) {
        this.notifDate = notifDate;
    }
}
