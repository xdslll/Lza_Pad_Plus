package com.lza.pad.core.db.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 14-9-19.
 */
@DatabaseTable(tableName = "lza_pad_hot_book_content")
public class HotBookContent {

    public static final String TABLE_NAME = "lza_pad_hot_book_content";

    public static final String _TITLE = "hot_book_content_title";
    public static final String _AUTHOR = "hot_book_content_author";
    public static final String _PUBISHER = "hot_book_content_pubisher";
    public static final String _CNT1 = "hot_book_content_cnt1";
    public static final String _CNT2 = "hot_book_content_cnt2";
    public static final String _MARC_NO = "hot_book_content_marc_no";

    @DatabaseField(generatedId = true, index = true)
    private int inner_id;

    @DatabaseField(columnName = "hot_book_content_title")
    private String title;

    @DatabaseField(columnName = "hot_book_content_author")
    private String author;

    @DatabaseField(columnName = "hot_book_content_pubisher")
    private String pubisher;

    @DatabaseField(columnName = "hot_book_content_cnt1")
    private String cnt1;

    @DatabaseField(columnName = "hot_book_content_cnt2")
    private String cnt2;

    @DatabaseField(columnName = "hot_book_content_marc_no")
    private String marc_no;

    public HotBookContent() {}

    public int getInner_id() {
        return inner_id;
    }

    public void setInner_id(int inner_id) {
        this.inner_id = inner_id;
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

    public String getPubisher() {
        return pubisher;
    }

    public void setPubisher(String pubisher) {
        this.pubisher = pubisher;
    }

    public String getCnt1() {
        return cnt1;
    }

    public void setCnt1(String cnt1) {
        this.cnt1 = cnt1;
    }

    public String getCnt2() {
        return cnt2;
    }

    public void setCnt2(String cnt2) {
        this.cnt2 = cnt2;
    }

    public String getMarc_no() {
        return marc_no;
    }

    public void setMarc_no(String marc_no) {
        this.marc_no = marc_no;
    }
}
