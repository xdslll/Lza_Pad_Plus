package com.lza.pad.ui.service;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 14-9-16.
 */
public class Params implements Parcelable {

    private int id;
    private String key;
    private String value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(key);
        dest.writeString(value);
    }

    public static final Creator<Params> CREATOR = new Creator<Params>() {
        @Override
        public Params createFromParcel(Parcel source) {
            Params p = new Params();
            p.id = source.readInt();
            p.key = source.readString();
            p.value = source.readString();
            return p;
        }

        @Override
        public Params[] newArray(int size) {
            return new Params[size];
        }
    };
}
