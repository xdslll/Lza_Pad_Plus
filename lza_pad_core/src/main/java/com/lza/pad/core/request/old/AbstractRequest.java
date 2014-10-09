package com.lza.pad.core.request.old;

import com.lza.pad.core.request.OnResponseListener;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 14-9-12.
 */
@Deprecated
public abstract class AbstractRequest<T> {

    protected OnResponseListener<T> mListener;

    public void setListener(OnResponseListener<T> listener) {
        this.mListener = listener;
    }

    public AbstractRequest() {}

    public AbstractRequest(OnResponseListener<T> mListener) {
        setListener(mListener);
    }
}
