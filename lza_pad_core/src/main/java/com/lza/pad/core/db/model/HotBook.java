package com.lza.pad.core.db.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 14-9-20.
 */
@DatabaseTable(tableName = "lza_pad_hot_book")
public class HotBook {

    public static final String TABLE_NAME = "lza_pad_hot_book";
    public static final String _ID = "hot_book_id";
    public static final String _XK = "hot_book_xk";
    public static final String _TYPE = "hot_bok_type";

    @DatabaseField(generatedId = true)
    private int innerId;

    @DatabaseField(columnName = "hot_book_id", index = true)
    private int id;

    @DatabaseField(columnName = "hot_book_school_id")
    private int school_id;

    @DatabaseField(columnName = "hot_book_title")
    private String title;

    @DatabaseField(columnName = "hot_book_author")
    private String author;

    @DatabaseField(columnName = "hot_book_isbn")
    private String isbn;

    @DatabaseField(columnName = "hot_book_url")
    private String url;

    @DatabaseField(columnName = "hot_book_xk")
    private String xk;

    @DatabaseField(columnName = "hot_book_small_img")
    private String smallImg;

    @DatabaseField(columnName = "hot_book_big_img")
    private String bigImg;

    @DatabaseField(columnName = "hot_book_content")
    private String content;

    @DatabaseField(columnName = "hot_book_pubdate")
    private String pubdate;

    @DatabaseField(columnName = "hot_book_publisher")
    private String publisher;

    @DatabaseField(columnName = "hot_book_author_c")
    private String authorC;

    @DatabaseField(columnName = "hot_book_mr")
    private String mr;

    @DatabaseField(columnName = "hot_book_img_path")
    private String imgPath;

    @DatabaseField(columnName = "hot_bok_type")
    private String type;

    @DatabaseField(columnName = "hot_book_date")
    private long updateDate;

    @DatabaseField(columnName = "hot_book_count")
    private int updateCount;

    @DatabaseField(columnName = "hot_book_click")
    private int clickCount;

    public HotBook() {}

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

    public int getSchool_id() {
        return school_id;
    }

    public void setSchool_id(int school_id) {
        this.school_id = school_id;
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

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getXk() {
        return xk;
    }

    public void setXk(String xk) {
        this.xk = xk;
    }

    public String getSmallImg() {
        return smallImg;
    }

    public void setSmallImg(String smallImg) {
        this.smallImg = smallImg;
    }

    public String getBigImg() {
        return bigImg;
    }

    public void setBigImg(String bigImg) {
        this.bigImg = bigImg;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getAuthorC() {
        return authorC;
    }

    public void setAuthorC(String authorC) {
        this.authorC = authorC;
    }

    public String getMr() {
        return mr;
    }

    public void setMr(String mr) {
        this.mr = mr;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
    public boolean equals(Object o) {
        boolean flag = false;
        if (o != null && o instanceof HotBook) {
            String hotBookTitle = ((HotBook) o).getTitle();
            String hotBookImg = ((HotBook) o).getSmallImg();
            if (hotBookTitle.equals(this.title) &&
                    hotBookImg.equals(this.smallImg)) {
                flag = true;
            }
        }
        return flag;
    }
}
