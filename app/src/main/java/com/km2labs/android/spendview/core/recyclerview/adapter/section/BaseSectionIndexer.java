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

import android.support.v7.widget.RecyclerView;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Subham Tyagi,
 * on 12/2/15.
 * 10:15 AM
 */
public abstract class BaseSectionIndexer<D> {

    private Map<Integer, BaseSection> mSections;
    private int mPreviousOffset;

    public BaseSectionIndexer() {
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
            Set<Map.Entry<Integer, BaseSection>> entries = mSections.entrySet();

            Iterator<Map.Entry<Integer, BaseSection>> iterator = entries.iterator();
            boolean isFound = false;
            while (iterator.hasNext()) {
                Map.Entry<Integer, BaseSection> entry = iterator.next();
                BaseSection baseSection = entry.getValue();
                if (baseSection.equals(section)) {
                    baseSection.update(section);
                    isFound = true;
                }
            }
            if (isFound) {
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
