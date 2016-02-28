package com.mmt.shubh.expensemanager.home;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.base.DrawerBaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeActivity extends DrawerBaseActivity implements ViewPager.OnPageChangeListener {

    @Bind(R.id.viewpager)
    ViewPager pager;

    @Bind(R.id.tabs)
    TabLayout tabs;

    private CharSequence Titles[] = {"DISTRIBUTION", "SUMMARY"};

    private int NumberOfTabs = 2;
    private HomeFragmentAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        initializeNavigationDrawer();
        adapter = new HomeFragmentAdapter(getSupportFragmentManager(), NumberOfTabs, Titles);
        pager.setAdapter(adapter);
        tabs.setupWithViewPager(pager);
        pager.addOnPageChangeListener(this);
        setTitle(R.string.home_title);
    }

    @Override
    protected void onResume() {
        super.onResume();
      // mNavigationView.setCheckedItem(R.id.home);
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
      /*  mTracker.setScreenName("Image~" + getClass().getSimpleName());
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());*/
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
