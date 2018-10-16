package com.app.league.goldenboys.leagueapp.Admin.Data;

public class DataNewsFeed {

    private String newstitle;
    private String newscontent;
    private String pushKey;
    private String datePosted;
    private String user_id;
    private String imageUrl;
    private Long counter;

    public DataNewsFeed() {
    }

    public DataNewsFeed(String newstitle, String newscontent, String pushKey, String datePosted, String user_id, String imageUrl, Long counter) {
        this.newstitle = newstitle;
        this.newscontent = newscontent;
        this.pushKey = pushKey;
        this.datePosted = datePosted;
        this.user_id = user_id;
        this.imageUrl = imageUrl;
        this.counter = counter;
    }

    public String getNewstitle() {
        return newstitle;
    }

    public void setNewstitle(String newstitle) {
        this.newstitle = newstitle;
    }

    public String getNewscontent() {
        return newscontent;
    }

    public void setNewscontent(String newscontent) {
        this.newscontent = newscontent;
    }

    public String getPushKey() {
        return pushKey;
    }

    public void setPushKey(String pushKey) {
        this.pushKey = pushKey;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getCounter() {
        return counter;
    }

    public void setCounter(Long counter) {
        this.counter = counter;
    }
}
