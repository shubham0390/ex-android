package com.km2labs.android.spendview.login;

public interface SignUpCallback {

    /**
     * Called when the PlusClient is successfully connected.
     */
    void onSignInComplete(ILoginHelper.Type type);

    void onSignInFailed(String message);

    void onSignInCanceled();

    /**
     * Called when the is blocking the UI.  If you have a progress bar widget,
     * this tells you when to show or hide it.
     */
    void onBlockingUI(boolean show);

}