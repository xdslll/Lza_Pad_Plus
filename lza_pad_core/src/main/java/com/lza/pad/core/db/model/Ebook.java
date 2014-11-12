package com.lza.pad.core.db.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by xiads on 14-9-3.
 */
@DatabaseTable(tableName = "lza_pad_ebook")
public class Ebook implements Parcelable {

    public static final String TABLE_NAME = "lza_pad_ebook";
    public static final String _INNER_ID = "inner_id";
    public static final String _ID = "id";
    public static final String _BOOK_ID = "book_id";
    public static final String _NAME = "book_name";
    public static final String _NAMEPY = "book_name_py";
    public static final String _ISBN = "book_isbn";
    public static final String _AUTHOR = "book_author";
    public static final String _TRANSLATOR = "book_translator";

    public static final String _PRESS = "book_press";
    public static final String _ADDRESS = "book_address";
    public static final String _PUBDATE = "book_pubdate";
    public static final String _PAGES = "book_pages";
    public static final String _SUBJECT = "book_subject";
    public static final String _ZTF = "book_ztf";
    public static final String _ABSTRACT = "book_abstract";
    public static final String _XK = "book_xk";
    public static final String _PX = "book_px";
    public static final String _IMG = "book_img";
    public static final String _IMG_PATH = "book_img_path";
    public static final String _UPDATE_DATE = "book_update_date";
    public static final String _UPDATE_COUNT = "book_update_count";
    public static final String _CLICK_COUNT = "book_click_count";
    public static final String _TYPE = "book_type";

    @DatabaseField(columnName = "inner_id", generatedId = true, index = true)
    private int innerId;

    @DatabaseField(index = true)
    private int id;

    @DatabaseField(columnName = "book_id", index = true)
    private int bookId;

    @DatabaseField(columnName = "book_name")
    private String name;

    @DatabaseField(columnName = "book_name_py")
    private String namePy;

    @DatabaseField(columnName = "book_isbn")
    private String isbn;

    @DatabaseField(columnName = "book_author")
    private String author;

    @DatabaseField(columnName = "book_translator")
    private String translator;

    @DatabaseField(columnName = "book_press")
    private String press;

    @DatabaseField(columnName = "book_address")
    private String address;

    @DatabaseField(columnName = "book_pubdate")
    private String pubdate;

    @DatabaseField(columnName = "book_pages")
    private String pages;

    @DatabaseField(columnName = "book_subject")
    private String subject;

    @DatabaseField(columnName = "book_ztf")
    private String ztf;

    @DatabaseField(columnName = "book_abstract")
    @SerializedName(value = "abstract")
    private String abstractStr;

    @DatabaseField(columnName = "book_xk")
    private String xk;

    @DatabaseField(columnName = "book_px")
    private String px;

    @DatabaseField(columnName = "book_img")
    @SerializedName(value = "img")
    private String imgUrl;

    @DatabaseField(columnName = "book_img_path")
    private String imgPath;

    @DatabaseField(columnName = "book_update_date")
    private long updateDate;

    @DatabaseField(columnName = "book_update_count")
    private int updateCount;

    @DatabaseField(columnName = "book_click_count")
    private int clickCount;

    @DatabaseField(columnName = "book_type", index = true)
    private String type;

    /**
     * EbookContent专用
     */
    private int page;

    /**
     * Journals专用
     */
    private String title_c;

    private String title_e;

    private String url;

    private String company;

    private String tel;

    private String email;

    private String zq;

    private String lan;

    private String kb;

    private String issn;

    private String xn;

    private String post;

    private String old_name;

    private String creat_pubdate;

    private String database_qg;

    private String hx;

    private String gg;

    private String sx;

    private String qkImg;

    private String qkImgPath;

    /**
     * JournalsContent专用
     */
    private String entitle;

    private String enauthor;

    private String jigou;

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

    private String title;

    /**
     * 到馆新书
     */
    private int school_id;

    private String smallImg;

    private String bigImg;

    private String content;

    private String publisher;

    private String authorC;

    private String mr;

    /**
     * 新闻参数
     */
    private String Img1;

    private String Img2;

    private String Img3;

    /**
     * 搜索参数
     */
    private String marc_no;

    private String hao;

    private String fb1;

