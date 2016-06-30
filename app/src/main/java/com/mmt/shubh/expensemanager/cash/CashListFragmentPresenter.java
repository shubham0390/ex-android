package com.mmt.shubh.expensemanager.cash;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;

import com.mmt.shubh.expensemanager.core.dagger.scope.ActivityScope;
import com.mmt.shubh.expensemanager.core.mvp.BasePresenter;
import com.mmt.shubh.expensemanager.core.mvp.lce.MVPLCEView;

/**
 * Created by Subham Tyagi,
 * on 06/Sep/2015,
 * 1:04 PM
 * TODO:Add class comment.
 */
@ActivityScope
public class CashListFragmentPresenter extends BasePresenter<MVPLCEView>
        implements LoaderManager.LoaderCallbacks<Object> {


    private Context mContext;


    public CashListFragmentPresenter(Context context) {
        mContext = context;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }


    @Override
    public Loader onCreateLoader(int i, Bundle bundle) {
        return new CashListLoader(mContext);
    }


    @Override
    public void onLoadFinished(Loader loader, Object data) {
        getView().setData(data);
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }

    private static class CashListLoader extends AsyncTaskLoader {

        public CashListLoader(Context context) {
            super(context);
        }

        @Override
        public Object loadInBackground() {
            return null;
        }
    }
}
