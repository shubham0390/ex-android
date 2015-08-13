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

import android.os.Parcel;
import android.os.Parcelable;

public class Account extends BaseContent implements Parcelable {

    public static final String TYPE_BANK = "bank";
    public static final String TYPE_CREDIT_CARD = "credit_card";
    public static final String TYPE_CASH = "cash";

    private String mAccountName;

    private long mAccountBalance;

    private String mType;

    private String mAccountNumber;

    private String mBankName;

    public Account() {
    }

    protected Account(Parcel in) {
        mAccountName = in.readString();
        mAccountBalance = in.readLong();
        mType = in.readString();
        mAccountNumber = in.readString();
        mBankName = in.readString();
    }

    public static final Creator<Account> CREATOR = new Creator<Account>() {
        @Override
        public Account createFromParcel(Parcel in) {
            return new Account(in);
        }

        @Override
        public Account[] newArray(int size) {
            return new Account[size];
        }
    };

    @Override
    public long getId() {
        return mId;
    }

    public String getAccountName() {
        return mAccountName;
    }

    public long getAccountBalance() {
        return mAccountBalance;
    }

    public void setAccountBalance(long accountBalance) {
        mAccountBalance = accountBalance;
    }

    public void setAccountName(String accountName) {
        mAccountName = accountName;
    }

    public String getType() {
        return mType;
    }

    public Account setType(String type) {
        mType = type;
        return this;
    }

    public String getAccountNumber() {
        return mAccountNumber;
    }

    public Account setAccountNumber(String accountNumber) {
        mAccountNumber = accountNumber;
        return this;
    }

    public String getBankName() {
        return mBankName;
    }

    public void setBankName(String bankName) {
        mBankName = bankName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mAccountName);
        dest.writeLong(mAccountBalance);
        dest.writeString(mType);
        dest.writeString(mAccountNumber);
        dest.writeString(mBankName);
    }
}