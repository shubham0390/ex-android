package com.mmt.shubh.expensemanager.expensebook.detail;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.dagger.component.MainComponent;
import com.mmt.shubh.expensemanager.database.content.ExpenseBook;
import com.mmt.shubh.expensemanager.base.ToolBarActivity;
import com.mmt.shubh.expensemanager.mvp.lce.MVPLCEView;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ExpenseBookActivity extends ToolBarActivity implements MVPLCEView<List<ExpenseBook>> {

    @Inject
    ExpenseBookActivityPresenter mPresenter;

    @Bind(R.id.viewPager)
    ViewPager mViewPager;

    @Bind(R.id.tabLayout)
    TabLayout mTabLayout;

    ExpenseBookFragmentAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_book_detail);
        ButterKnife.bind(this);
        initializeToolbar();
        getSupportActionBar().setElevation(0);
        mPresenter.attachView(this);
        setToolbar();
        setTabs();
    }

    private void setTabs() {
        mViewPager.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        loadData(false);
    }


    private void setToolbar() {
        toggleHomeBackButton(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_expense_book_detail, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_settings:
                //installSettingFragment();
                break;
           /* case R.id.edit:
                Utilities.hideKeyboard(this);
                Intent intent = new Intent(this, ExpenseBookAddUpdateActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constants.KEY_EXPENSE_BOOK, Parcels.wrap(mExpenseBook));
                intent.putExtras(bundle);
                startActivity(intent);*/
        }
        return true;
    }

    @Override
    protected void injectDependencies(MainComponent mainComponent) {
        ExpenseBookActivityComponent component = DaggerExpenseBookActivityComponent.builder()
                .expenseBookActivityModule(new ExpenseBookActivityModule())
                .mainComponent(mainComponent)
                .build();
        component.inject(this);
    }

   /* private void installSettingFragment() {
        ExpenseBookSettingFragment fragment = new ExpenseBookSettingFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.KEY_EXPENSE_BOOK, Parcels.wrap(mExpenseBook));
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.settings, fragment).commit();
    }*/

    @Override
    public void showLoading(boolean pullToRefresh) {

    }

    @Override
    public void showContent() {

    }

    @Override
    public void showError(Throwable e, boolean pullToRefresh) {

    }

    @Override
    public void setData(List<ExpenseBook> data) {
        adapter = new ExpenseBookFragmentAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        adapter.addData(data);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        mPresenter.loadExpenseBookList();
    }
}
