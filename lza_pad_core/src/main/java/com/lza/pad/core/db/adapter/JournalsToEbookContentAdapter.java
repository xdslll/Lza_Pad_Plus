package com.lza.pad.core.db.adapter;

import com.lza.pad.core.db.model.EbookContent;
import com.lza.pad.core.db.model.JournalsContent;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 14-9-19.
 */
public class JournalsToEbookContentAdapter implements DataAdapter<JournalsContent,EbookContent> {

    @Override
    public EbookContent apdater(JournalsContent oldData) {
        EbookContent content = new EbookContent();
        content.setAbstractStr(oldData.getAbstractStr());
        content.setArticlefrom(oldData.getArticlefrom());
        content.setAuthor(oldData.getAuthor());
        content.setDoi(oldData.getDoi());
        content.setEnabstract(oldData.getEnabstract());
        content.setEnkanname(oldData.getEnkanname());
        content.setEnkeywords(oldData.getEnkeywords());
        content.setEnauthor(oldData.getEnauthor());
        content.setEntitle(oldData.getEntitle());
        content.setFenleihao(oldData.getFenleihao());
        content.setFname(oldData.getFname());
        content.setId(oldData.getId());
        content.setJigou(oldData.getJigou());
        content.setKan_name(oldData.getKan_name());
        content.setKancode(oldData.getKancode());
        content.setKeywords(oldData.getKeywords());
        content.setPubdate(oldData.getPubdate());
        content.setQkId(oldData.getQkId());
        content.setQi(oldData.getQi());
        content.setTest_url(oldData.getTest_url());
        content.setTitle(oldData.getTitle());
        content.setTotal_down(oldData.getTotal_down());
        return content;
    }
}
