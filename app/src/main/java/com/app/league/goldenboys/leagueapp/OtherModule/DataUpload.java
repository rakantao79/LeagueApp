package com.app.league.goldenboys.leagueapp.OtherModule;

public class DataUpload {

    private String mName;
    private String mImageUrl;

    public DataUpload() {
    }

    public DataUpload(String mName, String mImageUrl) {

        if (mName.trim().equals("")){
            mName = "No Name";
        }

        this.mName = mName;
        this.mImageUrl = mImageUrl;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }
}