    private String fb2;

    private int xh;

    /**
     * 所属页数
     */
    //@DatabaseField
    //private String pageNumber;

    /**
     * 所属学科
     */
    private String value;
    private String schoolId;
    private String xk_type;

    public Ebook() {}

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

    public String getNamePy() {
        return namePy;
    }

    public void setNamePy(String namePy) {
        this.namePy = namePy;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTranslator() {
        return translator;
    }

    public void setTranslator(String translator) {
        this.translator = translator;
    }

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getZtf() {
        return ztf;
    }

    public void setZtf(String ztf) {
        this.ztf = ztf;
    }

    public String getAbstractStr() {
        return abstractStr;
    }

    public void setAbstractStr(String abstractStr) {
        this.abstractStr = abstractStr;
    }

    public String getXk() {
        return xk;
    }

    public void setXk(String xk) {
        this.xk = xk;
    }

    public String getPx() {
        return px;
    }

    public void setPx(String px) {
        this.px = px;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public int getClickCount() {
        return clickCount;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getTitle_c() {
        return title_c;
    }

    public void setTitle_c(String title_c) {
        this.title_c = title_c;
    }

    public String getTitle_e() {
        return title_e;
    }

    public void setTitle_e(String title_e) {
        this.title_e = title_e;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getZq() {
        return zq;
    }

    public void setZq(String zq) {
        this.zq = zq;
    }

    public String getLan() {
        return lan;
    }

    public void setLan(String lan) {
        this.lan = lan;
    }

    public String getKb() {
        return kb;
    }

    public void setKb(String kb) {
        this.kb = kb;
    }

    public String getIssn() {
        return issn;
    }

    public void setIssn(String issn) {
        this.issn = issn;
    }

    public String getXn() {
        return xn;
    }

    public void setXn(String xn) {
        this.xn = xn;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getOld_name() {
        return old_name;
    }

    public void setOld_name(String old_name) {
        this.old_name = old_name;
    }

    public String getCreat_pubdate() {
        return creat_pubdate;
    }

    public void setCreat_pubdate(String creat_pubdate) {
        this.creat_pubdate = creat_pubdate;
    }

    public String getDatabase_qg() {
        return database_qg;
    }

    public void setDatabase_qg(String database_qg) {
        this.database_qg = database_qg;
    }

    public String getHx() {
        return hx;
    }

    public void setHx(String hx) {
        this.hx = hx;
    }

    public String getGg() {
        return gg;
    }

    public void setGg(String gg) {
        this.gg = gg;
    }

    public String getSx() {
        return sx;
    }

    public void setSx(String sx) {
        this.sx = sx;
    }

    public String getQkImg() {
        return qkImg;
    }

    public void setQkImg(String qkImg) {
        this.qkImg = qkImg;
    }

    public String getQkImgPath() {
        return qkImgPath;
    }

    public void setQkImgPath(String qkImgPath) {
        this.qkImgPath = qkImgPath;
    }

    public String getEntitle() {
        return entitle;
    }

    public void setEntitle(String entitle) {
        this.entitle = entitle;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSchool_id() {
        return school_id;
    }

    public void setSchool_id(int school_id) {
        this.school_id = school_id;
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

    public String getMarc_no() {
        return marc_no;
    }

    public void setMarc_no(String marc_no) {
        this.marc_no = marc_no;
    }

    public String getHao() {
        return hao;
    }

    public void setHao(String hao) {
        this.hao = hao;
    }

    public String getFb1() {
        return fb1;
    }

    public void setFb1(String fb1) {
        this.fb1 = fb1;
    }

    public String getFb2() {
        return fb2;
    }

    public void setFb2(String fb2) {
        this.fb2 = fb2;
    }

    public int getXh() {
        return xh;
    }

    public void setXh(int xh) {
        this.xh = xh;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(bookId);
        dest.writeString(name);
        dest.writeString(namePy);
        dest.writeString(isbn);
        dest.writeString(author);
        dest.writeString(translator);
        dest.writeString(press);
        dest.writeString(address);
        dest.writeString(pubdate);
        dest.writeString(pages);
        dest.writeString(subject);
        dest.writeString(ztf);
        dest.writeString(abstractStr);
        dest.writeString(xk);
        dest.writeString(px);
        dest.writeString(imgUrl);
        dest.writeString(imgPath);
        dest.writeLong(updateDate);
        dest.writeInt(updateCount);
        dest.writeInt(clickCount);
        dest.writeString(type);

        dest.writeString(title_c);
        dest.writeString(title_e);
        dest.writeString(url);
        dest.writeString(company);
        dest.writeString(tel);
        dest.writeString(email);
        dest.writeString(zq);
        dest.writeString(lan);
        dest.writeString(kb);
        dest.writeString(issn);
        dest.writeString(xn);
        dest.writeString(post);
        dest.writeString(old_name);
        dest.writeString(creat_pubdate);
        dest.writeString(database_qg);
        dest.writeString(hx);
        dest.writeString(gg);
        dest.writeString(sx);
        dest.writeString(qkImg);
        dest.writeString(qkImgPath);

        dest.writeInt(school_id);
        dest.writeString(smallImg);
        dest.writeString(bigImg);
        dest.writeString(content);
        dest.writeString(publisher);
        dest.writeString(authorC);
        dest.writeString(mr);

        dest.writeString(Img1);
        dest.writeString(Img2);
        dest.writeString(Img3);

        dest.writeString(marc_no);
        dest.writeString(hao);
        dest.writeString(fb1);
        dest.writeString(fb2);
        dest.writeInt(xh);
    }

    public static final Creator<Ebook> CREATOR = new Creator<Ebook>() {
        @Override
        public Ebook createFromParcel(Parcel source) {
            Ebook ebook = new Ebook();
            ebook.id = source.readInt();
            ebook.bookId = source.readInt();
            ebook.name = source.readString();
            ebook.namePy = source.readString();
            ebook.isbn = source.readString();
            ebook.author = source.readString();
            ebook.translator = source.readString();
            ebook.press = source.readString();
            ebook.address = source.readString();
            ebook.pubdate = source.readString();
            ebook.pages = source.readString();
            ebook.subject = source.readString();
            ebook.ztf = source.readString();
            ebook.abstractStr = source.readString();
            ebook.xk = source.readString();
            ebook.px = source.readString();
            ebook.imgUrl = source.readString();
            ebook.imgPath = source.readString();
            ebook.updateDate = source.readLong();
            ebook.updateCount = source.readInt();
            ebook.clickCount = source.readInt();
            ebook.type = source.readString();

            ebook.title_c = source.readString();
            ebook.title_e = source.readString();
            ebook.url = source.readString();
            ebook.company = source.readString();
            ebook.tel = source.readString();
            ebook.email = source.readString();
            ebook.zq = source.readString();
            ebook.lan = source.readString();
            ebook.kb = source.readString();
            ebook.issn = source.readString();
            ebook.xn = source.readString();
            ebook.post = source.readString();
            ebook.old_name = source.readString();
            ebook.creat_pubdate = source.readString();
            ebook.database_qg = source.readString();
            ebook.hx = source.readString();
            ebook.gg = source.readString();
            ebook.sx = source.readString();
            ebook.qkImg = source.readString();
            ebook.qkImgPath = source.readString();

            ebook.school_id = source.readInt();
            ebook.smallImg = source.readString();
            ebook.bigImg = source.readString();
            ebook.content = source.readString();
            ebook.publisher = source.readString();
            ebook.authorC = source.readString();
            ebook.mr = source.readString();

            ebook.Img1 = source.readString();
            ebook.Img2 = source.readString();
            ebook.Img3 = source.readString();

            ebook.marc_no = source.readString();
            ebook.hao = source.readString();
            ebook.fb1 = source.readString();
            ebook.fb2 = source.readString();
            ebook.xh = source.readInt();

            return ebook;
        }

        @Override
        public Ebook[] newArray(int size) {
            return new Ebook[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        boolean flag = false;
        if (o != null && o instanceof Ebook) {
            int ebookId = ((Ebook) o).getBookId();
            String ebookImgUrl = ((Ebook) o).getImgUrl();
            String ebookName = ((Ebook) o).getName();
            if (ebookId == bookId &&
                    ebookImgUrl.equals(imgUrl) &&
                    ebookName.equals(name)) {
                flag = true;
            }
        }
        return flag;
    }
}
