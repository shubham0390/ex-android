package com.mmt.shubh.expensemanager.expensebook.setting;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.mmt.shubh.expensemanager.core.dagger.module.FragmentModule;
import com.mmt.shubh.expensemanager.core.mvp.MVPFragment;
import com.mmt.shubh.expensemanager.utils.Constants;
import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.core.dagger.component.MainComponent;
import com.mmt.shubh.expensemanager.database.content.ExpenseBook;
import com.mmt.shubh.expensemanager.database.content.Member;
import com.mmt.shubh.expensemanager.member.MemberListFragment;

import org.parceler.Parcels;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExpenseBookSettingFragment extends MVPFragment<ExpenseBookSettingPresenter>
        implements IExpenseBookSettingView {

    public static final String TAG = "settingFragment";

    @BindView(R.id.created_by)
    TextView mCreatedByTextView;

    @BindView(R.id.created_on)
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
        mExpenseBook = Parcels.unwrap(getArguments().getParcelable(Constants.EXTRA_EXPENSE_BOOK));
        installMemberListFragment();
        mPresenter.loadOwnerDetails(mExpenseBook.getOwnerId());
    }


    private void installMemberListFragment() {
        Fragment fragment = new MemberListFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(Constants.EXTRA_EXPENSE_BOOK_ID, mExpenseBook.getId());
        bundle.putInt(Constants.EXTRA_TYPE, MemberListFragment.TYPE_EXPENSE_BOOK);
        bundle.putBoolean(Constants.EXTRA_DELETE_MEMBER, !mExpenseBook.getType().equals(ExpenseBook.TYPE_PERSONAL));
        fragment.setArguments(bundle);
        getFragmentManager().beginTransaction().add(R.id.member_list, fragment).commit();
    }

    @Override
    protected void injectDependencies(MainComponent mainComponent) {
        mainComponent.fragmentComponent(new FragmentModule()).inject(this);
    }

    @Override
    public void onOwnerLoaded(Member member) {
        mCreatedByTextView.setText(String.format(getString(R.string.created_by), member.getMemberName()));
        mCreatedOnTextView.setText(String.format(getString(R.string.created_on), mExpenseBook.getCreationTime()));
    }
}
