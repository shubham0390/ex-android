package com.km2labs.android.spendview.database.content;

import org.parceler.Parcel;

/**
 * Created by Subham Tyagi,
 * on 30/Sep/2015,
 * 5:46 PM
 * TODO:Add class comment.
 */
@Parcel(value = Parcel.Serialization.BEAN)
public class CardDetails {

    private String cardNo;
    private long balanceAmount;
    private String accountKey;
    private long id;

    public CardDetails() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAccountKey() {
        return accountKey;
    }

    public void setAccountKey(String accountKey) {
        this.accountKey = accountKey;
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
}
