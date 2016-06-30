package com.mmt.shubh.expensemanager.core.view;

import android.content.Context;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.mmt.shubh.expensemanager.R;


public class BottomSheetFragment extends CardView {

    private BottomSheetBehavior mBottomSheetBehavior;

    private Fragment mFragment;

    private FrameLayout mFrameLayout;

    private FragmentManager fragmentManager;

    public BottomSheetFragment(Context context) {
        super(context);
        init(context);
    }

    public BottomSheetFragment(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BottomSheetFragment(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.bottom_sheet, this, true);
        mFrameLayout = (FrameLayout) findViewById(R.id.bottom_sheet_fragment);
    }
    public void setLayoutBehaviour(BottomSheetBehavior<BottomSheet> bottomSheetBehavior) {
        mBottomSheetBehavior = bottomSheetBehavior;
    }


    public void show() {
        fragmentManager.beginTransaction().add(R.id.bottom_sheet_fragment, mFragment).commit();
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    public void hide() {
        this.fragmentManager.beginTransaction().remove(mFragment).commit();
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    public void addFragment(FragmentManager fragmentManager, Fragment fragment) {
        this.fragmentManager = fragmentManager;
        this.mFragment = fragment;
    }

}
