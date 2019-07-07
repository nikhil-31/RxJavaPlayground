package com.example.nikhilb.rxjavaplayground;


import android.support.annotation.NonNull;

public class Note {
    private int id;
    private String note;

    public Note( int id, String note ) {
        this.id = id;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public String getNote() {
        return note;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public void setNote( String note ) {
        this.note = note;
    }

    @NonNull
    @Override
    public String toString() {
        return "Id: " + id + " Note: " + note;
    }
}
