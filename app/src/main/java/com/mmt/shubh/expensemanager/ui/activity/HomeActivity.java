package com.mmt.shubh.expensemanager.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.ui.adapters.LibraryFragmentAdapter;

public class HomeActivity extends DrawerBaseActivity implements ViewPager.OnPageChangeListener {
    private ViewPager pager;
    private LibraryFragmentAdapter adapter;
    private TabLayout tabs;
    private CharSequence Titles[] = {"DISTRIBUTION", "SUMMARY"};
    private int NumberOfTabs = 2;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        initializeToolbar();
        adapter = new LibraryFragmentAdapter(getSupportFragmentManager(), NumberOfTabs, Titles);

        pager = (ViewPager) findViewById(R.id.viewpager);
        pager.setAdapter(adapter);

        tabs = (TabLayout) findViewById(R.id.tabs);

        tabs.setupWithViewPager(pager);
        pager.addOnPageChangeListener(this);

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
