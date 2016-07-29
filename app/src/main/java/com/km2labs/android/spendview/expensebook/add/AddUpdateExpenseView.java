package com.km2labs.android.spendview.expensebook.add;

import com.km2labs.android.spendview.core.mvp.MVPView;

/**
 * Created by subhamtyagi on 2/29/16.
 */
public interface AddUpdateExpenseView extends MVPView {
    void onMemberAdd(Boolean v);

    void onMemberAddError(Throwable e);
}
