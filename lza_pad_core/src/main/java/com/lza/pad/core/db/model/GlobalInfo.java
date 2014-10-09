package com.lza.pad.core.db.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 全局参数表
 *
 * @author xiads
 * @Date 14-9-12.
 */
@DatabaseTable(tableName = "lza_pad_global_info")
public class GlobalInfo implements Parcelable {

    @DatabaseField(generatedId = true)
    private int id;

    /**
     * 字段的名称
     */
    @DatabaseField(columnName = "global_info_key", unique = true)
    private String key;

    /**
     * 字段的值
     */
    @DatabaseField(columnName = "global_info_value")
    private String value;

    /**
     * 所属类型
     */
    @DatabaseField(columnName = "global_info_type")
    private String type;

    /**
     * 二级类型
     */
    @DatabaseField(columnName = "global_info_sub_type")
    private String subType;

    /**
     * 备注或说明
     */
    @DatabaseField(columnName = "global_info_remark")
    private String remark;

    /**
     * 预留字段
     */
    @DatabaseField(columnName = "global_info_etc")
    private String etc;

    public GlobalInfo() {}

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getEtc() {
        return etc;
    }

    public void setEtc(String etc) {
        this.etc = etc;
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
        dest.writeString(type);
        dest.writeString(subType);
        dest.writeString(remark);
        dest.writeString(etc);
    }

    public static final Creator<GlobalInfo> CREATOR = new Creator<GlobalInfo>() {
        @Override
        public GlobalInfo createFromParcel(Parcel source) {
            GlobalInfo gi = new GlobalInfo();
            gi.id = source.readInt();
            gi.key = source.readString();
            gi.value = source.readString();
            gi.type = source.readString();
            gi.subType = source.readString();
            gi.remark = source.readString();
            gi.etc = source.readString();
            return gi;
        }

        @Override
        public GlobalInfo[] newArray(int size) {
            return new GlobalInfo[size];
        }
    };
}
