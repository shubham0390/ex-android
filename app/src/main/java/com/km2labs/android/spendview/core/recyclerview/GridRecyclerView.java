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

package com.km2labs.android.spendview.core.recyclerview;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by Subham Tyagi,
 * on 10/Jul/2015,
 * 11:53 AM
 * TODO:Add class comment.
 */
public class GridRecyclerView extends RecyclerView {

    private ItemClickSupport mItemClickSupport;

    public GridRecyclerView(Context context) {
        super(context);
        init(context);
    }

    public GridRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GridRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        if (isInEditMode()) {
            return;
        }
        mItemClickSupport = ItemClickSupport.addTo(this);
        setLayoutManager(new GridLayoutManager(context, 5));
        setItemAnimator(new DefaultItemAnimator());
    }

    public void setOnItemClickListener(ListRecyclerView.OnItemClickListener onItemClickListener) {
        mItemClickSupport.setOnItemClickListener(onItemClickListener);
    }

    public void setOnItemLongClickListener(ListRecyclerView.OnItemLongClickListener longClickListener) {
        mItemClickSupport.setOnItemLongClickListener(longClickListener);
    }
}