package com.mmt.shubh.expensemanager.ui.view;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.mmt.shubh.expensemanager.R;
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
