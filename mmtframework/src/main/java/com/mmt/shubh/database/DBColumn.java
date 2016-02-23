package com.mmt.shubh.database;


import com.mmt.shubh.util.DSUtil;

/**
 * TODO:Add class comment.
 * <p>
 * Created by shubham,
 * on 1/13/16,
 */
public class DBColumn {
    protected String mName;
    protected String mType;
    private String[] mConstraint;

    public DBColumn(String name, String type) {
        mName = name;
        mType = type;
    }

    public void setConstraint(String[] constraint) {
        mConstraint = constraint;
    }

    public void createStatement(StringBuilder sb) {
        sb.append(mName);
        sb.append(" ");
        sb.append(mType);
        if (!DSUtil.isArrayEmpty(mConstraint)) {
            sb.append(" ");
            for (String aMConstraint : mConstraint) {
                sb.append(aMConstraint);
                sb.append(" ");
            }
        }
    }
}
