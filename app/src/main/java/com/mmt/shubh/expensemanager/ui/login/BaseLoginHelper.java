package com.mmt.shubh.expensemanager.ui.login;

import android.content.Intent;
import android.widget.Button;

/**
 * Created by styagi on 6/4/2015.
 */
public interface BaseLoginHelper {

    enum Type {
        GOOGLE,
        FACEBOOK,
        TWITEER,
    }

    void signIn();

    void signOut();

    void revokeAccess();

    void onActivityResult(int requestCode, int responseCode, Intent intent);
}
