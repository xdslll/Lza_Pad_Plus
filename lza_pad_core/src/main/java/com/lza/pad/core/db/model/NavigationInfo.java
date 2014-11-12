package com.lza.pad.core.db.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 底部导航栏相关的数据
 *
 * @author xiads
 * @Date 14-9-10.
 */
@DatabaseTable(tableName = "lza_pad_navigation_info")
public class NavigationInfo implements Parcelable, Cloneable {

    public static final String _ID = "id";
    public static final String _SORT_ID = "navigation_sort_id";
    public static final String _SORT = "sort";
    public static final String _SCREEN_SAVER_TIME = "screenSaverTime";
    public static final String IS_CLOSED = "navigation_is_closed";
    public static final String IS_ACTIVATED = "navigation_is_activated";
    public static final String _API_URL = "navigation_api_url";
    public static final String _CITY_CODE = "navigation_api_city_code";
    public static final String _DEVICE_CODE = "navigation_api_device_code";
    public static final String _SCHOOL_ID = "navigation_api_school_id_par";
    public static final String _CONTROL = "navigation_api_control_par";
    public static final String _ACTION = "navigation_api_action_par";
    public static final String _API_CITY_CODE = "navigation_api_city_code";
    public static final String _API_DEVICE_CODE = "navigation_api_device_code";
    public static final String _API_SCHOOL_ID = "navigation_api_school_id_par";
    public static final String _API_IMG_SCALING = "navigation_img_scaling";
    public static final String _API_RUNNING_MODE = "navigation_running_mode";
    public static final String _API_VERTICAL_OFFSET = "navigation_vertical_offset";

    public static final String IS_CLOSED_OPEN_VALUE = "0";
    public static final String IS_ACTIVATED_ACTIVATED_VALUE = "1";
    public static final float DEFAULT_IMG_SCALING = 0.8f;

    public static final int RUNNING_MODE_DB = 0;
    public static final int RUNNING_MODE_NET = 1;

    @DatabaseField(generatedId = true)
    private int id;

    /**
     * 导航栏的名称
     */
    @DatabaseField(columnName = "navigation_name")
    private String name;

    /**
     * 导航栏对应的图片资源名称
     */
    @DatabaseField(columnName = "navigation_res_name")
    private String resName;

    /**
     * 专门用于排序的字段
     */
    @DatabaseField(columnName = "navigation_sort_id")
    private int sortId;

    /**
     * 对应的Activity的路径
     */
    @DatabaseField(columnName = "navigation_activity_path")
    private String activityPath;

    /**
     * 是否包含标题栏
     */
    @DatabaseField(columnName = "navigation_has_title_bar")
    private int hasTitleBar;

    /**
     * 标题栏是否包含功能按钮
     */
    @DatabaseField(columnName = "navigation_has_title_button")
    private int hasTitleButton;

    /**
     * 每页显示的数据行数
     */
    @DatabaseField(columnName = "navigation_data_row_number")
    private int dataRowNumber;

    /**
     * 每行显示的数据量
     */
    @DatabaseField(columnName = "navigation_data_column_number")
    private int dataColumnNumber;

    /**
     * API中的control参数,用于获取数据源
     */
    @DatabaseField(columnName = "navigation_api_control_par")
    private String apiControlPar;

    /**
     * API中的action参数,用于获取数据源
     */
    @DatabaseField(columnName = "navigation_api_action_par")
    private String apiActionPar;

    /**
     * API中的page参数,用于定位当前页码
     */
    @DatabaseField(columnName = "navigation_api_page_par")
    private int apiPagePar;

    /**
     * API中的pagesize参数,用于确定每页的数据量
     */
    @DatabaseField(columnName = "navigation_api_page_size_par")
    private int apiPageSizePar;

    @DatabaseField(columnName = "navigation_api_school_id_par")
    private int apiSchoolIdPar;

    /**
     * API的URL
     */
    @DatabaseField(columnName = "navigation_api_url")
    private String apiUrl;

    /**
     * API的book_id参数
     */
    @DatabaseField(columnName = "navigation_api_book_id")
    private int apiBookId;

    /**
     * 当前模块是否关闭
     */
    @DatabaseField(columnName = "navigation_is_closed")
    private int isClosed;

    /**
     * 当前模式是否激活
     */
    @DatabaseField(columnName = "navigation_is_activated")
    private int isActivated;

    /**
     * API的学科参数
     */
    @DatabaseField(columnName = "navigation_api_xk")
    private String apiXkPar;

    /**
     * API的热门图书编号
     */
    @DatabaseField(columnName = "navigation_api_marc_no")
    private String apiMarcNoPar;

    /**
     * API的热门图书内容ACT
     */
    @DatabaseField(columnName = "navigation_api_act")
    private String apiActPar;

    /**
     * API的新闻类型参数
     */
    @DatabaseField(columnName = "navigation_api_type")
    private int apiTypePar;

    /**
     * API的新闻数据量参数
     */
    @DatabaseField(columnName = "navigation_api_return_num")
    private String apiReturnNum;

