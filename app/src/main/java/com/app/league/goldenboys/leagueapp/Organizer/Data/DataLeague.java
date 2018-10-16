package com.app.league.goldenboys.leagueapp.Organizer.Data;

public class DataLeague {

    private String leagueTitle;
    private String leagueDesc;
    private String divisionJunior;
    private String divisionSenior;
    private String divisionMaster;
    private String divisionMediet;
    private String leagueStatus;
    private String leagueStartDate;
    private String leagueEndDate;
    private String imageLeague;
    private Long count;
    private String pushKey;


    public DataLeague() {
    }

    public DataLeague(String leagueTitle, String leagueDesc, String divisionJunior, String divisionSenior, String divisionMaster, String divisionMediet, String leagueStatus, String leagueStartDate, String leagueEndDate, String imageLeague, Long count, String pushKey) {
        this.leagueTitle = leagueTitle;
        this.leagueDesc = leagueDesc;
        this.divisionJunior = divisionJunior;
        this.divisionSenior = divisionSenior;
        this.divisionMaster = divisionMaster;
        this.divisionMediet = divisionMediet;
        this.leagueStatus = leagueStatus;
        this.leagueStartDate = leagueStartDate;
        this.leagueEndDate = leagueEndDate;
        this.imageLeague = imageLeague;
        this.count = count;
        this.pushKey = pushKey;
    }

    public String getLeagueTitle() {
        return leagueTitle;
    }

    public void setLeagueTitle(String leagueTitle) {
        this.leagueTitle = leagueTitle;
    }

    public String getLeagueDesc() {
        return leagueDesc;
    }

    public void setLeagueDesc(String leagueDesc) {
        this.leagueDesc = leagueDesc;
    }

    public String getDivisionJunior() {
        return divisionJunior;
    }

    public void setDivisionJunior(String divisionJunior) {
        this.divisionJunior = divisionJunior;
    }

    public String getDivisionSenior() {
        return divisionSenior;
    }

    public void setDivisionSenior(String divisionSenior) {
        this.divisionSenior = divisionSenior;
    }

    public String getDivisionMaster() {
        return divisionMaster;
    }

    public void setDivisionMaster(String divisionMaster) {
        this.divisionMaster = divisionMaster;
    }

    public String getDivisionMediet() {
        return divisionMediet;
    }

    public void setDivisionMediet(String divisionMediet) {
        this.divisionMediet = divisionMediet;
    }

    public String getLeagueStatus() {
        return leagueStatus;
    }

    public void setLeagueStatus(String leagueStatus) {
        this.leagueStatus = leagueStatus;
    }

    public String getLeagueStartDate() {
        return leagueStartDate;
    }

    public void setLeagueStartDate(String leagueStartDate) {
        this.leagueStartDate = leagueStartDate;
    }

    public String getLeagueEndDate() {
        return leagueEndDate;
    }

    public void setLeagueEndDate(String leagueEndDate) {
        this.leagueEndDate = leagueEndDate;
    }

    public String getImageLeague() {
        return imageLeague;
    }

    public void setImageLeague(String imageLeague) {
        this.imageLeague = imageLeague;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getPushKey() {
        return pushKey;
    }

    public void setPushKey(String pushKey) {
        this.pushKey = pushKey;
    }
}
