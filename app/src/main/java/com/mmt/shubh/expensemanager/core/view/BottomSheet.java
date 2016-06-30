package com.mmt.shubh.expensemanager.core.view;

import android.content.Context;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.recyclerviewlib.ListRecyclerView;


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
