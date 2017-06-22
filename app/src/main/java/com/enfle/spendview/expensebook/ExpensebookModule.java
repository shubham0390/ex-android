package com.enfle.spendview.expensebook;

import com.enfle.spendview.core.dagger.scope.ConfigPersistent;
import com.enfle.spendview.database.FirebaseDataManager;
import com.enfle.spendview.expensebook.add.AddExpensebookFragmentContract;
import com.enfle.spendview.expensebook.add.ExpenseBookFragmentPresenter;
import com.google.firebase.database.FirebaseDatabase;

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
