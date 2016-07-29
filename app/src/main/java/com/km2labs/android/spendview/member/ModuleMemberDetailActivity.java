package com.km2labs.android.spendview.member;

import com.km2labs.android.spendview.core.dagger.scope.ActivityScope;
import com.km2labs.android.spendview.database.api.ExpenseBookDataAdapter;
import com.km2labs.android.spendview.database.api.MemberDataAdapter;
import com.km2labs.android.spendview.database.api.ExpenseDataAdapter;

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
