package com.mmt.shubh.database;

/**
 * Created by subhamtyagi on 2/2/16.
 */
public class Join {

    String mTableName;
    String[] mProjection;
    String mJoinType;
    Selection mSelection;

    public Join(String mTableName, String[] mProjection, String mJoinType, Selection selection) {
        this.mTableName = mTableName;
        this.mProjection = mProjection;
        this.mJoinType = mJoinType;
        mSelection = selection;
    }

    public void build(StringBuilder sb, String prefix) {
        sb.append(QueryBuilder.SPACE);
        sb.append(mJoinType);
        sb.append(QueryBuilder.SPACE);
        sb.append(mTableName);
        sb.append(QueryBuilder.SPACE);
        mSelection.build(sb, prefix, mTableName);
    }

    public String getTableName() {
        return mTableName;
    }

    public void setTableName(String tableName) {
        mTableName = tableName;
    }

    public String[] getProjection() {
        return mProjection;
    }

    public void setProjection(String[] projection) {
        mProjection = projection;
    }

    public String getJoinType() {
        return mJoinType;
    }

    public void setJoinType(String joinType) {
        mJoinType = joinType;
    }

    public Selection getSelection() {
        return mSelection;
    }

    public void setSelection(Selection selection) {
        mSelection = selection;
    }
}
