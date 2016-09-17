/*
 * Copyright (c) 2016. . The Km2Labs Project
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.km2labs.android.spendview.core;

import android.text.TextUtils;

import com.km2labs.android.spendview.core.util.CollectionUtils;

/**
 * Created by shubham on 12/31/15.
 */
public class Selection {

    public static final String EQUAL = "=";
    public static final String AND = "AND";
    public static final String OR = "OR";
    public static final String ON = "ON";
    public static final String GREATER = ">";


    private String mColumnName;
    private String mOperation;
    private String mValue;
    private String[] mValues;

    public Selection(String columnName, String operation, String value) {
        mColumnName = columnName;
        mOperation = operation;
        mValue = value;
    }

    public Selection(String columnName, String operation, long value) {
        this(columnName, operation, String.valueOf(value));
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

        if (CollectionUtils.isEmpty(mValues)) {
            sb.append(" ( ");
            new QueryBuilder().arrayToString(sb, mValues, prefix);
            sb.append(" ) ");
        } else {
            sb.append(QueryBuilder.SPACE);
            sb.append(mValue);
        }
    }

    public String build() {
        StringBuilder builder = new StringBuilder();
        build(builder, null);
        return builder.toString();
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

    public static class Builder {
        StringBuilder sb = new StringBuilder();
        String prefix;

        public Builder addPrefix(String prefix) {
            this.prefix = prefix;
            return this;
        }

        public Builder appendOperation(String operation) {
            sb.append(QueryBuilder.SPACE);
            sb.append(operation);
            sb.append(QueryBuilder.SPACE);
            return this;
        }

        public Builder appendSelection(String columnName, String operation, String value) {
            if (!TextUtils.isEmpty(prefix)) {
                sb.append(prefix);
                sb.append(".");
            }
            sb.append(columnName);
            sb.append(QueryBuilder.SPACE);
            sb.append(operation);
            sb.append(QueryBuilder.SPACE);
            sb.append(value);
            return this;
        }

        public Builder appendSelection(String columnName, String operation, long value) {
            return appendSelection(columnName, operation, String.valueOf(value));
        }

        public String build() {
            return sb.toString();
        }
    }
}
