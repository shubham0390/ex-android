package com.mmt.shubh.expensemanager.ui.activity;

import android.content.Intent;
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
import com.mmt.shubh.expensemanager.database.content.UserInfo;

/**
 * Created by Subham Tyagi,
 * on 19/Jun/2015,
 * 9:59 AM
 * TODO:Add class comment.
 */
public class DrawerBaseActivity extends ToolBarActivity {


    private DrawerLayout mDrawerLayout;


    UserInfo mUserInfo;

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
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        populateDrawerItems();
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        Intent intent = null;
                        switch (menuItem.getItemId()) {
                            case R.id.nav_home:
                                break;
                            case R.id.nav_account:
                                intent =  new Intent(getApplicationContext(),AccountActivity.class);
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
                        startActivity(intent);
                        return true;

                    }
                });
    }

    private void populateDrawerItems() {
        if (mUserInfo != null) {
            mUserInfo = getIntent().getParcelableExtra("Account");
            ImageView profileImage = (ImageView) findViewById(R.id.profile_image);
            ImageView coverImage = (ImageView) findViewById(R.id.cover_image_view);
            TextView displayName = (TextView) findViewById(R.id.account_name);
            TextView emailId = (TextView) findViewById(R.id.email_id);

            if (!TextUtils.isEmpty(mUserInfo.getCoverPhotoUrl())) {
                Glide.with(this)
                        .load(mUserInfo.getCoverPhotoUrl())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(coverImage);

            }

            Glide.with(this).load(mUserInfo.getProfilePhotoUrl())
                    .placeholder(R.drawable.member_avatar_white_48dp)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(profileImage);

            displayName.setText(mUserInfo.getDisplayName());
            emailId.setText(mUserInfo.getEmailAddress());
        }
    }


}
