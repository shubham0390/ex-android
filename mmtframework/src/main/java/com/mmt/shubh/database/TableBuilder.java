package com.mmt.shubh.database;

/**
 * TODO:Add class comment.
 * <p>
 * Created by shubham,
 * on 1/13/16,
 */
public class TableBuilder {

    public static final String CREATE_TABLE = " CREATE TABLE ";
    public static final String FOREIGN_KEY = " FOREIGN KEY ";
    public static final String FIELD_TYPE_INTEGER = " INTEGER ";
    public static final String FIELD_TYPE_TEXT = " TEXT ";
    public static final String CONSTRAINT_NOT_NULL = " NOT NULL ";
    public static final String CONSTRAINT_PRIMARY_KEY = " PRIMARY KEY ";
    public static final String CONSTRAINT_UNIQUE = " UNIQUE ";
    public static final String REFERENCES = " REFERENCES ";


    private final StringBuilder mSB;

    private final DBColumn[] mDBColumns;

    private int mCount = 0;

    private String mTableName;

    public TableBuilder(int columnCount) {
        mSB = new StringBuilder();
        mDBColumns = new DBColumn[columnCount];

    }

    private String createTable() {
        mSB.append(CREATE_TABLE);
        mSB.append(mTableName);
        mSB.append(" ( ");
        for (int i = 0; i < mDBColumns.length; i++) {
            DBColumn column = mDBColumns[i];
            column.createStatement(mSB);
            if (i < mDBColumns.length - 1) {
                mSB.append(", ");
            }
        }
        mSB.append(" ); ");
        return mSB.toString();
    }

    public TableBuilder addTableName(String tableName) {
        mTableName = tableName;
        return this;
    }

    public TableBuilder addColumn(DBColumn dbColumn) {
        mDBColumns[mCount++] = dbColumn;
        return this;
    }

    public TableBuilder addColumn(String columnName, String type) {
        addColumn(new DBColumn(columnName, type));
        return this;
    }

    public TableBuilder addColumn(String columnName, String type, String... constraint) {
        DBColumn column = new DBColumn(columnName, type);
        column.setConstraint(constraint);
        addColumn(column);
        return this;
    }

    public TableBuilder addForgienKey(String colmnName, String refrenceTableName, String referenceColumnName) {
        addColumn(new DBForeignColumn(colmnName, refrenceTableName, referenceColumnName));
        return this;
    }

    public String build() {
        if (mCount != mDBColumns.length) {
            throw new IllegalArgumentException("Column Count should be similar to provide column count in constructor. " +
                    " TableName-: " + mTableName + " ,Total ColumnCount-: " + mDBColumns.length + " ,added Columns-: " + mCount);
        }
        createTable();
        return mSB.toString();
    }


}
