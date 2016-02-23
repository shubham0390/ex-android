package com.mmt.shubh.recyclerviewlib;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by Subham Tyagi,
 * on 10/Jul/2015,
 * 11:53 AM
 * TODO:Add class comment.
 */
public class HorizontalRecyclerView extends RecyclerView {

    private ItemClickSupport mItemClickSupport;

    public HorizontalRecyclerView(Context context) {
        super(context);
        init(context);
    }

    public HorizontalRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HorizontalRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        mItemClickSupport = ItemClickSupport.addTo(this);
        setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        addItemDecoration(new DividerItemDecoration(context));
        setItemAnimator(new DefaultItemAnimator());
    }

    public void setOnItemClickListener(ListRecyclerView.OnItemClickListener onItemClickListener) {
        mItemClickSupport.setOnItemClickListener(onItemClickListener);
    }

    public void setOnItemLongClickListener(ListRecyclerView.OnItemLongClickListener longClickListener) {
        mItemClickSupport.setOnItemLongClickListener(longClickListener);
    }
}