package com.km2labs.android.spendview.core.recyclerview.adapter.section;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.km2labs.shubh.expensemanager.R;


public class SectionViewHolder extends RecyclerView.ViewHolder {

    TextView mNameTextView;
    private View mParent;

    public SectionViewHolder(View itemView) {
        super(itemView);
        mParent = itemView;
        if (this.getClass().equals(SectionViewHolder.class)) {
            mParent = itemView;
            mNameTextView = (TextView) mParent.findViewById(R.id.sectionTitle);
        }
    }

    public void onBindSectionViewHolder(BaseSection section) {
        if (this.getClass().equals(SectionViewHolder.class))
            mNameTextView.setText(section.mSectionName);
    }
}