package com.mmt.shubh.expensemanager.database.content;

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
    private int amount;
    private long date;
    private String type;

    public Transaction() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
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
}
