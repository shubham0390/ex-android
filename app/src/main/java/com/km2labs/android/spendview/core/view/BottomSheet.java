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

package com.km2labs.android.spendview.core.view;

import android.content.Context;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import com.km2labs.android.spendview.core.recyclerview.ListRecyclerView;
import com.km2labs.spendview.android.R;


public class BottomSheet extends CardView {

    private BottomSheetBehavior mBottomSheetBehavior;

    private ListRecyclerView mListView;

    private BottomSheetAdapter mAdapter;
    private onItemsSelectListener mItemsSelectListener;

    public BottomSheet(Context context) {
        super(context);
        init(context);
    }

    public BottomSheet(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BottomSheet(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.bottom_sheet, this, true);
        mListView = (ListRecyclerView) findViewById(R.id.action_items);
        mListView.setOnItemClickListener((parent, view, position, id) -> BottomSheet.this.notifyItemSelect(position));
    }

    public void setAdapter(BottomSheetAdapter adapter) {
        this.mAdapter = adapter;
        mListView.setAdapter(adapter);
    }

    public BottomSheetAdapter getAdapter() {
        return mAdapter;
    }

    public void show() {
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    public void hide() {
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    public void setLayoutBehaviour(BottomSheetBehavior<BottomSheet> bottomSheetBehavior) {
        mBottomSheetBehavior = bottomSheetBehavior;
    }

    public interface onItemsSelectListener {
        void onItemSelect(int position);
    }

    private boolean notifyItemSelect(int position) {
        if (mItemsSelectListener != null) {
            mItemsSelectListener.onItemSelect(position);
            return true;
        }
        return false;
    }

    public void setItemsSelectListener(onItemsSelectListener mItemsSelectListener) {
        this.mItemsSelectListener = mItemsSelectListener;
    }
}
