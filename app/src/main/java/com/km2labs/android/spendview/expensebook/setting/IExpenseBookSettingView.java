package com.km2labs.android.spendview.expensebook.setting;

import com.km2labs.android.spendview.database.content.Member;
import com.km2labs.android.spendview.core.mvp.MVPView;

/**
 * Created by Subham Tyagi,
 * on 16/Sep/2015,
 * 4:59 PM
 * TODO:Add class comment.
 */
public interface IExpenseBookSettingView extends MVPView {
    void onOwnerLoaded(Member member);
}
