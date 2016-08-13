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
