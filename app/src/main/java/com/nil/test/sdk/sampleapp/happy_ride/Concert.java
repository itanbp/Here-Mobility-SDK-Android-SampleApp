package com.nil.test.sdk.sampleapp.happy_ride;

/**
 * Created by itanbp on 03/06/2018.
 */
public class Concert {

    String title;
    String arena;
    String date;
    int drawableId;


    public Concert(String title, String arena, String date, int drawableId) {
        this.title = title;
        this.arena = arena;
        this.date = date;
        this.drawableId = drawableId;
    }


    public String getTitle() {
        return title;
    }

    public String getArena() {
        return arena;
    }

    public String getDate() {
        return date;
    }

    public int getDrawableId() {
        return drawableId;
    }
}
