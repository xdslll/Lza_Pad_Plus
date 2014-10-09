package com.lza.pad.core.db.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by xiads on 14-9-3.
 */
@DatabaseTable(tableName = "lza_pad_news")
public class News implements Parcelable {

    public static final String TABLE_NAME = "lza_pad_news";
    public static final String _INNER_ID = "inner_id";
    public static final String _ID = "lza_pad_news_id";
    public static final String _TYPE = "lza_pad_news_type";

    @DatabaseField(generatedId = true)
    private int innerId;

    @DatabaseField(index = true, columnName = "lza_pad_news_id")
    private int id;

    @DatabaseField(columnName = "lza_pad_news_title")
    private String title;

    @DatabaseField(columnName = "lza_pad_news_author")
    private String author;

    @DatabaseField(columnName = "lza_pad_news_pubdate")
    private String pubdate;

    @DatabaseField(columnName = "lza_pad_news_url")
    private String url;

    @DatabaseField(columnName = "lza_pad_news_content", dataType = DataType.LONG_STRING)
    private String content;

    @DatabaseField(columnName = "lza_pad_news_school_id")
    private int school_id;

    @DatabaseField(columnName = "lza_pad_news_type")
    private String type;

    @DatabaseField(columnName = "lza_pad_news_img1")
    private String Img1;

    @DatabaseField(columnName = "lza_pad_news_img2")
    private String Img2;

    @DatabaseField(columnName = "lza_pad_news_img3")
    private String Img3;

    @DatabaseField(columnName = "lza_pad_news_img_path")
    private String imgPath;

    @DatabaseField(columnName = "lza_pad_news_update_date")
    private long updateDate;

    @DatabaseField(columnName = "lza_pad_news_update_count")
    private int updateCount;

    @DatabaseField(columnName = "lza_pad_news_click_count")
    private int clickCount;

    public News() {}

    public int getInnerId() {
        return innerId;
    }

    public void setInnerId(int innerId) {
        this.innerId = innerId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getSchool_id() {
        return school_id;
    }

    public void setSchool_id(int school_id) {
        this.school_id = school_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImg1() {
        return Img1;
    }

    public void setImg1(String img1) {
        Img1 = img1;
    }

    public String getImg2() {
        return Img2;
    }

    public void setImg2(String img2) {
        Img2 = img2;
    }

    public String getImg3() {
        return Img3;
    }

    public void setImg3(String img3) {
        Img3 = img3;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public long getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(long updateDate) {
        this.updateDate = updateDate;
    }

    public int getUpdateCount() {
        return updateCount;
    }

    public void setUpdateCount(int updateCount) {
        this.updateCount = updateCount;
    }

    public int getClickCount() {
        return clickCount;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(author);
        dest.writeString(pubdate);
        dest.writeString(type);
        dest.writeString(url);
        dest.writeInt(school_id);
        dest.writeString(content);
        dest.writeString(Img1);
        dest.writeString(Img2);
        dest.writeString(Img3);
    }

    public static final Creator<News> CREATOR = new Creator<News>() {
        @Override
        public News createFromParcel(Parcel source) {
            News news = new News();
            news.id = source.readInt();
            news.author = source.readString();
            news.pubdate = source.readString();
            news.type = source.readString();
            news.url = source.readString();
            news.school_id = source.readInt();
            news.content = source.readString();
            news.Img1 = source.readString();
            news.Img2 = source.readString();
            news.Img3 = source.readString();

            return news;
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };
}
