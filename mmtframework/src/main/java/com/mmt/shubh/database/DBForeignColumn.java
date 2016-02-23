package com.mmt.shubh.database;

/**
 * TODO:Add class comment.
 * <p>
 * Created by shubham,
 * on 1/13/16,
 */
public class DBForeignColumn extends DBColumn {

    private final String mReferenceColumnName;

    public DBForeignColumn(String columnName, String referenceTableName, String referenceColumnName) {
        super(columnName, referenceTableName);
        mReferenceColumnName = referenceColumnName;
    }

    public void createStatement(StringBuilder builder) {
        builder.append(TableBuilder.FOREIGN_KEY);
        builder.append(" ( ");
        builder.append(mName);
        builder.append(" ) ");
        builder.append(TableBuilder.REFERENCES);
        builder.append(mType);
        builder.append(" ( ");
        builder.append(mReferenceColumnName);
        builder.append(" ) ");
    }
}
