package com.lza.pad.ui.service;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 14-9-16.
 */
public class ApiInfo implements Parcelable {

    private int id;
    private String url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(url);
    }

    public static final Creator<ApiInfo> CREATOR = new Creator<ApiInfo>() {
        @Override
        public ApiInfo createFromParcel(Parcel source) {
            ApiInfo a = new ApiInfo();
            a.id = source.readInt();
            a.url = source.readString();
            return a;
        }

        @Override
        public ApiInfo[] newArray(int size) {
            return new ApiInfo[size];
        }
    };
}
