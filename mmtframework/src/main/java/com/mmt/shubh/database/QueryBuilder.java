package com.mmt.shubh.database;


import com.mmt.shubh.util.DSUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shubham on 12/30/15.
 */
public class QueryBuilder {

    public static final String SPACE = " ";
    public static final int SORT_TYPE_ASC = 0;
    public static final int SORT_TYPE_DESC = 1;
    public static final char COMMA = ',';

    private static final String SELECT = "SELECT";
    private static final String FROM = "FROM";
    private static final String ALL = "*";
    private static final String WHERE = "WHERE";
    private static final String SORT_BY = "ORDER BY";
    private static final String ASC = "ASC";
    private static final String DESC = "DESC";
    private String mTableName;

    private String[] mProjections;

    private List<Selection> mSelections = new ArrayList<>();

    private List<Join> mJoins = new ArrayList<>();

    private List<OrderBy> mSortType;
    private GroupBy mGroupBy;

    private StringBuilder createQuery(String tableName, String[] projection) {
        StringBuilder sb = new StringBuilder();
        sb.append(SELECT);
        sb.append(SPACE);

        //adding projection to query
        if (DSUtil.isArrayEmpty(projection)) {
            sb.append(ALL);
            sb.append(SPACE);
        } else {
            String prefix = "";
            if (!DSUtil.isListEmpty(mJoins)) {
                prefix = mTableName + ".";
            }
            arrayToString(sb, projection, prefix);
            sb.append(SPACE);
        }

        //add join projection here /
        for (Join join : mJoins) {
            createJoinProjection(sb, join);
        }

        sb.append(FROM);
        sb.append(SPACE);
        sb.append(tableName);
        return sb;
    }

    private void createJoinProjection(StringBuilder sb, Join join) {
        arrayToString(sb, join.mProjection, join.mTableName);
    }

    public String createSelectQuery(String tableName, String[] projection) {
        StringBuilder sb = createQuery(tableName, projection);
        sb.append(SPACE);
        return sb.toString();
    }


    public void arrayToString(StringBuilder sb, Object[] projection, String prefix) {
        for (int i = 0; i < projection.length; i++) {
            sb.append(prefix);
            sb.append(projection[i]);
            if (i < projection.length - 1)
                sb.append(COMMA);
        }
    }

    /**
     * Set tale name to database query.
     *
     * @param tableName - name of the table on which query will execute.
     * @return {@link QueryBuilder}
     */
    public QueryBuilder addFrom(String tableName) {
        mTableName = tableName;
        return this;
    }

    public QueryBuilder addProjection(String[] projection) {
        mProjections = projection;
        return this;
    }

    public QueryBuilder addSelection(Selection selection) {
        mSelections.add(selection);
        return this;
    }


    public QueryBuilder addSortType(OrderBy sortType) {
        if (DSUtil.isListEmpty(mSortType)) {
            mSortType = new ArrayList<>();
        }
        mSortType.add(sortType);
        return this;
    }

    public QueryBuilder addJoin(Join join) {
        mJoins.add(join);
        return this;
    }

    public QueryBuilder addGroupBy(GroupBy groupBy) {
        mGroupBy = groupBy;
        return this;
    }

    private void createSelection(StringBuilder sb) {
        for (Selection selection : mSelections) {
            selection.build(sb, mTableName);
        }
    }

    private void createJoin(StringBuilder sb) {
        for (Join join : mJoins) {
            join.build(sb, mTableName);
        }
    }

    private void createSortType() {

    }

    private void createGroupBy() {

    }


    public String build() {
        StringBuilder sb = createQuery(mTableName, mProjections);
        if (DSUtil.isListEmpty(mJoins)) {
            createJoin(sb);
        }
        if (DSUtil.isListEmpty(mSelections)) {
            createSelection(sb);
        }
        if (!DSUtil.isListEmpty(mSortType)) {
            createSortType();
        }
        if (mGroupBy != null) {
            createGroupBy();
        }
        return sb.toString();
    }


}
