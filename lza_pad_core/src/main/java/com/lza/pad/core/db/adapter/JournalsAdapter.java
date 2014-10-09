package com.lza.pad.core.db.adapter;

import com.lza.pad.core.db.model.Ebook;
import com.lza.pad.core.db.model.Journals;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 14-9-17.
 */
public class JournalsAdapter implements DataAdapter<Ebook, Journals> {

    @Override
    public Journals apdater(Ebook oldData) {
        Journals journals = new Journals();
        journals.setId(oldData.getId());
        journals.setTitle_c(oldData.getTitle_c());
        journals.setTitle_e(oldData.getTitle_e());
        journals.setUrl(oldData.getUrl());
        journals.setCompany(oldData.getCompany());
        journals.setAddress(oldData.getAddress());
        journals.setTel(oldData.getTel());
        journals.setEmail(oldData.getEmail());
        journals.setZq(oldData.getZq());
        journals.setPress(oldData.getPress());
        journals.setLan(oldData.getLan());
        journals.setKb(oldData.getKb());
        journals.setIssn(oldData.getIssn());
        journals.setXn(oldData.getXn());
        journals.setPost(oldData.getPost());
        journals.setOld_name(oldData.getOld_name());
        journals.setCreat_pubdate(oldData.getCreat_pubdate());
        journals.setDatabase_qg(oldData.getDatabase_qg());
        journals.setHx(oldData.getHx());
        journals.setGg(oldData.getGg());
        journals.setSx(oldData.getSx());
        journals.setQkImg(oldData.getQkImg());

        return journals;
    }
}
