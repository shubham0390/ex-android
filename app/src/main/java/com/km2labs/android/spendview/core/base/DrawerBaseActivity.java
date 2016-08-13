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

package com.km2labs.android.spendview.core.base;

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

import com.km2labs.android.spendview.account.AccountActivity;
import com.km2labs.android.spendview.category.CategoryActivity;
import com.km2labs.android.spendview.core.mvp.MVPPresenter;
import com.km2labs.android.spendview.core.view.CircleImageView;
import com.km2labs.android.spendview.core.view.SimpleImageView;
import com.km2labs.android.spendview.database.content.User;
import com.km2labs.android.spendview.expensebook.detail.ExpenseBookActivity;
import com.km2labs.android.spendview.home.HomeActivity;
import com.km2labs.android.spendview.settings.UserSettings;
import com.km2labs.spendview.android.R;
import com.km2labs.android.spendview.home.SummaryActivity;
import com.km2labs.android.spendview.settings.SettingsActivity;

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

    User mUser;

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
                            value = false;
                            // intent = new Intent(DrawerBaseActivity.this, CashActivity.class);
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

        mUser = UserSettings.getInstance().getUser();
        if (mUser != null) {
            View view = mNavigationView.getHeaderView(0);
            CircleImageView profileImage = (CircleImageView) view.findViewById(R.id.profile_image);
            SimpleImageView coverImage = (SimpleImageView) view.findViewById(R.id.cover_image_view);
            TextView displayName = (TextView) view.findViewById(R.id.account_name);
            TextView emailId = (TextView) view.findViewById(R.id.email_id);

            if (!TextUtils.isEmpty(mUser.getCoverPhotoUrl())) {
                coverImage.loadImage(mUser.getCoverPhotoUrl());
            }

            if (!TextUtils.isEmpty(mUser.getProfilePhotoUrl())) {
                profileImage.loadImage(mUser.getCoverPhotoUrl());
            }

            displayName.setText(mUser.getDisplayName());
            emailId.setText(mUser.getEmailAddress());
        }
    }

}
