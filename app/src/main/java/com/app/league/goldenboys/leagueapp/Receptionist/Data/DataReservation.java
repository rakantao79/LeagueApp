package com.app.league.goldenboys.leagueapp.Receptionist.Data;

public class DataReservation {

    private String reserveName;
    private String reserveDate;
    private String reserveTimeStart;
    private String reserveTimeEnd;
    private String reserveTransDate;

    public DataReservation() {
    }

    public DataReservation(String reserveName, String reserveDate, String reserveTimeStart, String reserveTimeEnd, String reserveTransDate) {
        this.reserveName = reserveName;
        this.reserveDate = reserveDate;
        this.reserveTimeStart = reserveTimeStart;
        this.reserveTimeEnd = reserveTimeEnd;
        this.reserveTransDate = reserveTransDate;
    }

    public String getReserveName() {
        return reserveName;
    }

    public void setReserveName(String reserveName) {
        this.reserveName = reserveName;
    }

    public String getReserveDate() {
        return reserveDate;
    }

    public void setReserveDate(String reserveDate) {
        this.reserveDate = reserveDate;
    }

    public String getReserveTimeStart() {
        return reserveTimeStart;
    }

    public void setReserveTimeStart(String reserveTimeStart) {
        this.reserveTimeStart = reserveTimeStart;
    }

    public String getReserveTimeEnd() {
        return reserveTimeEnd;
    }

    public void setReserveTimeEnd(String reserveTimeEnd) {
        this.reserveTimeEnd = reserveTimeEnd;
    }

    public String getReserveTransdate() {
        return reserveTransDate;
    }

    public void setReserveTransdate(String reserveTransdate) {
        this.reserveTransDate = reserveTransdate;
    }
}
