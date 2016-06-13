package com.mmt.shubh.expensemanager.member;

import com.mmt.shubh.expensemanager.dagger.scope.ActivityScope;
import com.mmt.shubh.expensemanager.database.api.ExpenseBookDataAdapter;
import com.mmt.shubh.expensemanager.database.api.ExpenseDataAdapter;
import com.mmt.shubh.expensemanager.database.api.MemberDataAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public class ModuleMemberDetailActivity {

    @Provides
    @ActivityScope
    public MemberModel provideMemberModel(MemberDataAdapter memberDataAdapter, ExpenseDataAdapter
            expenseDataAdapter, ExpenseBookDataAdapter expenseBookDataAdapter) {
        return new MemberModel(memberDataAdapter, expenseDataAdapter, expenseBookDataAdapter);
    }
}
