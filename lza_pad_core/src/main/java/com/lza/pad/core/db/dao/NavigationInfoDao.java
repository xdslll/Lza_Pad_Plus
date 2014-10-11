package com.lza.pad.core.db.dao;

import android.content.Context;

import com.lza.pad.core.R;
import com.lza.pad.core.db.model.ApiParams;
import com.lza.pad.core.db.model.NavigationInfo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 14-9-10.
 */
public class NavigationInfoDao extends BaseDao<NavigationInfo, Integer> {

    private static NavigationInfoDao sNavigationInfoDao;

    public NavigationInfoDao() {
        super(NavigationInfo.class);
    }

    public static NavigationInfoDao getInstance() {
        if (sNavigationInfoDao == null)
            sNavigationInfoDao = new NavigationInfoDao();
        return sNavigationInfoDao;
    }

    /**
     * 初始化底部导航栏数据
     */
    public void initNavigationData(Context context) {
        List<NavigationInfo> navigationInfos = new ArrayList<NavigationInfo>();
        String[] navNames = context.getResources().getStringArray(R.array.navigation_init_names);
        String[] navIcons = context.getResources().getStringArray(R.array.navigation_init_icons);
        int[] navSort = context.getResources().getIntArray(R.array.navigation_init_sort);
        String[] navActivity = context.getResources().getStringArray(R.array.navigation_init_activity);
        int[] navHasTitleBar = context.getResources().getIntArray(R.array.navigation_init_has_title_bar);
        int[] navHasTitleButton = context.getResources().getIntArray(R.array.navigation_init_has_title_button);
        int[] navDataRowNumber = context.getResources().getIntArray(R.array.navigation_init_data_row_number);
        int[] navDataColumnNumber = context.getResources().getIntArray(R.array.navigation_init_data_each_row);
        String[] navApiControlPar = context.getResources().getStringArray(R.array.navigation_init_api_control_par);
        String[] navApiActionPar = context.getResources().getStringArray(R.array.navigation_init_api_action_par);
        int[] navApiPage = context.getResources().getIntArray(R.array.navigation_init_api_page_par);
        //int[] navApiPageSize = context.getResources().getIntArray(R.array.navigation_init_api_page_size_par);
        int[] navApiSchoolId = context.getResources().getIntArray(R.array.navigation_init_api_school_id_par);
        String[] navApiUrl = context.getResources().getStringArray(R.array.navigation_init_api_url);
        int[] navIsActivate = context.getResources().getIntArray(R.array.navigation_init_is_activate);
        int[] navIsClosed = context.getResources().getIntArray(R.array.navigation_init_is_closed);
        int[] navNewsType = context.getResources().getIntArray(R.array.navigation_init_api_news_type);
        String[] navImgScaling = context.getResources().getStringArray(R.array.navigation_init_img_scaling);
        int[] navRunningMode = context.getResources().getIntArray(R.array.navigation_init_running_mode);
        int[] navVerticalOffset = context.getResources().getIntArray(R.array.navigation_init_vertical_offset);

        int length = navNames.length;
        for (int i = 0; i < length; i++) {
            NavigationInfo ni = new NavigationInfo();
            ni.setName(navNames[i]);
            ni.setResName(navIcons[i]);
            ni.setSortId(navSort[i]);
            ni.setActivityPath(navActivity[i]);
            ni.setHasTitleBar(navHasTitleBar[i]);
            ni.setHasTitleButton(navHasTitleButton[i]);
            ni.setDataRowNumber(navDataRowNumber[i]);
            ni.setDataColumnNumber(navDataColumnNumber[i]);
            ni.setApiControlPar(navApiControlPar[i]);
            ni.setApiActionPar(navApiActionPar[i]);
            ni.setApiPagePar(navApiPage[i]);
            //ni.setApiPageSizePar(navApiPageSize[i]);
            ni.setApiPageSizePar(navDataRowNumber[i] * navDataColumnNumber[i]);
            ni.setApiSchoolIdPar(navApiSchoolId[i]);
            ni.setApiUrl(navApiUrl[i]);
            ni.setIsActivated(navIsActivate[i]);
            ni.setIsClosed(navIsClosed[i]);
            ni.setApiTypePar(navNewsType[i]);
            ni.setImgScaling(Float.valueOf(navImgScaling[i]));
            ni.setRunningMode(navRunningMode[i]);
            ni.setVerticalOffset(navVerticalOffset[i]);

            navigationInfos.add(ni);
        }
        createNewDatas(navigationInfos);
    }

