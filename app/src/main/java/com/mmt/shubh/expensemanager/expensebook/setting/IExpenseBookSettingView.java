package com.mmt.shubh.expensemanager.expensebook.setting;

import com.mmt.shubh.expensemanager.database.content.Member;
import com.mmt.shubh.expensemanager.core.mvp.MVPView;

/**
 * Created by Subham Tyagi,
 * on 16/Sep/2015,
 * 4:59 PM
 * TODO:Add class comment.
 */
public interface IExpenseBookSettingView extends MVPView {
    void onOwnerLoaded(Member member);
}
