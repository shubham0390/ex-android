package com.mmt.shubh.expensemanager.ui.viewmodel;

import com.mmt.shubh.expensemanager.database.content.CardDetails;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by Subham Tyagi,
 * on 18/Sep/2015,
 * 6:05 PM
 * TODO:Add class comment.
 */
@Parcel
public class AccountListViewModel {

    String mAccountName;
    String mAccountType;
    double mAccountBalance;
    String mAccountNumber;

    List<CardDetails> mCardList;

    public AccountListViewModel() {
    }


    public double getAccountBalance() {
        return mAccountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        mAccountBalance = accountBalance;
    }

    public String getAccountName() {
        return mAccountName;
    }

    public void setAccountName(String accountName) {
        mAccountName = accountName;
    }

    public String getAccountNumber() {
        return mAccountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        mAccountNumber = accountNumber;
    }

    public String getAccountType() {
        return mAccountType;
    }

    public void setAccountType(String accountType) {
        mAccountType = accountType;
    }

    public List<CardDetails> getCardList() {
        return mCardList;
    }

    public void setCardList(List<CardDetails> cardList) {
        mCardList = cardList;
    }


}
