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

package com.enfle.spendview.core.view;

import android.content.Context;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.enfle.spendview.R;


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
