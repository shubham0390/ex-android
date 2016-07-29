package com.km2labs.android.spendview.expense;

import com.km2labs.android.spendview.database.content.Account;
import com.km2labs.android.spendview.core.mvp.MVPView;

import java.util.List;

/**
 * Created by subhamtyagi on 3/12/16.
 */
public interface AddExpenseView extends MVPView{
    void onEmptyTitleError();

    void onEmptyAccountError();

    void onEmptyAmountError();



    void onAccountListLoad(List<Account> data);


}
