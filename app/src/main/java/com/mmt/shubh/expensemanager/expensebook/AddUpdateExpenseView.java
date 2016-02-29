package com.mmt.shubh.expensemanager.expensebook;

import com.mmt.shubh.expensemanager.mvp.MVPView;

/**
 * Created by subhamtyagi on 2/29/16.
 */
public interface AddUpdateExpenseView extends MVPView {
    void onMemberAdd(Boolean v);

    void onMemberAddError(Throwable e);
}
