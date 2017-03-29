package com.shades.shade.model;

import java.util.ArrayList;

public class AllDataSet {

    private boolean hasSmiley;
    private boolean hasNote;
    private String uvUnit;
    private String date;
    private String note;
    private ArrayList<String> notes;

    public boolean isHasSmiley() {
        return hasSmiley;
    }

    public void setHasSmiley(boolean hasSmiley) {
        this.hasSmiley = hasSmiley;
    }

    public boolean isHasNote() {
        return hasNote;
    }

    public void setHasNote(boolean hasNote) {
        this.hasNote = hasNote;
    }

    public String getUvUnit() {
        return uvUnit;
    }

    public void setUvUnit(String uvUnit) {
        this.uvUnit = uvUnit;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public ArrayList<String> getNotes() {
        return notes;
    }

    public void setNotes(ArrayList<String> notes) {
        this.notes = notes;
    }
}
