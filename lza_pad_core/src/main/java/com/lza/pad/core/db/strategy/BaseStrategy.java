package com.lza.pad.core.db.strategy;

import com.lza.pad.core.utils.Consts;
import com.lza.pad.core.db.model.NavigationInfo;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 14-9-19.
 */
public abstract class BaseStrategy<RESULT> implements IEbookStrategy<RESULT>, Consts {

    protected NavigationInfo mNav;

    protected BaseStrategy(NavigationInfo nav){
        this.mNav = nav;
    }

    @Override
    public abstract RESULT operation();
}
