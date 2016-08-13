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
