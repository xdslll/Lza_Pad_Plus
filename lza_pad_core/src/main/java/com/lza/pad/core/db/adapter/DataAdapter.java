package com.lza.pad.core.db.adapter;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 14-9-17.
 */
public interface DataAdapter<OLD, NEW> {

    public NEW apdater(OLD oldData);

}
