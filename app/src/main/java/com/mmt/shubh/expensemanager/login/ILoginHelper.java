package com.mmt.shubh.expensemanager.login;

import android.content.Intent;

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