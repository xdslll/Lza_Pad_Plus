package com.lza.pad.core.request;

/**
 * 处理Url响应事件的接口
 *
 * @author xiads
 * @Date 14-9-12.
 */
public interface OnResponseListener<T> {

    public void onSuccess(T t);

    public void onError(Exception error);

}
