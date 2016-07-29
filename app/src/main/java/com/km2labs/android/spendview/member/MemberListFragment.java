package com.km2labs.android.spendview.member;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.km2labs.shubh.expensemanager.R;
import com.km2labs.android.spendview.core.dagger.component.MainComponent;
import com.km2labs.android.spendview.core.dagger.module.FragmentModule;
import com.km2labs.android.spendview.core.mvp.lce.LCEViewState;
import com.km2labs.android.spendview.core.mvp.lce.LCEViewStateImpl;
import com.km2labs.android.spendview.core.mvp.lce.MVPLCEView;
import com.km2labs.android.spendview.core.mvp.lce.SupportMVPLCEFragment;
import com.km2labs.android.spendview.core.recyclerview.ListRecyclerView;
import com.km2labs.android.spendview.database.content.Member;
import com.km2labs.android.spendview.member.detail.MemberDetailActivity;
import com.km2labs.android.spendview.utils.Constants;

import org.parceler.Parcels;

import java.util.List;

import static butterknife.ButterKnife.findById;

/**
 * Created by Subham Tyagi,
 * on 22/Jun/2015,
 * 11:30 PM
 * TODO:Add class comment.
 */
public class MemberListFragment extends SupportMVPLCEFragment<ListRecyclerView, List<Member>, MVPLCEView<List<Member>>,
        MemberListFragmentPresenter> {

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
        setRetainInstance(true);
        setRestoringViewState(true);
        mArguments = getArguments();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_member_list;
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
        mContentView.setAdapter(mListAdapter);
        setupListener();
        getViewState().apply(this, true);
    }

    private void setupListener() {
        mContentView.setOnItemClickListener((parent, view, position, id) -> {
            onMemberItemClick(position);
            return true;
        });
    }

    private void onMemberItemClick(int position) {
        Intent intent = new Intent(getActivity(), MemberDetailActivity.class);
        Member member = mListAdapter.getItem(position);
        intent.putExtra(Constants.EXTRA_MEMBER, Parcels.wrap(member));
        startActivity(intent);
    }

    @Override
    public LCEViewState<List<Member>, MVPLCEView<List<Member>>> createViewState() {
        return new LCEViewStateImpl<>();
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return "Unable to load members";
    }

    @Override
    public List<Member> getData() {
        return mMemberList;
    }

    @Override
    public void setData(List<Member> data) {
        showContent();
        mMemberList = data;
        mListAdapter.setMembers(data);

    }

    @Override
    public void loadData(boolean pullToRefresh) {
        showLoading(true);
        switch (mType) {
            case TYPE_EXPENSE_BOOK:
                mPresenter.loadAllMembersByExpenseBook(mExpenseBookId);
                break;
            case TYPE_MEMBER:
                mPresenter.loadAllMembers();
                break;
        }
    }

    @Override
    protected void injectDependencies(MainComponent mainComponent) {
        mainComponent.fragmentComponent(new FragmentModule()).inject(this);
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
            mPresenter.deleteMemberFromExpenseBook(event.mMemberId, mExpenseBookId);

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
    public void showError(Throwable e, boolean pullToRefresh) {
        if (e instanceof SQLiteConstraintException) {
            mDailogMessageTextView.setText(R.string.error_message_member_cannot_be_removed);
        } else {
            super.showError(e, pullToRefresh);
        }
    }
}
