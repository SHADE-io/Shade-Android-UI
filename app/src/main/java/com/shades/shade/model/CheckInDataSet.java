package com.shades.shade.model;


public class CheckInDataSet {
    private int smileyState;
    private String date;

    public CheckInDataSet(int smileyState, String date) {
        this.smileyState = smileyState;
        this.date = date;
    }

    public int getSmileyState() {
        return smileyState;
    }

    public void setSmileyState(int smileyState) {
        this.smileyState = smileyState;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
