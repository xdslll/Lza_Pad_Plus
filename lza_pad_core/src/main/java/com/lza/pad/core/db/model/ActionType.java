package com.lza.pad.core.db.model;

/**
 * Action类型,即api中action参数的值
 *
 * @author xiads
 * @Date 14-9-12.
 */
public class ActionType {

    private String getMessage;

    private String getMessage_1_0;

    private String get_pdf;

    private String getPadClientVersion;

    public String getGetMessage() {
        return getMessage;
    }

    public void setGetMessage(String getMessage) {
        this.getMessage = getMessage;
    }

    public String getGetMessage_1_0() {
        return getMessage_1_0;
    }

    public void setGetMessage_1_0(String getMessage_1_0) {
        this.getMessage_1_0 = getMessage_1_0;
    }

    public String getGet_pdf() {
        return get_pdf;
    }

    public void setGet_pdf(String get_pdf) {
        this.get_pdf = get_pdf;
    }

    public String getGetPadClientVersion() {
        return getPadClientVersion;
    }

    public void setGetPadClientVersion(String getPadClientVersion) {
        this.getPadClientVersion = getPadClientVersion;
    }
}
