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
import org.parceler.ParcelPropertyConverter;

import io.realm.AccountRealmProxy;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

@Parcel(implementations = { AccountRealmProxy.class },
        value = Parcel.Serialization.BEAN,
        analyze = { Account.class })
public class Account extends RealmObject {

    public static final String TYPE_BANK = "bank";
    public static final String TYPE_CREDIT_CARD = "credit_card";
    public static final String TYPE_CASH = "cash";

    @PrimaryKey
    private long id;

    private String accountName;

    private long accountBalance;

    private String accountType;

    private String accountNumber;

    private String bankName;

    private RealmList<BankCard> cards;

    public Account() {
    }

    @ParcelPropertyConverter(BankCardParcelConverter.class)
    public RealmList<BankCard> getCards() {
        return cards;
    }

    public void setCards(RealmList<BankCard> cards) {
        this.cards = cards;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName;
    }

    public long getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(long accountBalance) {
        this.accountBalance = accountBalance;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}