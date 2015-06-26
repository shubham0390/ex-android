package com.mmt.shubh.expensemanager.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.facebook.FacebookSdk;
import com.google.android.gms.common.SignInButton;
import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.database.content.Account;
import com.mmt.shubh.expensemanager.database.dataadapters.AccountSQLDataAdapter;
import com.mmt.shubh.expensemanager.gsm.RegistrationIntentService;
import com.mmt.shubh.expensemanager.ui.login.BaseLoginHelper;
import com.mmt.shubh.expensemanager.ui.login.FacebookLoginHelper;
import com.mmt.shubh.expensemanager.ui.login.FacebookProfileFetcher;
import com.mmt.shubh.expensemanager.ui.login.GoogleLoginHelper;
import com.mmt.shubh.expensemanager.ui.login.GoogleProfileFetcher;
import com.mmt.shubh.expensemanager.ui.login.LoginCallback;
import com.mmt.shubh.expensemanager.ui.login.ProfileFetcher;

import java.util.List;


public class LoginActivity extends AppCompatActivity implements LoginCallback {

    private View mProgressView;

    private SignInButton mPlusSignInButton;

    private GoogleLoginHelper mGoogleLoginHelper;

    private FacebookLoginHelper mFacebookLoginHelper;

    private BaseLoginHelper mLoginHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);

        mPlusSignInButton = (SignInButton) findViewById(R.id.plus_sign_in_button);
        mProgressView = findViewById(R.id.login_progress);
        //LoginButton faceBookLoginButton = (LoginButton) findViewById(R.id.facebook_login_button);

        mGoogleLoginHelper = new GoogleLoginHelper(this);
        mGoogleLoginHelper.setUp(mPlusSignInButton);


       /* mFacebookLoginHelper = new FacebookLoginHelper(this);
        mFacebookLoginHelper.setUp(faceBookLoginButton);*/

        AccountSQLDataAdapter sqlDataAdapter = new AccountSQLDataAdapter(this);
        List<Account> accounts = sqlDataAdapter.getAll();
        if (accounts.size() > 0) {
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
    public void onSignInComplete(BaseLoginHelper.Type type) {
        ProfileFetcher profileFetcher = null;
        switch (type) {
            case GOOGLE:
                profileFetcher = new GoogleProfileFetcher(mGoogleLoginHelper.getPlusClient());
                break;
            case FACEBOOK:
                profileFetcher = new FacebookProfileFetcher();
                break;
        }


        Account account = profileFetcher.fetchUserAccountDetails(this);
        Intent intent1 = new Intent(this, RegistrationIntentService.class);
        intent1.putExtra("Account", account);
        startService(intent1);

        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("Account", account);
        startActivity(intent);
        finish();
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
        mGoogleLoginHelper.onActivityResult(requestCode, responseCode, intent);
    }

}

