package com.mmt.shubh.expensemanager.ui.fragment.expensebook;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.mmt.shubh.expensemanager.Constants;
import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.database.content.ExpenseBook;
import com.mmt.shubh.expensemanager.ui.fragment.MemberListFragment;
import com.mmt.shubh.expensemanager.ui.fragment.base.IFragmentSwitcher;
import com.mmt.shubh.expensemanager.ui.mvp.SupportMVPFragment;
import com.mmt.shubh.expensemanager.ui.presenters.ExpenseBookSettingPresenter;
import com.mmt.shubh.expensemanager.ui.views.IExpenseBookSettingView;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExpenseBookSettingFragment extends SupportMVPFragment<IExpenseBookSettingView, ExpenseBookSettingPresenter>
        implements IExpenseBookSettingView {

    public static final String TAG = "settingFragment";

    IFragmentSwitcher mIFragmentSwitcher;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    private ExpenseBook mExpenseBook;

    public ExpenseBookSettingFragment() {
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_expense_book_setting;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mIFragmentSwitcher = (IFragmentSwitcher) getActivity();
        mExpenseBook = getArguments().getParcelable(Constants.KEY_EXPENSE_BOOK);
        installMemberListFragment();
        mToolbar.inflateMenu(R.menu.menu_fragment_setting_expense_book);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIFragmentSwitcher.removeFragment(R.id.settings, null);
            }
        });
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // TODO: 9/16/2015 handle add member
                // Intent intent =  new Intent(getActivity(),new AddM)
                return true;
            }
        });

    }

    private void installMemberListFragment() {
        Fragment fragment = new MemberListFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(Constants.KEY_EXPENSE_BOOK_ID, mExpenseBook.getId());
        bundle.putBoolean(Constants.KEY_DELETE_MEMBER,true);
        fragment.setArguments(bundle);
        getFragmentManager().beginTransaction().add(R.id.member_list, fragment).commit();
    }

    /*@OnClick(R.id.close_self)
    void closeSelf() {

    }*/

    @Override
    protected ExpenseBookSettingPresenter getPresenter() {
        return new ExpenseBookSettingPresenter();
    }

}
