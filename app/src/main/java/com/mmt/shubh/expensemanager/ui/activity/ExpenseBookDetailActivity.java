package com.mmt.shubh.expensemanager.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mmt.shubh.expensemanager.Constants;
import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.database.content.ExpenseBook;
import com.mmt.shubh.expensemanager.ui.activity.base.ToolBarActivity;
import com.mmt.shubh.expensemanager.ui.adapters.ExpenseBookDetailFragmentAdapter;
import com.mmt.shubh.expensemanager.ui.fragment.base.IFragmentSwitcher;
import com.mmt.shubh.expensemanager.ui.fragment.expensebook.ExpenseBookSettingFragment;
import com.mmt.shubh.expensemanager.utils.Utilities;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ExpenseBookDetailActivity extends ToolBarActivity implements
        ViewPager.OnPageChangeListener, IFragmentSwitcher {

    private ExpenseBook mExpenseBook;

    @Bind(R.id.logo)
    ImageView mLogoImageView;

    @Bind(R.id.expense_book_title)
    TextView mTitleTextView;

    @Bind(R.id.subtitle)
    TextView mSubtitleTextView;

    @Bind(R.id.viewpager)
    ViewPager pager;

    @Bind(R.id.tabs)
    TabLayout tabs;

    private CharSequence Titles[] = {"SUMMARY", "DISTRIBUTION"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_book_detail);

        ButterKnife.bind(this);
        initializeToolbar();

        mExpenseBook = Parcels.unwrap(getIntent().getParcelableExtra(Constants.KEY_EXPENSE_BOOK));
        setToolbar();
        setTabs();
    }

    private void setTabs() {
        int numberOfTabs = 2;
        ExpenseBookDetailFragmentAdapter adapter = new ExpenseBookDetailFragmentAdapter(getSupportFragmentManager(), numberOfTabs, Titles);
        pager.setAdapter(adapter);
        tabs.setupWithViewPager(pager);
        pager.addOnPageChangeListener(this);
    }

    private void setToolbar() {
        toggleHomeBackButton(true);
        mTitleTextView.setText(mExpenseBook.getName());
        mSubtitleTextView.setText(mExpenseBook.getType());
        loadLogoImage();
    }

    private void loadLogoImage() {
        ColorGenerator generator = ColorGenerator.MATERIAL;
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(String.valueOf(mExpenseBook.getName().charAt(0)), generator.getRandomColor());

        if (TextUtils.isEmpty(mExpenseBook.getProfileImagePath())) {
            Glide.with(this)
                    .load(mExpenseBook.getProfileImagePath())
                    .placeholder(drawable)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(mLogoImageView);
        } else {
            mLogoImageView.setImageDrawable(drawable);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_expense_book_detail, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (mExpenseBook.getType().equals("Private")) {
            menu.removeItem(R.id.edit);
        }
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
                installSettingFragment();
                break;
            case R.id.edit:
                Utilities.hideKeyboard(this);
                Intent intent = new Intent(this, ExpenseBookAddUpdateActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constants.KEY_EXPENSE_BOOK, Parcels.wrap(mExpenseBook));
                intent.putExtras(bundle);
                startActivity(intent);
        }
        return true;
    }

    private void installSettingFragment() {
        ExpenseBookSettingFragment fragment = new ExpenseBookSettingFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.KEY_EXPENSE_BOOK, Parcels.wrap(mExpenseBook));
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.settings, fragment).commit();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void replaceFragment(String tag, Bundle bundle) {

    }

    @Override
    public void replaceFragment(int id, Bundle bundle) {

    }

    @Override
    public void removeFragment(String tag, Bundle bundle) {

    }

    @Override
    public void removeFragment(int id, Bundle bundle) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(id);
        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
    }
}
