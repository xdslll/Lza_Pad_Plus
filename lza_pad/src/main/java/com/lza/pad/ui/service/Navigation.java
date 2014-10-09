package com.lza.pad.ui.service;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 14-9-16.
 */
public class Navigation implements Parcelable {

    private int id;
    private String name;
    private List<Params> params;
    private ApiInfo apiInfo;

    public ApiInfo getApiInfo() {
        return apiInfo;
    }

    public void setApiInfo(ApiInfo apiInfo) {
        this.apiInfo = apiInfo;
    }

    public List<Params> getParams() {
        return params;
    }

    public void setParams(List<Params> params) {
        this.params = params;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeTypedList(params);
        dest.writeParcelable(apiInfo, flags);
    }

    public static final Creator<Navigation> CREATOR = new Creator<Navigation>() {
        @Override
        public Navigation createFromParcel(Parcel source) {
            Navigation nav = new Navigation();
            nav.id = source.readInt();
            nav.name = source.readString();
            nav.params = new ArrayList<Params>();
            source.readTypedList(nav.params, Params.CREATOR);
            nav.apiInfo = source.readParcelable(ApiInfo.class.getClassLoader());
            return nav;
        }

        @Override
        public Navigation[] newArray(int size) {
            return new Navigation[size];
        }
    };

}
