package com.mmt.shubh.expensemanager.ui.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by Subham Tyagi,
 * on 11/Nov/2015,
 * 11:21 PM
 * TODO:Add class comment.
 */
public class SpinerAdapter<T> extends ArrayAdapter<T> implements ThemedSpinnerAdapter {
    private final ThemedSpinnerAdapter.Helper mDropDownHelper;

    public SpinerAdapter(Context context, int resource) {
        super(context, resource);
        mDropDownHelper = new ThemedSpinnerAdapter.Helper(context);
    }

    public SpinerAdapter(Context context, int resource, List<T> objects) {
        super(context, resource, objects);
        mDropDownHelper = new ThemedSpinnerAdapter.Helper(context);
    }

    public SpinerAdapter(Context context, int resource, T[] objects) {
        super(context, resource, objects);
        mDropDownHelper = new ThemedSpinnerAdapter.Helper(context);
    }

    public SpinerAdapter(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
        mDropDownHelper = new ThemedSpinnerAdapter.Helper(context);
    }

    public SpinerAdapter(Context context, int resource, int textViewResourceId, List<T> objects) {
        super(context, resource, textViewResourceId, objects);
        mDropDownHelper = new ThemedSpinnerAdapter.Helper(context);
    }

    public SpinerAdapter(Context context, int resource, int textViewResourceId, T[] objects) {
        super(context, resource, textViewResourceId, objects);
        mDropDownHelper = new ThemedSpinnerAdapter.Helper(context);
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = null;

        if (convertView == null) {
            // Inflate the drop down using the helper's LayoutInflater
            LayoutInflater inflater = mDropDownHelper.getDropDownViewInflater();
            view = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
        }
        return view;
    }

    @Override
    public void setDropDownViewTheme(Resources.Theme theme) {
        mDropDownHelper.setDropDownViewTheme(theme);
    }

    @Override
    public Resources.Theme getDropDownViewTheme() {
        return mDropDownHelper.getDropDownViewTheme();
    }
}

