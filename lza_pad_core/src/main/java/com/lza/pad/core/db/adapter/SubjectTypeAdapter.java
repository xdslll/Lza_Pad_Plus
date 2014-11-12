package com.lza.pad.core.db.adapter;

import com.lza.pad.core.db.model.Ebook;
import com.lza.pad.core.db.model.SubjectType;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 11/7/14.
 */
public class SubjectTypeAdapter implements DataAdapter<Ebook,SubjectType> {

    @Override
    public SubjectType apdater(Ebook oldData) {
        SubjectType data = new SubjectType();

        data.setId(String.valueOf(oldData.getId()));
        data.setTitle(oldData.getTitle());
        data.setValue(oldData.getValue());
        data.setSchoolId(oldData.getSchoolId());
        data.setPx(Integer.parseInt(oldData.getPx()));
        data.setXk_type(oldData.getXk_type());
        data.setTag(oldData.getName());

        return data;
    }
}
