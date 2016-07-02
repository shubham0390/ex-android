package com.mmt.shubh.expensemanager.core.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.core.mvp.MVPPresenter;
import com.mmt.shubh.expensemanager.expensebook.detail.ExpenseBookActivity;
import com.mmt.shubh.expensemanager.settings.SettingsActivity;
import com.mmt.shubh.expensemanager.database.content.UserInfo;
import com.mmt.shubh.expensemanager.account.AccountActivity;
import com.mmt.shubh.expensemanager.cash.CashActivity;
import com.mmt.shubh.expensemanager.category.CategoryActivity;
import com.mmt.shubh.expensemanager.home.HomeActivity;
import com.mmt.shubh.expensemanager.home.SummaryActivity;
import com.mmt.shubh.expensemanager.settings.UserSettings;
import com.mmt.shubh.expensemanager.core.view.CircleImageView;
import com.mmt.shubh.expensemanager.core.view.SimpleImageView;

import butterknife.BindView;

/**
 * Created by Subham Tyagi,
 * on 19/Jun/2015,
 * 9:59 AM
 * TODO:Add class comment.
 */
public abstract class DrawerBaseActivity<P extends MVPPresenter> extends ToolBarActivity2<P> {

    @BindView(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;

    UserInfo mUserInfo;

    @Nullable
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (mDrawerLayout != null)
                    mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    protected void initializeNavigationDrawer() {
        initializeToolbar();
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            ab.setDisplayHomeAsUpEnabled(true);
        }
        if (mNavigationView != null) {
            setupDrawerContent(mNavigationView);
        }
        populateDrawerItems();
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                menuItem -> {
                    menuItem.setChecked(true);
                    mDrawerLayout.closeDrawers();
                    Intent intent = null;
                    boolean value = true;
                    switch (menuItem.getItemId()) {
                        case R.id.nav_home:
                            intent = new Intent(DrawerBaseActivity.this, HomeActivity.class);
                            DrawerBaseActivity.this.finish();
                            break;
                        case R.id.nav_account:
                            value = true;
                            intent = new Intent(DrawerBaseActivity.this, AccountActivity.class);
                            break;
                        case R.id.nav_distribution:
                            value = false;
                            break;
                        case R.id.nav_cash_managment:
                            intent = new Intent(DrawerBaseActivity.this, CashActivity.class);
                            break;
                        case R.id.nav_expense_book:
                            intent = new Intent(DrawerBaseActivity.this, ExpenseBookActivity.class);
                            break;
                        case R.id.nav_summary:
                            intent = new Intent(DrawerBaseActivity.this, SummaryActivity.class);
                            break;
                        case R.id.nav_app_settings:
                            intent = new Intent(DrawerBaseActivity.this, SettingsActivity.class);
                            break;
                        case R.id.nav_about_app:
                            intent = new Intent(DrawerBaseActivity.this, CategoryActivity.class);
                            break;
                    }
                    if (value)
                        DrawerBaseActivity.this.startActivity(intent);
                    else
                        Toast.makeText(DrawerBaseActivity.this, "Not Implemented yet", Toast.LENGTH_SHORT).show();
                    return true;

                });
    }

    protected void populateDrawerItems() {

        mUserInfo = UserSettings.getInstance().getUserInfo();
        if (mUserInfo != null) {
            View view = mNavigationView.getHeaderView(0);
            CircleImageView profileImage = (CircleImageView) view.findViewById(R.id.profile_image);
            SimpleImageView coverImage = (SimpleImageView) view.findViewById(R.id.cover_image_view);
            TextView displayName = (TextView) view.findViewById(R.id.account_name);
            TextView emailId = (TextView) view.findViewById(R.id.email_id);

            if (!TextUtils.isEmpty(mUserInfo.getCoverPhotoUrl())) {
                coverImage.loadImage(mUserInfo.getCoverPhotoUrl());
            }

            if (!TextUtils.isEmpty(mUserInfo.getProfilePhotoUrl())) {
                profileImage.loadImage(mUserInfo.getCoverPhotoUrl());
            }

            displayName.setText(mUserInfo.getDisplayName());
            emailId.setText(mUserInfo.getEmailAddress());
        }
    }

}
