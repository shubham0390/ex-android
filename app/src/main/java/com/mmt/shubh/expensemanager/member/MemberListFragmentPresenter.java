package com.mmt.shubh.expensemanager.member;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;

import com.mmt.shubh.expensemanager.Constants;
import com.mmt.shubh.expensemanager.database.api.MemberDataAdapter;
import com.mmt.shubh.expensemanager.database.content.Member;
import com.mmt.shubh.expensemanager.database.dataadapters.MemberSQLDataAdapter;
import com.mmt.shubh.expensemanager.mvp.MVPAbstractPresenter;
import com.mmt.shubh.expensemanager.mvp.MVPPresenter;
import com.mmt.shubh.expensemanager.mvp.lce.MVPLCEView;

import java.util.List;

/**
 * Created by Subham Tyagi,
 * on 02/Oct/2015,
 * 11:53 AM
 * TODO:Add class comment.
 */
public class MemberListFragmentPresenter extends MVPAbstractPresenter<MVPLCEView<List<Member>>>
        implements MVPPresenter<MVPLCEView<List<Member>>>, LoaderManager.LoaderCallbacks<List<Member>> {

    private Context mContext;

    public MemberListFragmentPresenter(Context context, MemberDataAdapter memberDataAdapter) {
        mContext = context;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    public void loadMembers(LoaderManager loaderManager, Bundle bundle) {
        loaderManager.initLoader(12, bundle, this).forceLoad();
    }

    public void deleteMember(long id) {

    }


    public static class MemberListLoader extends AsyncTaskLoader<List<Member>> {
        long mId;

        public MemberListLoader(Context context, long expenseBookId) {
            super(context);
            mId = expenseBookId;
        }

        @Override
        public List<Member> loadInBackground() {
            MemberDataAdapter dataAdapter = new MemberSQLDataAdapter(getContext());
            if (mId != -1) {
                return dataAdapter.getAllMemberByExpenseBookId(mId);
            }
            return dataAdapter.getAll();
        }
    }

    @Override
    public Loader<List<Member>> onCreateLoader(int id, Bundle args) {
        if (args != null) {
            long exbId = args.getLong(Constants.KEY_EXPENSE_BOOK_ID);
            return new MemberListLoader(mContext, exbId);
        }
        return new MemberListLoader(mContext, -1);
    }

    @Override
    public void onLoadFinished(Loader<List<Member>> loader, List<Member> data) {
        getView().setData(data);
    }

    @Override
    public void onLoaderReset(Loader<List<Member>> loader) {
    }
}