    @DatabaseField(columnName = "navigation_api_last_id")
    private String apiLastId;

    @DatabaseField(columnName = "navigation_api_city_code")
    private int apiCityCode;

    @DatabaseField(columnName = "navigation_api_device_code")
    private int apiDeviceCode;

    /**
     * 图片缩放比例
     */
    @DatabaseField(columnName = "navigation_img_scaling")
    private float imgScaling;

    /**
     * 运行模式
     * 0 - 使用数据库
     * 1 - 使用网络
     */
    @DatabaseField(columnName = "navigation_running_mode")
    private int runningMode;

    /**
     * 书架的垂直偏移量
     */
    @DatabaseField(columnName = "navigation_vertical_offset", defaultValue = "100")
    private int verticalOffset;

    /**
     * 搜索关键字
     */
    private String searchKeyWord;
    /**
     * 搜索类型
     */
    private int searchType;
    /**
     * 关键字类型
     */
    private int sourceType;

    /**
     * 记录书籍排序的字段
     * 2 - 根据id排序
     * 1 - 根据bookId排序
     * 0 - 根据内置id排序
     */
    @DatabaseField
    private int sort;
    public static final int SORT_BY_ID = 2;
    public static final int SORT_BY_BOOK_ID = 1;
    public static final int SORT_BY_INNER_ID = 0;

    /**
     * 记录屏保时间
     */
    @DatabaseField
    private int screenSaverTime;


    public NavigationInfo() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public int getSortId() {
        return sortId;
    }

    public void setSortId(int sortId) {
        this.sortId = sortId;
    }

    public String getActivityPath() {
        return activityPath;
    }

    public void setActivityPath(String activityPath) {
        this.activityPath = activityPath;
    }

    public int getHasTitleButton() {
        return hasTitleButton;
    }

    public void setHasTitleButton(int hasTitleButton) {
        this.hasTitleButton = hasTitleButton;
    }

    public int getHasTitleBar() {
        return hasTitleBar;
    }

    public void setHasTitleBar(int hasTitleBar) {
        this.hasTitleBar = hasTitleBar;
    }

    public int getDataRowNumber() {
        return dataRowNumber;
    }

    public void setDataRowNumber(int dataRowNumber) {
        this.dataRowNumber = dataRowNumber;
    }

    public int getDataColumnNumber() {
        return dataColumnNumber;
    }

    public void setDataColumnNumber(int dataColumnNumber) {
        this.dataColumnNumber = dataColumnNumber;
    }

    public String getApiControlPar() {
        return apiControlPar;
    }

    public void setApiControlPar(String apiControlPar) {
        this.apiControlPar = apiControlPar;
    }

    public String getApiActionPar() {
        return apiActionPar;
    }

    public void setApiActionPar(String apiActionPar) {
        this.apiActionPar = apiActionPar;
    }

    public int getApiPagePar() {
        return apiPagePar;
    }

    public void setApiPagePar(int apiPagePar) {
        this.apiPagePar = apiPagePar;
    }

    public int getApiPageSizePar() {
        return apiPageSizePar;
    }

    public void setApiPageSizePar(int apiPageSizePar) {
        this.apiPageSizePar = apiPageSizePar;
    }

    public int getApiSchoolIdPar() {
        return apiSchoolIdPar;
    }

