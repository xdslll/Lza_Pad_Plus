package com.lza.pad.core.db.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by xiads on 14-9-3.
 */
@DatabaseTable(tableName = "lza_pad_ebook_content")
public class EbookContent implements Parcelable {

    public static final String TABLE_NAME = "lza_pad_ebook_content";
    public static final String _INNER_ID = "inner_id";
    public static final String _ID = "id";
    public static final String _BOOK_ID = "book_id";
    public static final String _NAME = "book_name";
    public static final String _PAGE = "book_page";

    @DatabaseField(generatedId = true, columnName = "inner_id")
    private int innerId;

    @DatabaseField(index = true)
    private int id;

    @DatabaseField(columnName = "book_id", index = true)
    private int bookId;

    @DatabaseField(columnName = "book_name")
    private String name;

    @DatabaseField(columnName = "book_page")
    private int page;

    private int qkId;

    private String title;

    private String entitle;

    private String author;

    private String enauthor;

    private String jigou;

    @SerializedName(value = "abstract")
    private String abstractStr;

    private String enabstract;

    private String keywords;

    private String enkeywords;

    private String articlefrom;

    private String kan_name;

    private String enkanname;

    private String year;

    private String qi;

    private String doi;

    private String fenleihao;

    private String total_down;

    private String test_url;

    private String fname;

    private String kancode;

    private String pubdate;

    private String pubisher;

    private String cnt1;

    private String cnt2;

    private String marc_no;

    public EbookContent() {}

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

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getQkId() {
        return qkId;
    }

    public void setQkId(int qkId) {
        this.qkId = qkId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEntitle() {
        return entitle;
    }

    public void setEntitle(String entitle) {
        this.entitle = entitle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getEnauthor() {
        return enauthor;
    }

    public void setEnauthor(String enauthor) {
        this.enauthor = enauthor;
    }

    public String getJigou() {
        return jigou;
    }

    public void setJigou(String jigou) {
        this.jigou = jigou;
    }

    public String getAbstractStr() {
        return abstractStr;
    }

    public void setAbstractStr(String abstractStr) {
        this.abstractStr = abstractStr;
    }

    public String getEnabstract() {
        return enabstract;
    }

    public void setEnabstract(String enabstract) {
        this.enabstract = enabstract;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getEnkeywords() {
        return enkeywords;
    }

    public void setEnkeywords(String enkeywords) {
        this.enkeywords = enkeywords;
    }

    public String getArticlefrom() {
        return articlefrom;
    }

    public void setArticlefrom(String articlefrom) {
        this.articlefrom = articlefrom;
    }

    public String getKan_name() {
        return kan_name;
    }

    public void setKan_name(String kan_name) {
        this.kan_name = kan_name;
    }

    public String getEnkanname() {
        return enkanname;
    }

    public void setEnkanname(String enkanname) {
        this.enkanname = enkanname;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getQi() {
        return qi;
    }

    public void setQi(String qi) {
        this.qi = qi;
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public String getFenleihao() {
        return fenleihao;
    }

    public void setFenleihao(String fenleihao) {
        this.fenleihao = fenleihao;
    }

    public String getTotal_down() {
        return total_down;
    }

    public void setTotal_down(String total_down) {
        this.total_down = total_down;
    }

    public String getTest_url() {
        return test_url;
    }

    public void setTest_url(String test_url) {
        this.test_url = test_url;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getKancode() {
        return kancode;
    }

    public void setKancode(String kancode) {
        this.kancode = kancode;
    }

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(innerId);
        dest.writeInt(id);
        dest.writeInt(bookId);
        dest.writeString(name);
        dest.writeInt(page);
    }

    public static final Creator<EbookContent> CREATOR = new Creator<EbookContent>() {
        @Override
        public EbookContent createFromParcel(Parcel source) {
            EbookContent ebook = new EbookContent();
            ebook.innerId = source.readInt();
            ebook.id = source.readInt();
            ebook.bookId = source.readInt();
            ebook.name = source.readString();
            ebook.page = source.readInt();
            return ebook;
        }

        @Override
        public EbookContent[] newArray(int size) {
            return new EbookContent[size];
        }
    };

    @Override
    public String toString() {
        return "EbookContent{" +
                "innerId=" + innerId +
                ", id=" + id +
                ", bookId=" + bookId +
                ", name='" + name + '\'' +
                ", page=" + page +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        boolean flag = false;
        if (o != null && o instanceof EbookContent) {
            int ebookId = ((EbookContent) o).getBookId();
            String ebookName = ((EbookContent) o).getName();
            int page = ((EbookContent) o).getPage();
            if (ebookId == bookId &&
                    ebookName.equals(name) &&
                    page == page) {
                flag = true;
            }
        }
        return flag;
    }

}
