package com.mmt.shubh.expensemanager.login;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.mmt.shubh.expensemanager.R;

/**
 * Created by subhamtyagi on 3/29/16.
 */
public class PhoneNumberView extends RelativeLayout {
    public PhoneNumberView(Context context) {
        super(context);
        init();
    }

    public PhoneNumberView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PhoneNumberView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PhoneNumberView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.phone_number_view,this,false);
    }
}
