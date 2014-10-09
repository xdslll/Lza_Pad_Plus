package com.lza.pad.core.db.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 各模块对应的参数
 *
 * @author xiads
 * @Date 14-9-15.
 */
@DatabaseTable(tableName = "lza_pad_api_param")
public class ApiParams implements Parcelable {

    public static final String _NAV_ID = "api_par_nav_id";
    public static final String _KEY = "api_par_key";
    public static final String _VALUE = "api_par_value";
    public static final String _TYPE = "api_par_type";

    public static final String API_TYPE_LIST = "list";
    public static final String API_TYPE_DETAIL = "detail";
    public static final String API_TYPE_UNIVERSAL = "universal";

    public static final String API_KEY_CONTROL = "control";
    public static final String API_KEY_PAGE = "page";

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = "api_par_nav_id")
    private int navId;

    @DatabaseField(columnName = "api_par_key")
    private String key;

    @DatabaseField(columnName = "api_par_value")
    private String value;

    /**
     * 参数对应的类型
     * list - 列表
     * detail - 明细
     * universal - 通用
     * etc - 其他
     */
    @DatabaseField(columnName = "api_par_type")
    private String type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNavId() {
        return navId;
    }

    public void setNavId(int navId) {
        this.navId = navId;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(navId);
        dest.writeString(key);
        dest.writeString(value);
        dest.writeString(type);
    }

    public static final Creator<ApiParams> CREATOR = new Creator<ApiParams>() {
        @Override
        public ApiParams createFromParcel(Parcel source) {
            ApiParams apiParams = new ApiParams();
            apiParams.id = source.readInt();
            apiParams.navId = source.readInt();
            apiParams.key = source.readString();
            apiParams.value = source.readString();
            apiParams.type = source.readString();
            return apiParams;
        }

        @Override
        public ApiParams[] newArray(int size) {
            return new ApiParams[size];
        }
    };
}
