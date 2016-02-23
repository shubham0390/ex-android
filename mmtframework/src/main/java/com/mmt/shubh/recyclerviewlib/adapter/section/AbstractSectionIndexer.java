package com.mmt.shubh.recyclerviewlib.adapter.section;

import android.support.v7.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shubham on 12/2/15.
 */
public abstract class AbstractSectionIndexer<D> {

    private Map<Integer, BaseSection> mSections;
    private int mPreviousOffset;

    public AbstractSectionIndexer() {
        mSections = new HashMap<>();
    }

    public Map<Integer, BaseSection> getSections(List<D> dataList) {

        int offset = 0;
        for (int i = 0; i < dataList.size(); i++) {
            BaseSection section = getSectionForData(dataList.get(i));
            if (!mSections.containsValue(section)) {
                section.mFirstPosition = i;
                section.mSectionedIndex = section.mFirstPosition + offset;
                mSections.put(offset, section);
                ++offset;
            }
        }
        mPreviousOffset = offset;
        return mSections;
    }

    public Map<Integer, BaseSection> getSections(int size, List<D> dataList) {
        int offset = mPreviousOffset;
        for (int i = 0; i < dataList.size(); i++) {

            BaseSection section = getSectionForData(dataList.get(i));

            if (!mSections.containsValue(section)) {
                section.mFirstPosition = size + i;
                section.mSectionedIndex = section.mFirstPosition + offset;
                mSections.put(offset, section);
                ++offset;
            }
        }
        mPreviousOffset = offset;
        return mSections;
    }

    public Map<Integer, BaseSection> getSections() {
        return mSections;
    }

    public int getPositionForSection(int position) {
        if (isSectionPosition(position)) {
            return RecyclerView.NO_POSITION;
        }

        int offset = 0;
        for (int i = 0; i < mSections.size(); i++) {
            if (mSections.get(i).mSectionedIndex > position) {
                break;
            } else
                --offset;
        }
        return position + offset;
    }

    public boolean isSectionPosition(int position) {
        for (int i = 0; i < mSections.size(); i++) {
            if (mSections.get(i).mSectionedIndex == position) {
                return true;

            }
        }
        return false;
    }

    public int getSectionForPosition(int position) {
        int offset = 0;
        for (int i = 0; i < mSections.size(); i++) {
            if (mSections.get(i).mFirstPosition > position) {
                break;
            } else
                ++offset;
        }
        return position + offset;
    }


    protected abstract BaseSection getSectionForData(D data);

    public void clear() {
        mSections.clear();
    }

    public BaseSection getSection(int position) {
        for (int i = 0; i < mSections.size(); i++) {
            if (mSections.get(i).mSectionedIndex == position) {
                return mSections.get(i);

            }
        }
        return null;
    }


}
