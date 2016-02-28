package com.mmt.shubh.expensemanager.database.api.exceptions;

import com.mmt.shubh.expensemanager.database.api.IDataAdapter;
import com.mmt.shubh.expensemanager.database.content.Account;

import com.mmt.shubh.expensemanager.database.api.DataAdapter;

import java.util.List;

import rx.Observable;

/**
 * Created by Subham Tyagi,
 * on 03/Jul/2015,
 * 3:30 PM
 * TODO:Add class comment.
 */
public interface AccountDataAdapter extends IDataAdapter<Account> {
    double getAccountBalance(long accountId);

    void updateAmount(long accountId, double balanceAmount);

    Observable<List<Account>> loadAllAccounts();
}
