package com.mmt.shubh.expensemanager.database.content;


import org.parceler.Parcel;

import io.realm.BankCardRealmProxy;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Subham Tyagi,
 * on 29/Sep/2015,
 * 8:59 PM
 * TODO:Add class comment.
 */
@Parcel(implementations = { BankCardRealmProxy.class },
        value = Parcel.Serialization.BEAN,
        analyze = { BankCard.class })
public class BankCard extends RealmObject {

    @PrimaryKey
    private long id;

    private Account account;

    private String cardNo;

    private long balanceAmount;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public long getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(long balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
