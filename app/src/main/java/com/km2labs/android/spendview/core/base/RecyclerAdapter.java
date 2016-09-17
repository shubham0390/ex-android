package com.km2labs.android.spendview.core.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;

import com.km2labs.android.spendview.core.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by : Subham Tyagi
 * Created on :  28/08/16.
 */

public abstract class RecyclerAdapter extends RecyclerView.Adapter {

    protected Map<Class, Integer> mClassVsViewTypeLookup = new HashMap<>();
    protected SparseArray<RecyclerItemView> mViewTypeVsViewItemLookup = new SparseArray<>();
    private final AtomicInteger NEXT_ID = new AtomicInteger(1);

    protected List<RecyclerItemView> mItems;

    public RecyclerAdapter() {
        mItems = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


    @Override
    public int getItemViewType(int position) {
        if (CollectionUtils.isEmpty(mItems)) {
            return 0;
        }

        RecyclerItemView item = mItems.get(position);
        return mClassVsViewTypeLookup.get(item.getClass());
    }

    private void computeViewType() {
        if (mItems == null || CollectionUtils.isEmpty(mItems))
            return;

        for (RecyclerItemView recyclableViewItem : mItems) {
            Class classs = recyclableViewItem.getClass();
            if (!mClassVsViewTypeLookup.containsKey(classs)) {
                mClassVsViewTypeLookup.put(classs, NEXT_ID.get());
                mViewTypeVsViewItemLookup.put(NEXT_ID.getAndIncrement(), recyclableViewItem);
            }
        }
    }

    public void setItems(@NonNull List<RecyclerItemView> items) {
        mItems = items;
        computeViewType();
        notifyDataSetChanged();
    }

    public void addItems(@NonNull List<RecyclerItemView> items) {
        int oldItemCount = mItems.size();
        mItems.addAll(items);
        computeViewType();
        //Notify only changed items
        notifyItemRangeInserted(oldItemCount, items.size());
    }

    public List<RecyclerItemView> getItems() {
        return mItems;
    }

    public <T> T getItem(int position) {
        return (T) mItems.get(position);
    }
}
