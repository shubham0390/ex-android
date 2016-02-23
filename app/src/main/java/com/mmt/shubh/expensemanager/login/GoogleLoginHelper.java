package com.mmt.shubh.expensemanager.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.lang.ref.WeakReference;

import timber.log.Timber;

/**
 * Created by Subham Tyagi,
 * on 27/Aug/2015,
 * 2:03 PM
 * TODO:Add class comment.
 */
public class GoogleLoginHelper implements ILoginHelper, GoogleApiClient.OnConnectionFailedListener {

    public static final int OUR_REQUEST_CODE = 49404;

    private GoogleSignInOptions gso;
    private GoogleApiClient mPlusClient;
    private SignUpCallback mCallback;
    private WeakReference<AppCompatActivity> mActivityWeakReference;
    private Context mContext;

    private GoogleSignInAccount mGoogleAccount;

    private View.OnClickListener mGoogleLoginClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            GoogleLoginHelper.this.signIn(mActivityWeakReference.get());
        }
    };

    public GoogleLoginHelper(AppCompatActivity context, SignUpCallback iSignUpPresenter) {
        Timber.tag(getClass().getName());
        mActivityWeakReference = new WeakReference<>(context);
        mCallback = iSignUpPresenter;
        mContext = context.getApplicationContext();
        gso = new GoogleSignInOptions   .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestProfile()
                .build();
        mPlusClient =
                new GoogleApiClient.Builder(context)
                        .enableAutoManage(context, this)
                        .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                        .build();

    }

    public void setUp(Object plusSignInButton) {

        SignInButton signInButton = (SignInButton) plusSignInButton;
        if (!supportsGooglePlayServices()) {
            signInButton.setVisibility(View.GONE);
        } else {
            signInButton.setStyle(SignInButton.SIZE_WIDE, SignInButton.COLOR_LIGHT);
            signInButton.setScopes(gso.getScopeArray());
            signInButton.setOnClickListener(mGoogleLoginClickListener);
        }
    }

    private boolean supportsGooglePlayServices() {
        return GooglePlayServicesUtil.isGooglePlayServicesAvailable(mContext) == ConnectionResult.SUCCESS;
    }

    /**
     * Try to sign in the user.
     *
     * @param activity
     */
    @Override
    public void signIn(Activity activity) {
        mCallback.onBlockingUI(true);
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mPlusClient);
        activity.startActivityForResult(signInIntent, OUR_REQUEST_CODE);
    }

    /**
     * Sign out the user (so they can switch to another account).
     */
    @Override
    public void signOut() {
        Auth.GoogleSignInApi.signOut(mPlusClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // ...
                    }
                });
    }

    /**
     * Revoke Google+ authorization completely.
     */
    @Override
    public void revokeAccess() {
        Auth.GoogleSignInApi.signOut(mPlusClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // TODO: 2/19/16 handle it gracefully
                    }
                });

    }

    /**
     * Connection failed for some reason (called by PlusClient)
     * Try and resolve the result.  Failure here is usually not an indication of a serious error,
     * just that the user's input is needed.
     *
     * @see #onActivityResult(int, int, Intent)
     */
    @Override
    public void onConnectionFailed(ConnectionResult result) {

        if (result.hasResolution()) {
            // TODO: 2/19/16 handle it gracefully
        }
    }


    public GoogleApiClient getClient() {
        return mPlusClient;
    }

    @Override
    public void onActivityResult(int requestCode, int responseCode, Intent intent) {
        GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(intent);
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            mGoogleAccount = result.getSignInAccount();
            mCallback.onSignInComplete(Type.GOOGLE);
            mCallback.onBlockingUI(false);
        } else {
            // Signed out, show unauthenticated UI.

        }
    }

    public GoogleSignInAccount getGoogleAccount() {
        return mGoogleAccount;
    }
}
