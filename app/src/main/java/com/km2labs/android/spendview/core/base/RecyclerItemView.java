package com.km2labs.android.spendview.core.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by : Subham Tyagi
 * Created on :  28/08/16.
 */

public interface RecyclerItemView {

    RecyclerView.ViewHolder createViewHolder(@NonNull ViewGroup parent);

    void bindView(RecyclerView.ViewHolder holder);
}
