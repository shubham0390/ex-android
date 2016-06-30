package com.mmt.shubh.expensemanager.member;

import android.content.Context;

import com.mmt.shubh.expensemanager.core.dagger.scope.ActivityScope;
import com.mmt.shubh.expensemanager.database.api.ExpenseBookDataAdapter;
import com.mmt.shubh.expensemanager.database.api.ExpenseDataAdapter;
import com.mmt.shubh.expensemanager.database.api.MemberDataAdapter;
import com.mmt.shubh.expensemanager.database.content.Member;
import com.mmt.shubh.expensemanager.core.mvp.lce.LCEViewState;
import com.mmt.shubh.expensemanager.core.mvp.lce.LCEViewStateImpl;
import com.mmt.shubh.expensemanager.core.mvp.lce.MVPLCEView;

import java.util.List;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Subham Tyagi,
 * on 02/Oct/2015,
 * 7:26 PM
 * TODO:Add class comment.
 */
@Module
public class MemberListFragmentModule {

    @Provides
    @ActivityScope
    MemberListFragmentPresenter provideMemberListFragmentPresenter(Context context, MemberModel memberModel) {
        return new MemberListFragmentPresenter(context, memberModel);
    }

    @Provides
    @ActivityScope
    MemberModel provideMemberModel(MemberDataAdapter memberDataAdapter, ExpenseDataAdapter expenseDataAdapter,
                                   ExpenseBookDataAdapter expenseBookDataAdapter) {
        return new MemberModel(memberDataAdapter, expenseDataAdapter, expenseBookDataAdapter);
    }

    @Provides
    @ActivityScope
    LCEViewState<List<Member>, MVPLCEView<List<Member>>> provideViewLCEViewState() {
        return new LCEViewStateImpl<>();
    }


}
