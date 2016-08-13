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

package com.km2labs.android.spendview.core.list;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

public abstract class BaseListItemView<T> extends FrameLayout implements IListItemView<T> {

    private boolean mSelected;

    public BaseListItemView(Context context) {
        super(context);
        init(context);
    }

    public BaseListItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseListItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BaseListItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(final Context context) {
        int childLayout = getLayout();
        LayoutInflater.from(context).inflate(childLayout, this, true);
        setPadding(5, 5, 5, 5);
    }

    protected abstract int getLayout();

    @Override
    public void setSelected(final boolean selected) {
        mSelected = true;
    }
}
