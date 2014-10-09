package com.lza.pad.core.db.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by xiads on 14-9-3.
 */
@DatabaseTable(tableName = "lza_pad_journals")
public class Journals {

    public static final String TABLE_NAME = "lza_pad_journals";
    public static final String _ID = "jrl_id";
    public static final String _TITLE_C = "jrl_title_c";
    public static final String _TITLE_E = "jrl_title_e";
    public static final String _URL = "jrl_url";
    public static final String _COMPANY = "jrl_company";
    public static final String _ADDRESS = "jrl_address";
    public static final String _TEL = "jrl_tel";

    public static final String _EMAIL = "jrl_email";
    public static final String _ZQ = "jrl_zq";
    public static final String _PRESS = "jrl_press";
    public static final String _LAN = "jrl_lan";
    public static final String _KB = "jrl_kb";
    public static final String _ISSN = "jrl_issn";
    public static final String _XN = "jrl_xn";
    public static final String _POST = "jrl_post";
    public static final String _OLD_NAME = "jrl_old_name";
    public static final String _PUBDATE = "jrl_creat_pubdate";
    public static final String _DATEBASE_GQ = "jrl_database_qg";
    public static final String _HX = "jrl_hx";
    public static final String _GG = "jrl_gg";
    public static final String _SX = "jrl_sx";
    public static final String _QK_IMG = "jrl_qk_img";
    public static final String _TYPE = "jrl_type";


    @DatabaseField(generatedId = true, index = true)
    private int innerId;

    @DatabaseField(columnName = "jrl_id", index = true)
    private int id;

    @DatabaseField(columnName = "jrl_title_c")
    private String title_c;

    @DatabaseField(columnName = "jrl_title_e")
    private String title_e;

    @DatabaseField(columnName = "jrl_url")
    private String url;

    @DatabaseField(columnName = "jrl_company")
    private String company;

    @DatabaseField(columnName = "jrl_address")
    private String address;

    @DatabaseField(columnName = "jrl_tel")
    private String tel;

    @DatabaseField(columnName = "jrl_email")
    private String email;

    @DatabaseField(columnName = "jrl_zq")
    private String zq;

    @DatabaseField(columnName = "jrl_press")
    private String press;

    @DatabaseField(columnName = "jrl_lan")
    private String lan;

    @DatabaseField(columnName = "jrl_kb")
    private String kb;

    @DatabaseField(columnName = "jrl_issn")
    private String issn;

    @DatabaseField(columnName = "jrl_xn")
    private String xn;

    @DatabaseField(columnName = "jrl_post")
    private String post;

    @DatabaseField(columnName = "jrl_old_name")
    private String old_name;

    @DatabaseField(columnName = "jrl_creat_pubdate")
    private String creat_pubdate;

    @DatabaseField(columnName = "jrl_database_qg")
    private String database_qg;

    @DatabaseField(columnName = "jrl_hx")
    private String hx;

    @DatabaseField(columnName = "jrl_gg")
    private String gg;

    @DatabaseField(columnName = "jrl_sx")
    private String sx;

    @DatabaseField(columnName = "jrl_qk_img")
    private String qkImg;

    @DatabaseField(columnName = "jrl_qk_img_path")
    private String qkImgPath;

    @DatabaseField(columnName = "jrl_update_date")
    private Date updateDate;

    @DatabaseField(columnName = "jrl_update_count")
    private int updateCount;

    @DatabaseField(columnName = "jrl_click_count")
    private int clickCount;

    @DatabaseField(columnName = "jrl_type", index = true)
    private String type;

    public static final int TYPE_QK = 1;

    public Journals() {}

    @Override
    public String toString() {
        return new StringBuilder()
                .append("{id=").append(id)
                .append(",title_c=").append(title_c)
                .append(",title_e=").append(title_e)
                .append(",url=").append(url)
                .append(",img=").append(qkImg)
                .append(",imgPath=").append(qkImgPath).append("}")
                .toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClickCount() {
        return clickCount;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public int getUpdateCount() {
        return updateCount;
    }

    public void setUpdateCount(int updateCount) {
        this.updateCount = updateCount;
    }

    public void setPress(String press) {
        this.press = press;
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

    public static String getAddress() {
        return _ADDRESS;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getPress() {
        return press;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getInnerId() {
        return innerId;
    }

    public void setInnerId(int innerId) {
        this.innerId = innerId;
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
}
