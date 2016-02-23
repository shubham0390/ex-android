package com.mmt.shubh.database;

import android.text.TextUtils;

import com.mmt.shubh.util.DSUtil;

/**
 * Created by shubham on 12/31/15.
 */
public class Selection {

    public static final String EQUAL = "=";
    public static final String AND = "AND";
    public static final String OR = "OR";
    public static final String ON = "ON";


    private String mColumnName;
    private String mOperation;
    private String mValue;
    private String[] mValues;

    public Selection(String columnName, String operation, String value) {
        mColumnName = columnName;
        mOperation = operation;
        mValue = value;
    }

    public Selection(String columnName, String operation, String[] value) {
        mColumnName = columnName;
        mOperation = operation;
        mValues = value;
    }

    public static final String getSelection(String columnName, String operation, Object value) {
        return columnName + " " + operation + " " + value.toString();
    }

    public void build(StringBuilder sb, String prefix) {

        if (!TextUtils.isEmpty(prefix)) {
            sb.append(prefix);
            sb.append(".");
        }
        sb.append(mColumnName);
        sb.append(QueryBuilder.SPACE);
        sb.append(mOperation);

        if (DSUtil.isArrayEmpty(mValues)) {
            sb.append(" ( ");
            new QueryBuilder().arrayToString(sb, mValues, prefix);
            sb.append(" ) ");
        } else {
            sb.append(QueryBuilder.SPACE);
            sb.append(mValue);
        }
    }

    public void build(StringBuilder sb, String prefix, String prefix2) {
        sb.append(prefix);
        sb.append(".");
        sb.append(mColumnName);
        sb.append(QueryBuilder.SPACE);
        sb.append(mOperation);
        sb.append(QueryBuilder.SPACE);
        sb.append(prefix2);
        sb.append(mValue);
        sb.append(QueryBuilder.SPACE);
    }
}
