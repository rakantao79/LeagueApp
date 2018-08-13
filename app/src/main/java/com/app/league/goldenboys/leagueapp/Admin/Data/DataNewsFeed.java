package com.app.league.goldenboys.leagueapp.Admin.Data;

public class DataNewsFeed {

    private String title;
    private String content;
    private String pushkey;
    private String datePosted;
    private String user_id;
    private String counter;


    public DataNewsFeed() {
    }

    public DataNewsFeed(String title, String content, String pushkey, String datePosted, String user_id, String counter) {
        this.title = title;
        this.content = content;
        this.pushkey = pushkey;
        this.datePosted = datePosted;
        this.user_id = user_id;
        this.counter = counter;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPushkey() {
        return pushkey;
    }

    public void setPushkey(String pushkey) {
        this.pushkey = pushkey;
    }

    public String getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(String datePosted) {
        this.datePosted = datePosted;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCounter() {
        return counter;
    }

    public void setCounter(String counter) {
        this.counter = counter;
    }
}
