package com.lza.pad.core.db.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 11/7/14.
 */
@DatabaseTable(tableName = "subject_type")
public class SubjectType {

    public static final String _ID = "id";
    public static final String _PX = "px";
    public static final String _IF_SHOW = "ifShow";

    @DatabaseField(generatedId = true)
    private int inner_id;

    @DatabaseField
    private String id;

    @DatabaseField
    private String title;

    @DatabaseField
    private String value;

    @DatabaseField
    private String schoolId;

    @DatabaseField
    private int px;

    @DatabaseField
    private String xk_type;

    /**
     * 模块的编号
     */
    @DatabaseField
    private String tag;

    /**
     * 是否显示该分类
     * 0 - 不显示
     * 1 - 显示
     */
    @DatabaseField
    private int ifShow;
    public static final int NOT_SHOW = 0;
    public static final int IS_SHOW = 1;

    public SubjectType() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getXk_type() {
        return xk_type;
    }

    public void setXk_type(String xk_type) {
        this.xk_type = xk_type;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getIfShow() {
        return ifShow;
    }

    public void setIfShow(int ifShow) {
        this.ifShow = ifShow;
    }

    public int getInner_id() {
        return inner_id;
    }

    public void setInner_id(int inner_id) {
        this.inner_id = inner_id;
    }

    public int getPx() {
        return px;
    }

    public void setPx(int px) {
        this.px = px;
    }

    @Override
    public boolean equals(Object o) {
        if (o != null && o instanceof SubjectType) {
            SubjectType data = (SubjectType) o;
            return data.getId().equals(id) &&
                    data.getTitle().equals(title) &&
                    data.getValue().equals(value);
        }
        return false;
    }
}
