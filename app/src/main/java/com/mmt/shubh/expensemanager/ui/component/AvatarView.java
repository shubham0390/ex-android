package com.mmt.shubh.expensemanager.ui.component;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.mmt.shubh.expensemanager.ui.component.outlineprovider.AvatarOutlineProvider;

/**
 * A simple view that wraps an avatar.
 */
public class AvatarView extends ImageView {

    public AvatarView(Context context) {
        this(context, null);
    }

    public AvatarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AvatarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initOutlineProvider();
    }

    private void initOutlineProvider() {
       /* setOutlineProvider(new AvatarOutlineProvider());
        setClipToOutline(true);*/
    }
}
