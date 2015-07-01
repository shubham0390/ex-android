package com.mmt.shubh.expensemanager.ui.login;

import android.content.Intent;
import android.widget.Button;

/**
 * Created by styagi on 6/4/2015.
 */
public interface ILoginHelper {

    enum Type {
        GOOGLE,
        FACEBOOK,
        TWITEER,
    }

    void setUp(Object object);
    void signIn();

    void signOut();

    void revokeAccess();

    Object getClient();

    void onActivityResult(int requestCode, int responseCode, Intent intent);
}
