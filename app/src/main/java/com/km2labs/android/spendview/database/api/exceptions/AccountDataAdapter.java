package com.km2labs.android.spendview.database.api.exceptions;

import com.km2labs.android.spendview.database.api.IDataAdapter;
import com.km2labs.android.spendview.database.content.Account;

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

    Observable<List<Account>> getAccountByMember(long memberId);
}
