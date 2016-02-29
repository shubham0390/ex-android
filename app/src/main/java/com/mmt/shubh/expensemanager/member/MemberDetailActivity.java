package com.mmt.shubh.expensemanager.member;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.mmt.shubh.expensemanager.Constants;
import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.base.ToolBarActivity;
import com.mmt.shubh.expensemanager.dagger.component.MainComponent;
import com.mmt.shubh.expensemanager.database.content.Member;
import com.mmt.shubh.expensemanager.expense.ExpenseListView;
import com.mmt.shubh.expensemanager.expensebook.ExpenseBookListView;

import org.parceler.Parcels;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Subham Tyagi,
 * on 22/Jun/2015,
 * 5:55 PM
 * TODO:Add class comment.
 */
public class MemberDetailActivity extends ToolBarActivity {

    @Bind(R.id.backdrop)
    ImageView mImageView;

    @Bind(R.id.progress)
    ProgressBar mImageProgressBar;

    @Bind(R.id.profile_image)
    ImageView mProfileImageView;

    @Bind(R.id.member_name)
    TextView mMemberNameTextView;

    @Bind(R.id.expense_book_list)
    ExpenseBookListView mExpenseBookListView;

    @Bind(R.id.expenseList)
    ExpenseListView mExpenseListView;

    @Inject
    MemberModel mMemberModel;

    private Member mMember;

    @Nullable
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_detail);
        ButterKnife.bind(this);
        initializeToolbar();
        toggleHomeBackButton(true);
        parseIntent();
    }

    private void parseIntent() {
        mMember = Parcels.unwrap(getIntent().getParcelableExtra(Constants.EXTRA_MEMBER));
        mMemberNameTextView.setText(mMember.getMemberName());
        Glide.with(this).load(mMember.getProfilePhotoUrl()).into(mProfileImageView);
        Glide.with(this)
                .load(mMember.getCoverPhotoUrl())
                .centerCrop()
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        mImageProgressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(mImageView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mMemberModel.loadAllExpenseByMemberId(mMember.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(d -> mExpenseListView.addData(d), e -> {
                    Timber.e("Unable to load expense for member %s", e.getMessage());
                });
        mMemberModel.loadAllExpneseBooksByMemberId(mMember.getId()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(d -> mExpenseBookListView.addData(d), e -> {
                    Timber.e("Unable to load expense for member %s", e.getMessage());
                });
    }

    @Override
    protected void injectDependencies(MainComponent mainComponent) {
        DaggerMemberDetailActivityComponent.builder()
                .mainComponent(mainComponent)
                .moduleMemberDetailActivity(new ModuleMemberDetailActivity())
                .build()
                .inject(this);
    }
}
