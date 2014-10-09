package com.lza.pad.core.db.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.List;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 14-9-12.
 */
@DatabaseTable(tableName = "lza_pad_ebook_request_history")
public class EbookRequest implements Parcelable {

    public static final String TABLE_NAME = "lza_pad_ebook_request_history";
    public static final String END_TAG_NOT_FINISH = "fail!";
    public static final String END_TAG_FINISH = "ok!";
    public static final String STATE_OK = "ok";
    public static final String HOT_BOOK_CONTENTS = "contents1";

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = "request_qk_table")
    private String qk_table;

    @DatabaseField(columnName = "request_qk_articles")
    private String qk_articles;

    @DatabaseField(columnName = "request_ebook_table")
    private String ebook_table;

    @DatabaseField(columnName = "request_ebook_mr")
    private String ebook_mr;

    @DatabaseField(columnName = "request_state")
    private String state;

    @DatabaseField(columnName = "request_t_name")
    private String t_name;

    @DatabaseField(columnName = "request_page_size")
    private int pagesize;

    @DatabaseField(columnName = "request_return_num")
    private int returnnum;

    @DatabaseField(columnName = "request_page_num")
    private int pagenum;

    @DatabaseField(columnName = "request_ye")
    private int ye;

    @DatabaseField(columnName = "request_end_tag")
    private String endTag;

    @DatabaseField(columnName = "request_date")
    private long date;

    private List<HotBookContent> contents1;

    private List<Ebook> contents;

    private UrlType urlType;

    private ActionType actionType;

    public EbookRequest() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getT_name() {
        return t_name;
    }

    public void setT_name(String t_name) {
        this.t_name = t_name;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    public int getReturnnum() {
        return returnnum;
    }

    public void setReturnnum(int returnnum) {
        this.returnnum = returnnum;
    }

    public int getPagenum() {
        return pagenum;
    }

    public void setPagenum(int pagenum) {
        this.pagenum = pagenum;
    }

    public int getYe() {
        return ye;
    }

    public void setYe(int ye) {
        this.ye = ye;
    }

    public String getEndTag() {
        return endTag;
    }

    public void setEndTag(String endTag) {
        this.endTag = endTag;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getEbook_table() {
        return ebook_table;
    }

    public void setEbook_table(String ebook_table) {
        this.ebook_table = ebook_table;
    }

    public String getEbook_mr() {
        return ebook_mr;
    }

    public void setEbook_mr(String ebook_mr) {
        this.ebook_mr = ebook_mr;
    }

    public UrlType getUrlType() {
        return urlType;
    }

    public void setUrlType(UrlType urlType) {
        this.urlType = urlType;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public List<Ebook> getContents() {
        return contents;
    }

    public void setContents(List<Ebook> contents) {
        this.contents = contents;
    }

    public String getQk_table() {
        return qk_table;
    }

    public void setQk_table(String qk_table) {
        this.qk_table = qk_table;
    }

    public String getQk_articles() {
        return qk_articles;
    }

    public void setQk_articles(String qk_articles) {
        this.qk_articles = qk_articles;
    }

    public List<HotBookContent> getContents1() {
        return contents1;
    }

    public void setContents1(List<HotBookContent> contents1) {
        this.contents1 = contents1;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(ebook_table);
        dest.writeString(ebook_mr);
        dest.writeString(state);
        dest.writeString(t_name);
        dest.writeInt(pagesize);
        dest.writeInt(returnnum);
        dest.writeInt(pagenum);
        dest.writeInt(ye);
        dest.writeString(endTag);
        dest.writeLong(date);
        dest.writeString(qk_table);
        dest.writeString(qk_articles);
    }

    public static final Creator<EbookRequest> CREATOR = new Creator<EbookRequest>() {
        @Override
        public EbookRequest createFromParcel(Parcel source) {
            EbookRequest request = new EbookRequest();
            request.id = source.readInt();
            request.ebook_table = source.readString();
            request.ebook_mr = source.readString();
            request.state = source.readString();
            request.t_name = source.readString();
            request.pagesize = source.readInt();
            request.returnnum = source.readInt();
            request.pagenum = source.readInt();
            request.ye = source.readInt();
            request.endTag = source.readString();
            request.date = source.readLong();
            request.qk_table = source.readString();
            request.qk_articles = source.readString();
            return request;
        }

        @Override
        public EbookRequest[] newArray(int size) {
            return new EbookRequest[size];
        }
    };
}
