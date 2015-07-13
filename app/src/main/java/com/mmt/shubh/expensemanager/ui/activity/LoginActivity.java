package com.mmt.shubh.expensemanager.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.facebook.FacebookSdk;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.database.content.UserInfo;
import com.mmt.shubh.expensemanager.database.dataadapters.UserInfoSQLDataAdapter;
import com.mmt.shubh.expensemanager.login.FacebookLoginHelper;
import com.mmt.shubh.expensemanager.login.GoogleLoginHelper;
import com.mmt.shubh.expensemanager.login.ILoginHelper;
import com.mmt.shubh.expensemanager.login.LoginCallback;
import com.mmt.shubh.expensemanager.setup.AccountSetupHelper;
import com.mmt.shubh.expensemanager.setup.FacebookProfileFetcher;
import com.mmt.shubh.expensemanager.setup.GoogleProfileFetcher;
import com.mmt.shubh.expensemanager.setup.ProfileFetcher;

import java.util.List;


public class LoginActivity extends AppCompatActivity implements LoginCallback, AccountSetupHelper.AccountSetupListener {

    private View mProgressView;

    private SignInButton mPlusSignInButton;

    private ILoginHelper mLoginHelper;
    private FacebookLoginHelper mFacebookLoginHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);

        mPlusSignInButton = (SignInButton) findViewById(R.id.plus_sign_in_button);
        mProgressView = findViewById(R.id.login_progress);
        LoginButton faceBookLoginButton = (LoginButton) findViewById(R.id.facebook_login_button);

        mLoginHelper = new GoogleLoginHelper(this);
        mLoginHelper.setUp(mPlusSignInButton);


        mFacebookLoginHelper = new FacebookLoginHelper(this);
        mFacebookLoginHelper.setUp(faceBookLoginButton);

        UserInfoSQLDataAdapter sqlDataAdapter = new UserInfoSQLDataAdapter(this);
        List<UserInfo> accounts = sqlDataAdapter.getAll();
        if (accounts != null && accounts.size() > 0) {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra("Account", accounts.get(0));
            startActivity(intent);
            finish();
        }
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    public void showProgress(final boolean show) {
        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onSignInComplete(ILoginHelper.Type type) {
        ProfileFetcher profileFetcher = null;
        switch (type) {
            case GOOGLE:
                profileFetcher = new GoogleProfileFetcher((GoogleApiClient) mLoginHelper.getClient());
                break;
            case FACEBOOK:
                profileFetcher = new FacebookProfileFetcher();
                break;
        }

        AccountSetupHelper helper = new AccountSetupHelper(getApplicationContext(), profileFetcher);
        helper.setSetupListener(this);
        helper.setUpUserAccount();
    }

    @Override
    public void onSignInFailed(String message) {

    }

    @Override
    public void onSignInCanceled() {

    }

    @Override
    public void onBlockingUI(boolean show) {
        showProgress(show);
    }


    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
        mLoginHelper.onActivityResult(requestCode, responseCode, intent);
    }

    @Override
    public void updateProgress(String message) {

    }

    @Override
    public void onAccountSetupComplete() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}

