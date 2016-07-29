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
