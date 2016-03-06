package com.mmt.shubh.expensemanager.member;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.mmt.shubh.expensemanager.Constants;
import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.base.ToolBarActivity;
import com.mmt.shubh.expensemanager.dagger.component.MainComponent;
import com.mmt.shubh.expensemanager.database.content.Member;
import com.mmt.shubh.expensemanager.expense.ExpenseListView;
import com.mmt.shubh.expensemanager.expensebook.ExpenseBookListView;
import com.mmt.shubh.expensemanager.settings.UserSettings;
import com.mmt.shubh.expensemanager.utils.Utilities;

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

    @Bind(R.id.expense_list)
    ExpenseListView mExpenseListView;
    @Bind(R.id.expense_card)
    CardView mExpenseCardView;

    @Bind(R.id.count)
    TextView mMemberCountTextView;

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
        Glide.with(this)
                .load(mMember.getProfilePhotoUrl())
                .fitCenter()
                //.placeholder(R.drawable.member_avatar_white_48dp)
                .override((int) Utilities.pxFromDp(this, 60), (int) Utilities.pxFromDp(this, 60))
                .into(mProfileImageView);

        Glide.with(this)
                .load(mMember.getCoverPhotoUrl())
                .asBitmap()
                .override((int) Utilities.pxFromDp(this, 256), (int) Utilities.pxFromDp(this, 256))
                .centerCrop()
                .into(new BitmapImageViewTarget(mImageView) {
                          @Override
                          public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                              super.onResourceReady(resource, glideAnimation);
                              Palette.Builder builder = new Palette.Builder(resource);
                              builder.generate(listener -> {
                                  mImageProgressBar.setVisibility(View.GONE);
                                  int color = listener.getLightMutedColor(getResources().getColor(android.R.color.black));
                                  mMemberNameTextView.setTextColor(color);
                              });
                          }
                      }
                );
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

        mMemberModel.loadAllExpneseBooksByMemberId(mMember.getId()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(d -> {
                    if (d.size() < 0) {
                        mExpenseCardView.setVisibility(View.GONE);
                    } else {
                        mMemberCountTextView.setText(String.valueOf(d.size()));
                        mExpenseBookListView.setMode(ExpenseBookListView.MODE_MEMBER);
                        mExpenseBookListView.addData(d);
                    }
                }, e -> {
                    Timber.e("Unable to load expense for member %s", e.getMessage());
                });

        mMemberModel.getAllSharedExpense(mMember.getId(), UserSettings.getInstance().getUserId()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(d -> mExpenseListView.addData(d), e -> {
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
