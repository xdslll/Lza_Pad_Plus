package com.lza.pad.core.db.model;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 14-9-19.
 */
@DatabaseTable(tableName = "lza_pad_journals_content")
public class JournalsContent {

    public static final String TABLE_NAME = "lza_pad_journals_content";
    public static final String _QK_ID = "jrl_content_qk_id";
    public static final String _ID = "jrl_content_id";

    @DatabaseField(generatedId = true, index = true)
    private int inner_id;

    @DatabaseField(columnName = "jrl_content_qk_id", index = true)
    private int qkId;

    @DatabaseField(columnName = "jrl_content_id", index = true)
    private int id;

    @DatabaseField(columnName = "jrl_content_title")
    private String title;

    @DatabaseField(columnName = "jrl_content_entitle")
    private String entitle;

    @DatabaseField(columnName = "jrl_content_author")
    private String author;

    @DatabaseField(columnName = "jrl_content_enauthor")
    private String enauthor;

    @DatabaseField(columnName = "jrl_content_jigou")
    private String jigou;

    @DatabaseField(columnName = "jrl_content_abstract")
    @SerializedName(value = "abstract")
    private String abstractStr;

    @DatabaseField(columnName = "jrl_content_enabstract")
    private String enabstract;

    @DatabaseField(columnName = "jrl_content_keywords")
    private String keywords;

    @DatabaseField(columnName = "jrl_content_enkeywords")
    private String enkeywords;

    @DatabaseField(columnName = "jrl_content_articlefrom")
    private String articlefrom;

    @DatabaseField(columnName = "jrl_content_kan_name")
    private String kan_name;

    @DatabaseField(columnName = "jrl_content_enkanname")
    private String enkanname;

    @DatabaseField(columnName = "jrl_content_year")
    private String year;

    @DatabaseField(columnName = "jrl_content_qi")
    private String qi;

    @DatabaseField(columnName = "jrl_content_doi")
    private String doi;

    @DatabaseField(columnName = "jrl_content_fenleihao")
    private String fenleihao;

    @DatabaseField(columnName = "jrl_content_total_down")
    private String total_down;

    @DatabaseField(columnName = "jrl_content_test_url")
    private String test_url;

    @DatabaseField(columnName = "jrl_content_fname")
    private String fname;

    @DatabaseField(columnName = "jrl_content_kancode")
    private String kancode;

    @DatabaseField(columnName = "jrl_content_pubdate")
    private String pubdate;

    public JournalsContent() {}

    public int getInner_id() {
        return inner_id;
    }

    public void setInner_id(int inner_id) {
        this.inner_id = inner_id;
    }

    public int getQkId() {
        return qkId;
    }

    public void setQkId(int qkId) {
        this.qkId = qkId;
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
}
