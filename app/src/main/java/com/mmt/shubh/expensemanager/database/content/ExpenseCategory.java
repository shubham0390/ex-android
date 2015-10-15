/*
 * Copyright (c) 2014. The MMT group Project
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.mmt.shubh.expensemanager.database.content;

import org.parceler.Parcel;

@Parcel(value = Parcel.Serialization.BEAN)
public class ExpenseCategory {

    public String categoryName;

    private long id;

    private String categoryType;

    public ExpenseCategory(String categoryName, String categoryType, String categoryImageName) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
        this.categoryImageName = categoryImageName;
    }

    private String categoryImageName;

    public ExpenseCategory() {
    }

    public ExpenseCategory(String categoryName, String categoryImageName) {
        this.categoryName = categoryName;
        this.categoryImageName = categoryImageName;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public String getCategoryImageName() {
        return categoryImageName;
    }

    public void setCategoryImageName(String categoryImageName) {
        this.categoryImageName = categoryImageName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public ExpenseCategory setCategoryName(String categoryName) {
        this.categoryName = categoryName;
        return this;
    }
}