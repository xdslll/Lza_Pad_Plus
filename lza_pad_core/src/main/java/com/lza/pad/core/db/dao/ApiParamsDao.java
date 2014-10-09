package com.lza.pad.core.db.dao;

import android.content.Context;

import com.lza.pad.core.R;
import com.lza.pad.core.db.model.ApiParams;

import java.sql.SQLException;
import java.util.List;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 14-9-15.
 */
public class ApiParamsDao extends BaseDao<ApiParams, Integer> {

    private static ApiParamsDao sApiParams;

    public ApiParamsDao() {
      super(ApiParams.class);
    }

    public static ApiParamsDao getInstance() {
        if (sApiParams == null)
            sApiParams = new ApiParamsDao();
        return sApiParams;
    }

    public void initApiParam(Context context) {
        String[] keys = context.getResources().getStringArray(R.array.api_par_init_key);
        String[] values = context.getResources().getStringArray(R.array.api_par_init_value);
        int[] navIds = context.getResources().getIntArray(R.array.api_par_nav_id);
        String[] types = context.getResources().getStringArray(R.array.api_par_init_type);

        for (int i = 0; i < keys.length; i++) {
            ApiParams params = new ApiParams();
            params.setKey(keys[i]);
            params.setValue(values[i]);
            params.setNavId(navIds[i]);
            params.setType(types[i]);
            createNewData(params);
        }
    }

    public List<ApiParams> queryForNavIdAndType(int navId, String type) {
        createQueryAndWhere();
        //查询指定类型和通用类型的参数
        try {
            mWhere.eq(ApiParams._NAV_ID, navId)
                    .and().eq(ApiParams._TYPE, type)
                    .or().eq(ApiParams._TYPE, ApiParams.API_TYPE_UNIVERSAL);
            return queryForCondition();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String queryControlPar(int navId, String type) {
        return queryParValueByKey(navId, type, ApiParams.API_KEY_CONTROL);
    }

    public String queryPagePar(int navId, String type) {
        return queryParValueByKey(navId, type, ApiParams.API_KEY_PAGE);
    }

    public String queryParValueByKey(int navId, String type, String keyType) {
        createQueryAndWhere();
        //查询指定类型和通用类型的参数
        try {
            mWhere.eq(ApiParams._NAV_ID, navId)
                    .and().eq(ApiParams._TYPE, type)
                    .and().eq(ApiParams._KEY, keyType);
            return queryForFirst().getValue();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
