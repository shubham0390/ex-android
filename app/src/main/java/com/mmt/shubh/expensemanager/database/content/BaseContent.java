package com.mmt.shubh.expensemanager.database.content;

/**
 * Created by styagi on 6/1/2015.
 */
public abstract class BaseContent {

    protected long mId;

    public long getId() {
        return mId;
    }

    public BaseContent setId(long id) {
        mId = id;
        return this;
    }
}
