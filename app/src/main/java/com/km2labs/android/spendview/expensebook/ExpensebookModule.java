package com.km2labs.android.spendview.expensebook;

import com.google.firebase.database.FirebaseDatabase;
import com.km2labs.android.spendview.core.dagger.scope.ConfigPersistent;
import com.km2labs.android.spendview.database.FirebaseDataManager;
import com.km2labs.android.spendview.expensebook.add.AddExpensebookFragmentContract;
import com.km2labs.android.spendview.expensebook.add.ExpenseBookFragmentPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by : Subham Tyagi
 * Created on :  20/09/16.
 */

@Module
public class ExpensebookModule {

    @Provides
    @ConfigPersistent
    FirebaseDataManager provideFirebaseDataManager() {
        return new FirebaseDataManager(FirebaseDatabase.getInstance());
    }

    @Provides
    @ConfigPersistent
    AddExpensebookFragmentContract.Presenter providePresenter(FirebaseDataManager firebaseDataManager) {
        return new ExpenseBookFragmentPresenter(firebaseDataManager);
    }
}
