package com.mmt.shubh.expensemanager.database.content;

/**
 * Created by Subham Tyagi,
 * on 10/Jul/2015,
 * 1:54 AM
 * TODO:Add class comment.
 */
public class Transaction extends BaseContent {

    public static final String TYPE_CREDIT = "credit";
    public static final String TYPE_DEBIT = "debit";

    private String name;

    private int amount;

    private long date;

    private String type;


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
