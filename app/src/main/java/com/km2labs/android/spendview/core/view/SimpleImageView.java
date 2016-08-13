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

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.km2labs.spendview.android.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;


public class SimpleImageView extends AppCompatImageView {
    public SimpleImageView(Context context) {
        super(context);
    }

    public SimpleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SimpleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void loadImage(String url) {
        Picasso.with(getContext())
                .load(url)
                .centerCrop()
                .resizeDimen(R.dimen.avatar_image_size, R.dimen.avatar_image_size)
                .into(this);
    }

    public void loadImage(String url, Callback callback) {
        Picasso.with(getContext())
                .load(url)
                .centerCrop()
                .resizeDimen(R.dimen.avatar_image_size, R.dimen.avatar_image_size)
                .into(this, callback);
    }


    public void loadImage(String url, Transformation transformation, Callback callback) {
        Picasso.with(getContext())
                .load(url)
                .fit()
                .transform(transformation)
                .into(this, callback);
    }

    public void loadImage(String url, Callback callback, List<Transformation> transformation) {
        Picasso.with(getContext())
                .load(url)
                .centerCrop()
                .resizeDimen(R.dimen.avatar_image_size, R.dimen.avatar_image_size)
                .transform(transformation)
                .into(this, callback);
    }
}
