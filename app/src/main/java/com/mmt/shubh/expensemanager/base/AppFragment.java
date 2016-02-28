package com.mmt.shubh.expensemanager.base;

import android.content.Intent;

public abstract interface AppFragment {

    public abstract String getTitle();

    public void onActivityResult(int requestCode, int resultCode, Intent intent);


}
