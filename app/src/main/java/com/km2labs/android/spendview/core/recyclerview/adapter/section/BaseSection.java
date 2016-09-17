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