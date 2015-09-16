package com.mmt.shubh.expensemanager.ui.presenters;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;

import com.mmt.shubh.expensemanager.dagger.MainComponent;
import com.mmt.shubh.expensemanager.database.api.ExpenseBookDataAdapter;
import com.mmt.shubh.expensemanager.database.content.ExpenseBook;
import com.mmt.shubh.expensemanager.ui.component.DaggerCashActivityComponent;
import com.mmt.shubh.expensemanager.ui.component.DaggerExpenseBookActivityComponent;
import com.mmt.shubh.expensemanager.ui.component.ExpenseBookActivityComponent;
import com.mmt.shubh.expensemanager.ui.module.ExpenseBookActivityModule;
import com.mmt.shubh.expensemanager.ui.mvp.MVPAbstractPresenter;
import com.mmt.shubh.expensemanager.ui.mvp.MVPLCEView;
import com.mmt.shubh.expensemanager.ui.mvp.MVPPresenter;

import java.lang.ref.WeakReference;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Subham Tyagi,
 * on 09/Sep/2015,
 * 10:07 PM
 * TODO:Add class comment.
 */
public class ExpenseBookListPresenter extends MVPAbstractPresenter<MVPLCEView<List<ExpenseBook>>>
        implements MVPPresenter<MVPLCEView<List<ExpenseBook>>>, LoaderManager.LoaderCallbacks<List<ExpenseBook>> {

    private Context mContext;

    @Inject
    ExpenseBookDataAdapter mExpenseBookDataAdapter;

    public ExpenseBookListPresenter(Context context, ExpenseBookDataAdapter adapter) {
        mContext = context;
        mExpenseBookDataAdapter = adapter;
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public Loader<List<ExpenseBook>> onCreateLoader(int i, Bundle bundle) {
        return new ExpenseBookListLoader(mContext, mExpenseBookDataAdapter);
    }

    @Override
    public void onLoadFinished(Loader<List<ExpenseBook>> loader, List<ExpenseBook> expenseBooks) {
        getView().setData(expenseBooks);
    }

    @Override
    public void onLoaderReset(Loader<List<ExpenseBook>> loader) {

    }

    private static class ExpenseBookListLoader extends AsyncTaskLoader<List<ExpenseBook>> {
        WeakReference<ExpenseBookDataAdapter> mAdapterWeakReference;

        public ExpenseBookListLoader(Context context, ExpenseBookDataAdapter adapter) {
            super(context);
            mAdapterWeakReference = new WeakReference<>(adapter);
        }

        @Override
        public List<ExpenseBook> loadInBackground() {
            return mAdapterWeakReference.get().getAll();
        }
    }

}
