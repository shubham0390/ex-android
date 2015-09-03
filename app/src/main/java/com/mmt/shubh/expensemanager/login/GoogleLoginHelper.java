package com.mmt.shubh.expensemanager.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.PersonBuffer;
import com.mmt.shubh.expensemanager.ui.presenters.ISignUpPresenter;

import java.lang.ref.WeakReference;

/**
 * Created by Subham Tyagi,
 * on 27/Aug/2015,
 * 2:03 PM
 * TODO:Add class comment.
 */
public class GoogleLoginHelper implements ILoginHelper, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, ResultCallback<People.LoadPeopleResult> {
    private static final String TAG = GoogleLoginHelper.class.getSimpleName();

    // A magic number we will use to know that our sign-in error resolution activity has completed
    public static final int OUR_REQUEST_CODE = 49404;

    // A flag to stop multiple dialogues appearing for the user
    private boolean mAutoResolveOnFail;

    // A flag to track when a connection is already in progress
    public boolean mPlusClientIsConnecting = false;


    private GoogleApiClient mPlusClient;

    private ConnectionResult mConnectionResult;

    private SignUpCallback mCallback;

    private WeakReference<Activity> mActivityWeakReference;

    private Context mContext;

    public GoogleLoginHelper(Activity context, ISignUpPresenter iSignUpPresenter) {
        mActivityWeakReference = new WeakReference<>(context);
        mCallback = (SignUpCallback) iSignUpPresenter;
        mContext = context.getApplicationContext();
        mPlusClient =
                new GoogleApiClient.Builder(context)
                        .addConnectionCallbacks(this)
                        .addOnConnectionFailedListener(this)
                        .addApi(Plus.API)
                        .addScope(new Scope("profile"))
                        .build();
    }

    public void setUp(Object plusSignInButton) {
        SignInButton signInButton = (SignInButton) plusSignInButton;
        if (!supportsGooglePlayServices()) {
            signInButton.setVisibility(View.GONE);
            return;
        } else {
            signInButton.setOnClickListener(mGoogleLoginClickListener);
        }
    }

    private boolean supportsGooglePlayServices() {
        return GooglePlayServicesUtil.isGooglePlayServicesAvailable(mContext) == ConnectionResult.SUCCESS;
    }


    private View.OnClickListener mGoogleLoginClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            signIn();
        }
    };

    /**
     * Try to sign in the user.
     */
    @Override
    public void signIn() {
        if (!mPlusClient.isConnected()) {
            setProgressBarVisible(true);
            mAutoResolveOnFail = true;
            if (mConnectionResult != null) {
                startResolution();
            } else {
                initiatePlusClientConnect();
            }
        }
    }


    private void initiatePlusClientConnect() {
        if (!mPlusClient.isConnected() && !mPlusClient.isConnecting()) {
            mPlusClient.connect();
        }
    }

    private void initiatePlusClientDisconnect() {
        if (mPlusClient.isConnected()) {
            mPlusClient.disconnect();
        }
    }

    /**
     * Sign out the user (so they can switch to another account).
     */
    @Override
    public void signOut() {

        // We only want to sign out if we're connected.
        if (mPlusClient.isConnected()) {
            // Disconnect from Google Play Services, then reconnect in order to restart the
            // process from scratch.
            initiatePlusClientDisconnect();
            Log.v(TAG, "Sign out successful!");
        }
    }

    /**
     * Revoke Google+ authorization completely.
     */
    @Override
    public void revokeAccess() {
        if (mPlusClient.isConnected()) {
            Plus.AccountApi.revokeAccessAndDisconnect(mPlusClient)
                    .setResultCallback(new ResultCallback<Status>() {

                        @Override
                        public void onResult(Status status) {
                            //TODO: handle it more garcefully
                            //mCallback.onPlusClientRevokeAccess();
                        }
                    });
        }

    }

    public boolean isPlusClientConnecting() {
        return mPlusClientIsConnecting;
    }

    private void setProgressBarVisible(boolean flag) {
        mPlusClientIsConnecting = flag;
        mCallback.onBlockingUI(flag);
    }

    /**
     * A helper method to flip the mResolveOnFail flag and start the resolution
     * of the ConnectionResult from the failed connect() call.
     */
    private void startResolution() {
        try {
            // Don't start another resolution now until we have a result from the activity we're
            // about to start.
            mAutoResolveOnFail = false;
            // If we can resolve the error, then call start resolution and pass it an integer tag
            // we can use to track.
            // This means that when we get the onActivityResult callback we'll know it's from
            // being started here.
            mConnectionResult.startResolutionForResult(mActivityWeakReference.get(), OUR_REQUEST_CODE);
        } catch (IntentSender.SendIntentException e) {
            // Any problems, just try to connect() again so we get a new ConnectionResult.
            mConnectionResult = null;
            initiatePlusClientConnect();
        }
    }

    /**
     * Successfully connected (called by PlusClient)
     */
    @Override
    public void onConnected(Bundle connectionHint) {
        setProgressBarVisible(false);
         /* This Line is the key */
       // Plus.PeopleApi.loadVisible(mPlusClient, null).setResultCallback(this);
        mCallback.onSignInComplete(Type.GOOGLE);
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

        // Most of the time, the connection will fail with a user resolvable result. We can store
        // that in our mConnectionResult property ready to be used when the user clicks the
        // sign-in button.
        if (result.hasResolution()) {
            mConnectionResult = result;
            if (mAutoResolveOnFail) {
                // This is a local helper function that starts the resolution of the problem,
                // which may be showing the user an account chooser or similar.
                startResolution();
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        mPlusClient.connect();
    }

    public GoogleApiClient getClient() {
        return mPlusClient;
    }

    @Override
    public void onActivityResult(int requestCode, int responseCode, Intent intent) {
        if (requestCode == OUR_REQUEST_CODE && responseCode == Activity.RESULT_OK) {
            mAutoResolveOnFail = true;
            initiatePlusClientConnect();
        } else if (requestCode == OUR_REQUEST_CODE && responseCode != Activity.RESULT_OK) {
            setProgressBarVisible(false);
        }
    }

    @Override
    public void onResult(People.LoadPeopleResult peopleData) {
        if (peopleData.getStatus().getStatusCode() == CommonStatusCodes.SUCCESS) {
            PersonBuffer personBuffer = peopleData.getPersonBuffer();
            try {
                int count = personBuffer.getCount();
                for (int i = 0; i < count; i++) {
                    Log.d(TAG, "Display name: " + personBuffer.get(i).getDisplayName());
                }
            } finally {
                personBuffer.close();
            }
        } else {
            Log.e(TAG, "Error requesting visible circles: " + peopleData.getStatus());
        }
    }
}
