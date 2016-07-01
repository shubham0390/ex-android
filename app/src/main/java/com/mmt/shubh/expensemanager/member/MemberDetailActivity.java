package com.mmt.shubh.expensemanager.member;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mmt.shubh.expensemanager.utils.Constants;
import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.core.base.ToolBarActivity;
import com.mmt.shubh.expensemanager.core.dagger.component.MainComponent;
import com.mmt.shubh.expensemanager.database.content.Member;
import com.mmt.shubh.expensemanager.database.content.MemberExpense;
import com.mmt.shubh.expensemanager.expense.ExpenseListView;
import com.mmt.shubh.expensemanager.expense.ExpenseListViewModel;
import com.mmt.shubh.expensemanager.expensebook.ExpenseBookGridView;
import com.mmt.shubh.expensemanager.expensebook.ExpenseBookListView;
import com.mmt.shubh.expensemanager.settings.UserSettings;
import com.mmt.shubh.expensemanager.core.view.CircleImageView;
import com.mmt.shubh.expensemanager.core.view.PaletteTransformation;
import com.mmt.shubh.expensemanager.core.view.SimpleImageView;
import com.mmt.shubh.expensemanager.utils.StringsUtils;
import com.squareup.picasso.Callback;

import org.parceler.Parcels;

import java.util.List;

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
    SimpleImageView mImageView;

    @Bind(R.id.progress)
    ProgressBar mImageProgressBar;

    @Bind(R.id.profile_image)
    CircleImageView mProfileImageView;

    @Bind(R.id.member_name)
    TextView mMemberNameTextView;

    @Bind(R.id.expense_book_list)
    ExpenseBookGridView mExpenseBookListView;

    @Bind(R.id.expense_list)
    ExpenseListView mExpenseListView;

    @Bind(R.id.expense_card)
    CardView mExpenseCardView;

    @Bind(R.id.count)
    TextView mMemberCountTextView;

    @Bind(R.id.total_expense)
    TextView mTotalExpenseTextView;

    @Bind(R.id.balance_amount)
    TextView mBalanceAmountTextView;

    @Bind(R.id.balance_amount_title)
    TextView mBalanceAmountTitleTextView;

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
        mProfileImageView.loadImage(mMember.getProfilePhotoUrl());
        mImageView.loadImage(mMember.getCoverPhotoUrl(), PaletteTransformation.instance(), new Callback() {
            @Override
            public void onSuccess() {
                mImageProgressBar.setVisibility(View.GONE);
                Bitmap bitmap = ((BitmapDrawable) mImageView.getDrawable()).getBitmap(); // Ew!
                Palette palette = PaletteTransformation.getPalette(bitmap);
                int color = palette.getLightMutedColor(getResources().getColor(android.R.color.black));
                mMemberNameTextView.setTextColor(color);
            }

            @Override
            public void onError() {
                mImageProgressBar.setVisibility(View.GONE);
            }
        });
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

        mMemberModel.getAllSharedExpense(mMember.getId(), UserSettings.getInstance().getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(d -> {
                    calculateTotalExpense(d);
                    if (d != null && d.size() > 0) {
                        mExpenseListView.setMode(ExpenseListView.MODE_MEMBER);
                        mExpenseListView.addData(d);
                    } else {
                        mExpenseListView.showEmptyMessage();
                    }
                }, e -> {
                    Timber.e("Unable to load expense for member %s", e.getMessage());
                });
        mMemberModel.getMemberExpenses(mMember.getId(), UserSettings.getInstance().getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(d -> {
                    double otherRemainingAmount = 0;
                    final double[] myRemainingAmount = {0};
                    for (MemberExpense memberExpense : d) {
                        otherRemainingAmount += memberExpense.getBalanceAmount();
                    }
                    final double finalOtherRemainingAmount = otherRemainingAmount;
                    mMemberModel.getMemberExpenses(mMember.getId(), UserSettings.getInstance().getUserId())
                            .subscribeOn(Schedulers.io())
                            .observeOn(Schedulers.io())
                            .subscribe(d2 -> {
                                for (MemberExpense memberExpense : d) {
                                    myRemainingAmount[0] += memberExpense.getBalanceAmount();
                                }
                                double diff = myRemainingAmount[0] - finalOtherRemainingAmount;
                                new Handler(Looper.getMainLooper()).post(() ->
                                        mBalanceAmountTextView.setText(StringsUtils.getLocalisedAmountString(diff)));
                            }, e2 -> {
                                Timber.e(e2.getMessage());
                            });
                }, e -> {
                    Timber.e(e.getMessage());
                });
    }

    private void calculateTotalExpense(List<ExpenseListViewModel> d) {
        double expenseAmount = 0;
        for (ExpenseListViewModel expenseListViewModel : d) {
            expenseAmount += expenseListViewModel.getExpenseAmount();
        }
        mTotalExpenseTextView.setText(StringsUtils.getLocalisedAmountString(expenseAmount));
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
