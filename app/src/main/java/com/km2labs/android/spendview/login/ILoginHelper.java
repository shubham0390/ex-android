package com.km2labs.android.spendview.login;

import android.app.Activity;
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
    void signIn(Activity activity);

    void signOut();

    void revokeAccess();

    Object getClient();

    void onActivityResult(int requestCode, int responseCode, Intent intent);
}
