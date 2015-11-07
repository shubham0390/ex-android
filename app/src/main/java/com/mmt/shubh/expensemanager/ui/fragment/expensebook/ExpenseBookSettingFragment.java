package com.mmt.shubh.expensemanager.ui.fragment.expensebook;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.mmt.shubh.expensemanager.Constants;
import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.dagger.component.MainComponent;
import com.mmt.shubh.expensemanager.database.content.ExpenseBook;
import com.mmt.shubh.expensemanager.ui.activity.ExpenseBookAddUpdateActivity;
import com.mmt.shubh.expensemanager.ui.dagger.component.DaggerExpenseBookDetailComponent;
import com.mmt.shubh.expensemanager.ui.dagger.component.ExpenseBookDetailComponent;
import com.mmt.shubh.expensemanager.ui.fragment.MemberListFragment;
import com.mmt.shubh.expensemanager.ui.fragment.base.IFragmentSwitcher;
import com.mmt.shubh.expensemanager.ui.dagger.module.SettingFragmentModule;
import com.mmt.shubh.expensemanager.ui.mvp.SupportMVPFragment;
import com.mmt.shubh.expensemanager.ui.presenters.ExpenseBookSettingPresenter;
import com.mmt.shubh.expensemanager.ui.views.IExpenseBookSettingView;

import org.parceler.Parcels;

import butterknife.Bind;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExpenseBookSettingFragment extends SupportMVPFragment<IExpenseBookSettingView, ExpenseBookSettingPresenter>
        implements IExpenseBookSettingView {

    public static final String TAG = "settingFragment";

    IFragmentSwitcher mIFragmentSwitcher;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.created_by)
    TextView mCreatedByTextView;

    @Bind(R.id.created_on)
    TextView mCreatedOnTextView;


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
        mExpenseBook = Parcels.unwrap(getArguments().getParcelable(Constants.KEY_EXPENSE_BOOK));

        installMemberListFragment();
        setupToolbar();

        mCreatedByTextView.setText(String.format(getString(R.string.created_by), mExpenseBook.getOwner().getMemberName()));
        mCreatedOnTextView.setText(String.format(getString(R.string.created_on), mExpenseBook.getCreationTime()));

        if (!mExpenseBook.getType().equals("Private"))
            addMenu();

    }

    private void setupToolbar() {
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        mToolbar.setTitle(R.string.action_settings);
        mToolbar.setNavigationOnClickListener(view -> mIFragmentSwitcher.removeFragment(R.id.settings, null));
    }

    private void addMenu() {

        mToolbar.inflateMenu(R.menu.menu_fragment_setting_expense_book);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);

        mToolbar.setNavigationOnClickListener(view -> mIFragmentSwitcher.removeFragment(R.id.settings, null));

        mToolbar.setOnMenuItemClickListener(item -> {
            Intent intent = new Intent(getActivity(), ExpenseBookAddUpdateActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constants.KEY_EXPENSE_BOOK, Parcels.wrap(mExpenseBook));
            intent.putExtras(bundle);
            intent.setAction(Constants.ACTION_ADD_MEMBERS);
            startActivity(intent);
            return true;
        });
    }

    private void installMemberListFragment() {
        Fragment fragment = new MemberListFragment();

        Bundle bundle = new Bundle();
        bundle.putLong(Constants.KEY_EXPENSE_BOOK_ID, mExpenseBook.getId());

        bundle.putBoolean(Constants.KEY_DELETE_MEMBER, !mExpenseBook.getType().equals("Private"));

        fragment.setArguments(bundle);

        getFragmentManager().beginTransaction().add(R.id.member_list, fragment).commit();
    }


    @Override
    protected ExpenseBookSettingPresenter getPresenter() {
        return new ExpenseBookSettingPresenter();
    }

    @Override
    protected void injectDependencies(MainComponent mainComponent) {
        ExpenseBookDetailComponent component = DaggerExpenseBookDetailComponent.builder()
                .settingFragmentModule(new SettingFragmentModule())
                .mainComponent(mainComponent).build();
        component.inject(this);
    }
}
