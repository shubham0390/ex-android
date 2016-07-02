package com.mmt.shubh.core.recyclerview.adapter.section;

public class BaseSection<T> {

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

    public void update(T section) {

    }
}