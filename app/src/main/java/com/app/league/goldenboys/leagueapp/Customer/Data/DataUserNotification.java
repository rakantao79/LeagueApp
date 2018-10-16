package com.app.league.goldenboys.leagueapp.Customer.Data;

public class DataUserNotification {

    private String notifContent;
    private long notifCount;
    private String notifDate;
    private String notifkey;
    private String notifTitle;
    private String match1;
    private String match2;
    private String match3;
    private String match4;
    private String match1Sched;
    private String match2Sched;
    private String match3Sched;
    private String match4Sched;


    public DataUserNotification() {
    }

    public DataUserNotification(String notifContent, long notifCount, String notifDate, String notifkey, String notifTitle, String match1, String match2, String match3, String match4, String match1Sched, String match2Sched, String match3Sched, String match4Sched) {
        this.notifContent = notifContent;
        this.notifCount = notifCount;
        this.notifDate = notifDate;
        this.notifkey = notifkey;
        this.notifTitle = notifTitle;
        this.match1 = match1;
        this.match2 = match2;
        this.match3 = match3;
        this.match4 = match4;
        this.match1Sched = match1Sched;
        this.match2Sched = match2Sched;
        this.match3Sched = match3Sched;
        this.match4Sched = match4Sched;
    }

    public String getNotifContent() {
        return notifContent;
    }

    public void setNotifContent(String notifContent) {
        this.notifContent = notifContent;
    }

    public long getNotifCount() {
        return notifCount;
    }

    public void setNotifCount(long notifCount) {
        this.notifCount = notifCount;
    }

    public String getNotifDate() {
        return notifDate;
    }

    public void setNotifDate(String notifDate) {
        this.notifDate = notifDate;
    }

    public String getNotifkey() {
        return notifkey;
    }

    public void setNotifkey(String notifkey) {
        this.notifkey = notifkey;
    }

    public String getNotifTitle() {
        return notifTitle;
    }

    public void setNotifTitle(String notifTitle) {
        this.notifTitle = notifTitle;
    }

    public String getMatch1() {
        return match1;
    }

    public void setMatch1(String match1) {
        this.match1 = match1;
    }

    public String getMatch2() {
        return match2;
    }

    public void setMatch2(String match2) {
        this.match2 = match2;
    }

    public String getMatch3() {
        return match3;
    }

    public void setMatch3(String match3) {
        this.match3 = match3;
    }

    public String getMatch4() {
        return match4;
    }

    public void setMatch4(String match4) {
        this.match4 = match4;
    }

    public String getMatch1Sched() {
        return match1Sched;
    }

    public void setMatch1Sched(String match1Sched) {
        this.match1Sched = match1Sched;
    }

    public String getMatch2Sched() {
        return match2Sched;
    }

    public void setMatch2Sched(String match2Sched) {
        this.match2Sched = match2Sched;
    }

    public String getMatch3Sched() {
        return match3Sched;
    }

    public void setMatch3Sched(String match3Sched) {
        this.match3Sched = match3Sched;
    }

    public String getMatch4Sched() {
        return match4Sched;
    }

    public void setMatch4Sched(String match4Sched) {
        this.match4Sched = match4Sched;
    }
}
