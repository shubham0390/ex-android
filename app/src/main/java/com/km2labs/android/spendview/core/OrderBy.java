package com.km2labs.android.spendview.core;

/**
 * Created by subhamtyagi on 2/3/16.
 */
public class OrderBy {

    String mTableName;
    String mColumnName;

    public OrderBy(String mTableName, String mColumnName) {
        this.mTableName = mTableName;
        this.mColumnName = mColumnName;
    }
}
