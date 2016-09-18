/*
 * Copyright (c) 2016. . The Km2Labs Project
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.km2labs.android.spendview.expensebook.setting;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.km2labs.android.spendview.core.mvp.MVPFragment;
import com.km2labs.android.spendview.database.content.ExpenseBook;
import com.km2labs.android.spendview.database.content.Member;
import com.km2labs.android.spendview.member.list.MemberListFragment;
import com.km2labs.android.spendview.utils.Constants;
import com.km2labs.expenseview.android.R;

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
    public void onOwnerLoaded(Member member) {
        mCreatedByTextView.setText(String.format(getString(R.string.created_by), member.getMemberName()));
        //mCreatedOnTextView.setText(String.format(getString(R.string.created_on), mExpenseBook.getCreationTime()));
    }
}