    /*public List<NavigationInfo> queryAllData() {
        QueryBuilder<NavigationInfo, Integer> queryBuilder = mDao.queryBuilder();
        queryBuilder.orderBy(NavigationInfo._SORT_ID, true);
        try {
            PreparedQuery<NavigationInfo> preparedQuery = queryBuilder.prepare();
            return mDao.query(preparedQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }*/

    /**
     * 获取未关闭的导航栏数据
     *
     * @return
     */
    public List<NavigationInfo> queryNotClosed() {
        createQueryAndWhere();
        whereNotClosed();
        orderBySortIdAsc();
        return queryForCondition();
    }

    /**
     * 查询未关闭且激活的导航栏数据
     *
     * @return
     */
    public List<NavigationInfo> queryNotClosedAndActivated() {
        createQueryAndWhere();
        whereNotClosedAndActivated();
        orderBySortIdAsc();
        return queryForCondition();
    }

    /**
     * 统计未关闭的导航栏数据量
     * @return
     */
    public long countNotClosed() {
        createQueryAndWhere();
        whereNotClosed();
        return countOfCondition();
    }

    /**
     * 根据sortId获取数据
     *
     * @param sortId
     * @return
     */
    public NavigationInfo queryNotClosedBySortId(int sortId) {
        createQueryAndWhere();
        whereNotClosedSortId(sortId);
        return queryForFirst();
    }

    private void whereNotClosedSortId(int sortId) {
        try {
            mWhere.eq(NavigationInfo._SORT_ID, sortId)
                    .and().eq(NavigationInfo.IS_CLOSED, NavigationInfo.IS_CLOSED_OPEN_VALUE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void orderBySortIdAsc() {
        mQuery.orderBy(NavigationInfo._SORT_ID, true);
    }

    private void whereNotClosed() {
        try {
            mWhere.eq(NavigationInfo.IS_CLOSED, NavigationInfo.IS_CLOSED_OPEN_VALUE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void whereNotClosedAndActivated() {
        try {
            mWhere.eq(NavigationInfo.IS_CLOSED, NavigationInfo.IS_CLOSED_OPEN_VALUE)
                    .and().eq(NavigationInfo.IS_ACTIVATED, NavigationInfo.IS_ACTIVATED_ACTIVATED_VALUE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<ApiParams> queryApiParamsByIdAndType(NavigationInfo ni, String type) {
        return ApiParamsDao.getInstance().queryForNavIdAndType(ni.getId(), type);
    }

    public String queryApiControlPar(NavigationInfo ni, String type) {
        return ApiParamsDao.getInstance().queryControlPar(ni.getId(), type);
    }

    public int queryApiPagePar(NavigationInfo ni, String type) {
        return Integer.valueOf(ApiParamsDao.getInstance().queryPagePar(ni.getId(), type));
    }

    public int updateExplicitCol(String col, String value) {
        return updateByValue(col, value);
    }

    public int updateApiUrl(String url) {
        return updateByValue(NavigationInfo._API_URL, url);
    }

    public int updateCityCode(String cityCode) {
        return updateByValue(NavigationInfo._CITY_CODE, cityCode);
    }

    public int updateDeviceCode(String deviceCode) {
        return updateByValue(NavigationInfo._DEVICE_CODE, deviceCode);
    }

    public int updateSchoolId(String schoolId) {
        return updateByValue(NavigationInfo._SCHOOL_ID, schoolId);
    }

    public int updateControl(String control) {
        return updateByValue(NavigationInfo._CONTROL, control);
    }

    public int updateAction(String action) {
        return updateByValue(NavigationInfo._ACTION, action);
    }

}
