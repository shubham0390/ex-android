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

package com.km2labs.android.spendview.member;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.km2labs.android.spendview.core.base.RecyclerViewFragment;
import com.km2labs.android.spendview.database.content.Member;
import com.km2labs.android.spendview.member.detail.MemberDetailActivity;
import com.km2labs.android.spendview.utils.Constants;
import com.km2labs.expenseview.android.R;

import org.parceler.Parcels;

import java.util.List;

import static butterknife.ButterKnife.findById;

/**
 * Created by Subham Tyagi,
 * on 22/Jun/2015,
 * 11:30 PM
 * TODO:Add class comment.
 */
public class MemberListFragment extends RecyclerViewFragment {

    public static final int TYPE_EXPENSE_BOOK = 1;
    public static final int TYPE_MEMBER = 2;
    private List<Member> mMemberList;
    private MemberAdapter mListAdapter;
    private boolean mIsMemberDeletable;
    private Bundle mArguments;
    private int mType = 1;
    private long mExpenseBookId;
    private TextView mDailogMessageTextView;
    private Button mRemoveButton;
    private Button mCancelButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mArguments = getArguments();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mArguments != null) {
            mIsMemberDeletable = mArguments.getBoolean(Constants.EXTRA_DELETE_MEMBER);
            mType = mArguments.getInt(Constants.EXTRA_TYPE, TYPE_MEMBER);
            mExpenseBookId = mArguments.getLong(Constants.EXTRA_EXPENSE_BOOK_ID);
        }
        mListAdapter = new MemberAdapter();
        mListAdapter.setCanDelete(mIsMemberDeletable);
    }

    private void onMemberItemClick(int position) {
        Intent intent = new Intent(getActivity(), MemberDetailActivity.class);
        Member member = mListAdapter.getItem(position);
        intent.putExtra(Constants.EXTRA_MEMBER, Parcels.wrap(member));
        startActivity(intent);
    }

    public void onMemberDeleteEvent(MemberDeleteEvent event) {
        AlertDialog alertDialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.dialog_titile_delete_member);
        builder.setNegativeButton(R.string.cancel, (dialog, which) -> {
            dialog.dismiss();
        });
        builder.setPositiveButton(R.string.delete, (dialog, which) -> {
            mDailogMessageTextView.setText(R.string.message_removing_member);
            mRemoveButton.setVisibility(View.GONE);
            mCancelButton.setVisibility(View.GONE);

        });
        alertDialog = builder.create();
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setContentView(R.layout.member_delete_dialog);
        mDailogMessageTextView = findById(alertDialog, R.id.message);
        mRemoveButton = findById(alertDialog, R.id.removeMemberButton);
        mCancelButton = findById(alertDialog, R.id.cancelButton);
        alertDialog.show();
    }


    @Override
    protected LayoutManagerType getLayoutManagerType() {
        return null;
    }

    @Override
    protected void loadList() {

    }
}
