package com.mmt.shubh.database;

/**
 * Created by subhamtyagi on 2/7/16.
 */
public class GroupBy {
    private String mColumnName;

    public GroupBy(String columnName) {
        mColumnName = columnName;
    }

    public String getColumnName() {
        return mColumnName;
    }

    public void setColumnName(String columnName) {
        mColumnName = columnName;
    }
}
