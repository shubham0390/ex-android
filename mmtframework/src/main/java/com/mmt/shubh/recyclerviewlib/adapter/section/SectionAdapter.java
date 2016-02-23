package com.mmt.shubh.recyclerviewlib.adapter.section;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.mmt.shubh.recyclerviewlib.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shubham on 12/2/15.
 */
public abstract class SectionAdapter<D, VH extends SectionViewHolder> extends RecyclerView.Adapter<SectionViewHolder> {

    private static final int ITEM_VIEW_TYPE_SECTION = 0;
    private static final int ITEM_VIEW_TYPE_BASIC = 1;
    private static final int ITEM_VIEW_TYPE_FOOTER = 2;
    private static final int VISIBLE_THRESHOLD = 10;

    protected List<D> mDataList = new ArrayList<>();

    protected AbstractSectionIndexer<D> mAbsSecIndexer;
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

            Log.d("shubham", "reaching end of recycler view");
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

    public SectionAdapter(RecyclerView recyclerView, AbstractSectionIndexer<D> abstractSectionIndexer) {
        mAbsSecIndexer = abstractSectionIndexer;
        recyclerView.addOnScrollListener(onScrollListener);
    }

    @Override
    public SectionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM_VIEW_TYPE_SECTION:
                return onCreateSectionViewHolder(parent, viewType);
            case ITEM_VIEW_TYPE_BASIC:
                return onCreateItemViewHolder(parent, viewType);
            case ITEM_VIEW_TYPE_FOOTER:
                return onCreateFooterViewHolder(parent, viewType);
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
            case ITEM_VIEW_TYPE_FOOTER:
                onBindFooterViewHolder(holder, mAbsSecIndexer.getPositionForSection(position));
                break;
        }
    }

    protected void onBindFooterViewHolder(SectionViewHolder holder, int position) {

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

    protected SectionViewHolder onCreateFooterViewHolder(ViewGroup parent, int viewType) {
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
        } else if (mDataList.get(mAbsSecIndexer.getPositionForSection(position)) == null) {
            return ITEM_VIEW_TYPE_FOOTER;
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
