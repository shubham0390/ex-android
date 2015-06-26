package com.mmt.shubh.expensemanager.ui.uiutils;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by STyagi on 5/3/2014.
 */
public class UiUtils {


    public static ProgressDialog getProgressBar(String message, Context context, boolean cancelable) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setIndeterminate(true);
        progressDialog.setCanceledOnTouchOutside(cancelable);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(cancelable);
        return progressDialog;
    }
}
