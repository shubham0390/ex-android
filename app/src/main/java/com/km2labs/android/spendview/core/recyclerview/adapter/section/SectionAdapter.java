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

package com.km2labs.android.spendview.core.recyclerview.adapter.section;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.km2labs.spendview.android.R;

import java.util.ArrayList;
import java.util.List;


public abstract class SectionAdapter<D, VH extends SectionViewHolder> extends RecyclerView.Adapter<SectionViewHolder> {

    private static final int ITEM_VIEW_TYPE_SECTION = 0;
    private static final int ITEM_VIEW_TYPE_BASIC = 1;
    private static final int VISIBLE_THRESHOLD = 10;

    protected List<D> mDataList = new ArrayList<>();

    protected BaseSectionIndexer<D> mAbsSecIndexer;
    int totalItemCount, visibleItemCount, firstVisibleItem, previousTotal;
    boolean loading;

    private OnLoadMoreListener onLoadMoreListener;
    RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener()

    {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

            totalItemCount = linearLayoutManager.getItemCount();
            visibleItemCount = linearLayoutManager.getChildCount();
            firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();

            if (loading) {
                if (totalItemCount > previousTotal) {
                    loading = false;
                    previousTotal = totalItemCount;
                }
            }

            if (!loading && (totalItemCount - visibleItemCount)
                    <= (firstVisibleItem + VISIBLE_THRESHOLD)) {
                // End has been reached

                //addItem(null);
                loading = true;
                if (onLoadMoreListener != null) {
                    onLoadMoreListener.onLoadMore();
                }

            }
        }
    };

    public SectionAdapter(RecyclerView recyclerView, BaseSectionIndexer<D> baseSectionIndexer) {
        mAbsSecIndexer = baseSectionIndexer;
        recyclerView.addOnScrollListener(onScrollListener);
    }

    @Override
    public SectionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM_VIEW_TYPE_SECTION:
                return onCreateSectionViewHolder(parent, viewType);
            case ITEM_VIEW_TYPE_BASIC:
                return onCreateItemViewHolder(parent, viewType);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(SectionViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case ITEM_VIEW_TYPE_SECTION:
                onBindSectionViewHolder(holder, mAbsSecIndexer.getSection(position));
                break;
            case ITEM_VIEW_TYPE_BASIC:
                onBindItemViewHolder((VH) holder, mAbsSecIndexer.getPositionForSection(position));
                break;
        }
    }

    /**
     * Methods user needs to override
     */

    protected abstract void onBindItemViewHolder(VH holder, int position);

    protected abstract VH onCreateItemViewHolder(ViewGroup parent, int viewType);

    protected void onBindSectionViewHolder(SectionViewHolder holder, BaseSection baseSection) {
        holder.onBindSectionViewHolder(baseSection);
    }

    protected SectionViewHolder onCreateSectionViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.section, parent, false);
        return new SectionViewHolder(view);
    }

    protected SectionViewHolder onCreateFooterViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer, parent, false);
        return new ProgressViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mDataList.size() + mAbsSecIndexer.getSections().size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mAbsSecIndexer.isSectionPosition(position)) {
            return ITEM_VIEW_TYPE_SECTION;
        } else {
            return ITEM_VIEW_TYPE_BASIC;
        }
    }

    public void setLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public D getItemAtPosition(int position) {
        return mDataList.get(mAbsSecIndexer.getPositionForSection(position));
    }

    public void addData(List<D> dataList) {
        if (loading) {

        }
        mAbsSecIndexer.getSections(mDataList.size(), dataList);
        mDataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void removeItem(D item) {
        int lastIndexOfItem = mDataList.lastIndexOf(null);
        if (lastIndexOfItem != -1) {
            mDataList.remove(lastIndexOfItem);
            notifyItemRemoved(lastIndexOfItem + mAbsSecIndexer.getSections().size());
        }
    }

    public void setData(List<D> dataList) {
        invalidate();
        mAbsSecIndexer.getSections(dataList);
        mDataList.addAll(dataList);
        notifyDataSetChanged();
    }

    private void invalidate() {
        mAbsSecIndexer.clear();
        mDataList.clear();
    }

    private void addItem(Object o) {
        mDataList.add((D) o);
        notifyItemInserted(mDataList.size() - 1 + mAbsSecIndexer.getSections().size());
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public static class ProgressViewHolder extends SectionViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
        }
    }

}
