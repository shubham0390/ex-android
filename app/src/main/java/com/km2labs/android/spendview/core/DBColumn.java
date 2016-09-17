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


import com.km2labs.android.spendview.core.util.CollectionUtils;

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
        if (!CollectionUtils.isEmpty(mConstraint)) {
            sb.append(" ");
            for (String aMConstraint : mConstraint) {
                sb.append(aMConstraint);
                sb.append(" ");
            }
        }
    }
}
