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

package com.km2labs.android.spendview.core.view;

import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.km2labs.expenseview.android.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class BottomSheetAdapter<T> extends RecyclerView.Adapter<BottomSheetAdapter.ViewHolder> {

    T[] data;
    int[] imgRes;

    public BottomSheetAdapter(T[] data) {
        this.data = data;
    }

    public BottomSheetAdapter(T[] data, int[] imgRes) {
        this.data = data;
        this.imgRes = imgRes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bottom_sheet_list, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindText(data[position].toString());
        if (imgRes != null) {
            //holder.bindImage(imgRes[position]);
        }
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_view)
        TextView textView;
        @Nullable
       // @BindView(R.id.image_view)
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindText(String text) {
            textView.setText(text);
        }

        public void bindImage(@DrawableRes int id) {
            if (id > 1) {
                if (imageView != null)
                    imageView.setImageResource(id);
            }
        }
    }
}
