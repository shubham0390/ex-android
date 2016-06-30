package com.mmt.shubh.database;


public class Select {

    private StringBuilder stringBuilder;
    private static final String SELECT = " SELECT ";
    private static final String ALL = " * ";
    private static final String FROM = " FROM ";
    private static final String WHERE = " WHERE ";
    private static final String AND = " AND ";
    private static final String OR = " OR ";
    private static final String IN = " IN ";


    public Select() {
        this.stringBuilder = new StringBuilder();
        stringBuilder.append(SELECT);
    }

    public Select all() {
        stringBuilder.append(ALL);
        return this;
    }

    public Select addColumns(String[] columns) {
        for (int i = 0; i < columns.length; i++) {

            stringBuilder.append(columns[i]);
            if (i < columns.length - 1)
                stringBuilder.append(" , ");
        }
        return this;
    }

    public Select from(String tableName) {
        stringBuilder.append(FROM);
        stringBuilder.append(tableName);
        return this;
    }


    public Select where(String columnName) {
        stringBuilder.append(WHERE);
        stringBuilder.append(columnName);
        return this;
    }

    public Select equql(String value) {
        stringBuilder.append(" = ");
        stringBuilder.append(value);
        return this;
    }

    public Select equql(long value) {
        stringBuilder.append(" = ");
        stringBuilder.append(value);
        return this;
    }

    public Select equql(int value) {
        stringBuilder.append(" = ");
        stringBuilder.append(value);
        return this;
    }

    public Select in(String[] values) {
        return this;
    }

    public Select in(Select values) {
        stringBuilder.append(IN);
        stringBuilder.append(" ( ");
        stringBuilder.append(values.toString());
        stringBuilder.append(" ) ");
        return this;
    }


    public Select and() {
        stringBuilder.append(AND);
        return this;
    }

    public Select or() {
        stringBuilder.append(OR);
        return this;
    }

    @Override
    public String toString() {
        return stringBuilder.toString();
    }
}
