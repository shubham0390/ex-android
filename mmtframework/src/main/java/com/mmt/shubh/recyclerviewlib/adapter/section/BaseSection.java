package com.mmt.shubh.recyclerviewlib.adapter.section;

public class BaseSection {

    public int mFirstPosition;
    public int mSectionedIndex;
    public String mSectionName;

    public BaseSection() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseSection section = (BaseSection) o;

        return mSectionName.equals(section.mSectionName);

    }

    @Override
    public int hashCode() {
        return mSectionName.hashCode();
    }
}