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

package com.km2labs.android.spendview.database.content;

import org.parceler.Parcel;

/**
 * Created by Subham Tyagi,
 * on 10/Jul/2015,
 * 1:54 AM
 * TODO:Add class comment.
 */
@Parcel(value = Parcel.Serialization.BEAN)
public class Transaction {

    public static final String TYPE_CREDIT = "credit";
    public static final String TYPE_DEBIT = "debit";

    private long id;
    private String name;
    private double amount;
    private long date;
    private String type;
    private long accountKey;

    public Transaction() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public Transaction setAmount(double amount) {
        this.amount = amount;
        return this;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getAccountKey() {
        return accountKey;
    }

    public Transaction setAccountKey(long accountKey) {
        this.accountKey = accountKey;
        return this;
    }
}
