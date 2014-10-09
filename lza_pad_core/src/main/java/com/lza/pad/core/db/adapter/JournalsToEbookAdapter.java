package com.lza.pad.core.db.adapter;

import com.lza.pad.core.db.model.Ebook;
import com.lza.pad.core.db.model.Journals;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 14-9-17.
 */
public class JournalsToEbookAdapter implements DataAdapter<Journals, Ebook> {

    @Override
    public Ebook apdater(Journals oldData) {
        Ebook ebook = new Ebook();
        ebook.setId(oldData.getId());
        ebook.setTitle_c(oldData.getTitle_c());
        ebook.setTitle_e(oldData.getTitle_e());
        ebook.setUrl(oldData.getUrl());
        ebook.setCompany(oldData.getCompany());
        ebook.setAddress(oldData.getAddress());
        ebook.setTel(oldData.getTel());
        ebook.setEmail(oldData.getEmail());
        ebook.setZq(oldData.getZq());
        ebook.setPress(oldData.getPress());
        ebook.setLan(oldData.getLan());
        ebook.setKb(oldData.getKb());
        ebook.setIssn(oldData.getIssn());
        ebook.setXn(oldData.getXn());
        ebook.setPost(oldData.getPost());
        ebook.setOld_name(oldData.getOld_name());
        ebook.setCreat_pubdate(oldData.getCreat_pubdate());
        ebook.setDatabase_qg(oldData.getDatabase_qg());
        ebook.setHx(oldData.getHx());
        ebook.setGg(oldData.getGg());
        ebook.setSx(oldData.getSx());
        ebook.setQkImg(oldData.getQkImg());
        ebook.setQkImgPath(oldData.getQkImgPath());

        return ebook;
    }
}
