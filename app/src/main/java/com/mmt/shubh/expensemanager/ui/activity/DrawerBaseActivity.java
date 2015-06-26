package com.mmt.shubh.expensemanager.ui.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.database.content.Account;

/**
 * Created by Subham Tyagi,
 * on 19/Jun/2015,
 * 9:59 AM
 * TODO:Add class comment.
 */
public class DrawerBaseActivity extends ToolBarActivity {

    private Toolbar mToolbar;

    private DrawerLayout mDrawerLayout;

    private boolean mToolbarInitialized;

    Account mAccount;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (!mToolbarInitialized) {
            throw new IllegalStateException("You must run super.initializeToolbar at " +
                    "the end of your onCreate method");
        }
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

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        mToolbar.setTitle(title);
    }

    @Override
    public void setTitle(int titleId) {
        super.setTitle(titleId);
        mToolbar.setTitle(titleId);
    }

    protected void initializeToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar == null) {
            throw new IllegalStateException("Layout is required to include a Toolbar with id " +
                    "'toolbar'");
        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        setSupportActionBar(mToolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            ab.setDisplayHomeAsUpEnabled(true);
        }
        mToolbarInitialized = true;
        populateDrawerItems();
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        switch (menuItem.getItemId()) {
                            case R.id.nav_home:
                                break;
                            case R.id.nav_distribution:
                                break;
                            case R.id.nav_expense_book:
                                break;
                            case R.id.nav_summary:
                                break;
                            case R.id.nav_app_settings:
                                break;
                            case R.id.nav_about_app:
                                break;
                        }
                        return true;
                    }
                });
    }

    private void populateDrawerItems() {
        mAccount = getIntent().getParcelableExtra("Account");
        ImageView profileImage = (ImageView) findViewById(R.id.profile_image);
        ImageView coverImage = (ImageView) findViewById(R.id.cover_image_view);
        TextView displayName = (TextView) findViewById(R.id.account_name);
        TextView emailId = (TextView) findViewById(R.id.email_id);

        if (!TextUtils.isEmpty(mAccount.getCoverPhotoUrl())) {
            Glide.with(this)
                    .load(mAccount.getCoverPhotoUrl())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(coverImage);

        }

        Glide.with(this).load(mAccount.getProfilePhotoUrl())
                .placeholder(R.drawable.member_avatar_white_48dp)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(profileImage);

        displayName.setText(mAccount.getDisplayName());
        emailId.setText(mAccount.getEmailAddress());

    }


}