    public void setApiSchoolIdPar(int apiSchoolIdPar) {
        this.apiSchoolIdPar = apiSchoolIdPar;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public int getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(int isClosed) {
        this.isClosed = isClosed;
    }

    public int getIsActivated() {
        return isActivated;
    }

    public void setIsActivated(int isActivated) {
        this.isActivated = isActivated;
    }

    public int getApiBookId() {
        return apiBookId;
    }

    public void setApiBookId(int apiBookId) {
        this.apiBookId = apiBookId;
    }

    public String getApiXkPar() {
        return apiXkPar;
    }

    public void setApiXkPar(String apiXkPar) {
        this.apiXkPar = apiXkPar;
    }

    public String getApiMarcNoPar() {
        return apiMarcNoPar;
    }

    public void setApiMarcNoPar(String apiMarcNoPar) {
        this.apiMarcNoPar = apiMarcNoPar;
    }

    public String getApiActPar() {
        return apiActPar;
    }

    public void setApiActPar(String apiActPar) {
        this.apiActPar = apiActPar;
    }

    public int getApiTypePar() {
        return apiTypePar;
    }

    public void setApiTypePar(int apiTypePar) {
        this.apiTypePar = apiTypePar;
    }

    public String getApiReturnNum() {
        return apiReturnNum;
    }

    public void setApiReturnNum(String apiReturnNum) {
        this.apiReturnNum = apiReturnNum;
    }

    public String getApiLastId() {
        return apiLastId;
    }

    public void setApiLastId(String apiLastId) {
        this.apiLastId = apiLastId;
    }

    public int getApiCityCode() {
        return apiCityCode;
    }

    public void setApiCityCode(int apiCityCode) {
        this.apiCityCode = apiCityCode;
    }

    public int getApiDeviceCode() {
        return apiDeviceCode;
    }

    public void setApiDeviceCode(int apiDeviceCode) {
        this.apiDeviceCode = apiDeviceCode;
    }

    public float getImgScaling() {
        return imgScaling;
    }

    public void setImgScaling(float imgScaling) {
        this.imgScaling = imgScaling;
    }

    public int getRunningMode() {
        return runningMode;
    }

    public void setRunningMode(int runningMode) {
        this.runningMode = runningMode;
    }

    public int getVerticalOffset() {
        return verticalOffset;
    }

    public void setVerticalOffset(int verticalOffset) {
        this.verticalOffset = verticalOffset;
    }

    public String getSearchKeyWord() {
        return searchKeyWord;
    }

    public void setSearchKeyWord(String searchKeyWord) {
        this.searchKeyWord = searchKeyWord;
    }

    public int getSearchType() {
        return searchType;
    }

    public void setSearchType(int searchType) {
        this.searchType = searchType;
    }

    public int getSourceType() {
        return sourceType;
    }

    public void setSourceType(int sourceType) {
        this.sourceType = sourceType;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getScreenSaverTime() {
        return screenSaverTime;
    }

    public void setScreenSaverTime(int screenSaverTime) {
        this.screenSaverTime = screenSaverTime;
    }

    @Override
    public NavigationInfo clone() {
        NavigationInfo o = null;
        try {
            return (NavigationInfo) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return o;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(resName);
        dest.writeInt(sortId);
        dest.writeString(activityPath);
        dest.writeInt(hasTitleBar);
        dest.writeInt(hasTitleButton);
        dest.writeInt(dataRowNumber);
        dest.writeInt(dataColumnNumber);
        dest.writeString(apiControlPar);
        dest.writeString(apiActionPar);
        dest.writeInt(apiPagePar);
        dest.writeInt(apiPageSizePar);
        dest.writeInt(apiSchoolIdPar);
        dest.writeString(apiUrl);
        dest.writeInt(isClosed);
        dest.writeInt(isActivated);
        dest.writeInt(apiBookId);
        dest.writeString(apiXkPar);
        dest.writeString(apiMarcNoPar);
        dest.writeString(apiActPar);
        dest.writeInt(apiTypePar);
        dest.writeString(apiReturnNum);
        dest.writeString(apiLastId);
        dest.writeInt(apiCityCode);
        dest.writeInt(apiDeviceCode);
        dest.writeFloat(imgScaling);
        dest.writeInt(runningMode);
        dest.writeInt(verticalOffset);
        dest.writeString(searchKeyWord);
        dest.writeInt(searchType);
        dest.writeInt(sourceType);
        dest.writeInt(sort);
        dest.writeInt(screenSaverTime);
    }

    public static final Creator<NavigationInfo> CREATOR =
            new Creator<NavigationInfo>() {
                @Override
                public NavigationInfo createFromParcel(Parcel source) {
                    NavigationInfo navInfo = new NavigationInfo();
                    navInfo.id = source.readInt();
                    navInfo.name = source.readString();
                    navInfo.resName = source.readString();
                    navInfo.sortId = source.readInt();
                    navInfo.activityPath = source.readString();
                    navInfo.hasTitleBar = source.readInt();
                    navInfo.hasTitleButton = source.readInt();
                    navInfo.dataRowNumber = source.readInt();
                    navInfo.dataColumnNumber = source.readInt();
                    navInfo.apiControlPar = source.readString();
                    navInfo.apiActionPar = source.readString();
                    navInfo.apiPagePar = source.readInt();
                    navInfo.apiPageSizePar = source.readInt();
                    navInfo.apiSchoolIdPar = source.readInt();
                    navInfo.apiUrl = source.readString();
                    navInfo.isClosed = source.readInt();
                    navInfo.isActivated = source.readInt();
                    navInfo.apiBookId = source.readInt();
                    navInfo.apiXkPar = source.readString();
                    navInfo.apiMarcNoPar = source.readString();
                    navInfo.apiActPar = source.readString();
                    navInfo.apiTypePar = source.readInt();
                    navInfo.apiReturnNum = source.readString();
                    navInfo.apiLastId = source.readString();
                    navInfo.apiCityCode = source.readInt();
                    navInfo.apiDeviceCode = source.readInt();
                    navInfo.imgScaling = source.readFloat();
                    navInfo.runningMode = source.readInt();
                    navInfo.verticalOffset = source.readInt();
                    navInfo.searchKeyWord = source.readString();
                    navInfo.searchType = source.readInt();
                    navInfo.sourceType = source.readInt();
                    navInfo.sort = source.readInt();
                    navInfo.screenSaverTime = source.readInt();

                    return navInfo;
                }

                @Override
                public NavigationInfo[] newArray(int size) {
                    return new NavigationInfo[size];
                }
            };

}
