package com.nil.test.sdk.sampleapp.happy_ride;

import android.os.Parcel;
import android.os.Parcelable;

import com.here.mobility.sdk.core.geo.LatLng;

import java.util.ArrayList;

/**
 * Created by itanbp on 03/06/2018.
 */
public class Concert implements Parcelable {

    String title;
    String arena;
    String date;
    int drawableId;
    ArrayList<String> dates;
    LatLng latLng;


    public Concert(String title, String arena, String date, int drawableId, ArrayList<String> dates, LatLng latLng) {
        this.title = title;
        this.arena = arena;
        this.date = date;
        this.drawableId = drawableId;
        this.dates = dates;
        this.latLng = latLng;
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

    public ArrayList<String> getDates() {
        return dates;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.arena);
        dest.writeString(this.date);
        dest.writeInt(this.drawableId);
        dest.writeStringList(this.dates);
        dest.writeParcelable(this.latLng, flags);
    }

    protected Concert(Parcel in) {
        this.title = in.readString();
        this.arena = in.readString();
        this.date = in.readString();
        this.drawableId = in.readInt();
        this.dates = in.createStringArrayList();
        this.latLng = in.readParcelable(LatLng.class.getClassLoader());
    }

    public static final Creator<Concert> CREATOR = new Creator<Concert>() {
        @Override
        public Concert createFromParcel(Parcel source) {
            return new Concert(source);
        }

        @Override
        public Concert[] newArray(int size) {
            return new Concert[size];
        }
    };
}
