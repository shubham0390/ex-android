package com.km2labs.android.spendview.core.view;

import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.km2labs.shubh.expensemanager.R;

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
