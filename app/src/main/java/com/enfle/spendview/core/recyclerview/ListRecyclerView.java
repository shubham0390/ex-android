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

package com.enfle.spendview.core.recyclerview;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Subham Tyagi,
 * on 10/Jul/2015,
 * 11:53 AM
 * TODO:Add class comment.
 */
public class ListRecyclerView extends RecyclerView {

    private ItemClickSupport mItemClickSupport;

    public ListRecyclerView(Context context) {
        super(context);
        init(context);
    }

    public ListRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ListRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        if (isInEditMode()) {
            return;
        }
        mItemClickSupport = ItemClickSupport.addTo(this);
        setLayoutManager(new LinearLayoutManager(context));
        setItemAnimator(new DefaultItemAnimator());
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mItemClickSupport.setOnItemClickListener(onItemClickListener);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener longClickListener) {
        mItemClickSupport.setOnItemLongClickListener(longClickListener);
    }

    public interface OnItemClickListener {
        /**
         * Callback method to be invoked when an item in this AdapterView has
         * been clicked.
         * <p>
         * Implementers can call getItemAtPosition(position) if they need
         * to access the data associated with the selected item.
         *
         * @param view     The view within the AdapterView that was clicked (this
         *                 will be a view provided by the adapter)
         * @param position The position of the view in the adapter.
         * @param id       The row id of the item that was clicked.
         * @return true if the callback consumed the long click, false otherwise
         */
        boolean onItemClick(RecyclerView parent, View view, int position, long id);
    }

    public interface OnItemLongClickListener {

        /**
         * Callback method to be invoked when an item in this view has been
         * clicked and held.
         * <p>
         * Implementers can call getItemAtPosition(position) if they need to access
         * the data associated with the selected item.
         *
         * @param view     The view within the AbsListView that was clicked
         * @param position The position of the view in the list
         * @param id       The row id of the item that was clicked
         * @return true if the callback consumed the long click, false otherwise
         */
        boolean onItemLongClick(RecyclerView parent, View view, int position, long id);
    }
}