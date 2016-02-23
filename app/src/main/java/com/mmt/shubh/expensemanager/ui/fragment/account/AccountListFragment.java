package com.mmt.shubh.expensemanager.ui.fragment.account;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mmt.shubh.expensemanager.Constants;
import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.dagger.component.MainComponent;
import com.mmt.shubh.expensemanager.database.exception.EmptyDataException;
import com.mmt.shubh.expensemanager.ui.activity.AccountActivity;
import com.mmt.shubh.expensemanager.ui.adapters.AccountListAdapter;
import com.mmt.shubh.expensemanager.ui.listener.AccountFragmentIntractionListener;
import com.mmt.shubh.expensemanager.ui.mvp.lce.LCEViewState;
import com.mmt.shubh.expensemanager.ui.mvp.lce.LCEViewStateImpl;
import com.mmt.shubh.expensemanager.ui.mvp.lce.MVPLCEFragment;
import com.mmt.shubh.expensemanager.ui.mvp.lce.MVPLCEView;
import com.mmt.shubh.expensemanager.ui.presenters.AccountListPresenter;
import com.mmt.shubh.expensemanager.ui.viewmodel.AccountListViewModel;
import com.mmt.shubh.recyclerviewlib.ListRecyclerView;

import java.util.List;

import butterknife.OnClick;


/**
 * A placeholder fragment containing a simple view.
 */
public class AccountListFragment extends MVPLCEFragment<ListRecyclerView, List<AccountListViewModel>,
        MVPLCEView<List<AccountListViewModel>>, AccountListPresenter> implements ListRecyclerView.OnItemClickListener {

    private AccountListAdapter mAccountListAdapter;

    private AccountFragmentIntractionListener mListener;

    private List<AccountListViewModel> mAccountListViewModels;

    public AccountListFragment() {
    }

    @Override()
    protected int getLayoutRes() {
        return R.layout.fragment_account;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (AccountFragmentIntractionListener) activity;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAccountListAdapter = new AccountListAdapter();
        mContentView.setAdapter(mAccountListAdapter);
        mViewState.apply(this, false);
    }

    @OnClick(R.id.fab)
    public void onFabClick() {
        mListener.onFragmentIntraction(AccountActivity.MODE_ADD, null);
    }


    @Override
    public boolean onItemClick(RecyclerView parent, View view, int position, long id) {
        long itemId = mAccountListAdapter.getItemId(position);
        Bundle bundle = new Bundle();
        bundle.putLong(Constants.KEY_ITEM_ID, itemId);
        mListener.onFragmentIntraction(AccountActivity.MODE_VIEW, bundle);
        return false;
    }


    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public LCEViewState<List<AccountListViewModel>, MVPLCEView<List<AccountListViewModel>>> createViewState() {
        return new LCEViewStateImpl<>();
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        if (e instanceof EmptyDataException) {
            return getString(R.string.no_account_present);
        }
        return "Unable to load Account list";
    }

    @Override
    public List<AccountListViewModel> getData() {
        return mAccountListViewModels;
    }

    @Override
    public void setData(List<AccountListViewModel> data) {
        mAccountListViewModels = data;
        showContent();
        mAccountListAdapter.setData(data);
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        getLoaderManager().initLoader(12, null, mPresenter).forceLoad();
    }


    @Override
    protected void injectDependencies(MainComponent mainComponent) {
        /*AccountActivityComponent component = DaggerAccountActivityComponent
                .builder()
                .mainComponent(mainComponent)
                .build();
        component.inject(this);*/
    }
}
