package com.mmt.shubh.expensemanager.uiutils;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by Subham Tyagi,
 * on 09/Sep/2015,
 * 12:15 PM
 * TODO:Add class comment.
 */
public class IndeterminateProgressBar extends DialogFragment {

    public final static String TAG = "CheckProgressDialog";
    private int mMessageResourceId;

    private ProgressDialog progressDialog;

    /**
     * Create a dialog that reports progress
     *
     * @param progressinitial progress indication
     */
    public void init(int progressinitial, int messageResourceId) {
        mMessageResourceId = messageResourceId;
        this.setCancelable(false);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();
        progressDialog = new ProgressDialog(context);
        progressDialog.setIndeterminate(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage(getResources().getString(mMessageResourceId));
        progressDialog.setCancelable(false);
        return progressDialog;
    }


    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    public void updateStringMessage(String Message) {
        if (progressDialog != null)
            progressDialog.setMessage(Message);

    }

    @Override
    public void dismiss() {
        progressDialog.dismiss();
    }

}
