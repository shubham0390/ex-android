package com.mmt.shubh.expensemanager.ui.activity;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.database.content.UserInfo;
import com.mmt.shubh.expensemanager.database.dataadapters.UserInfoSQLDataAdapter;

import java.util.List;

/**
 * Created by Subham Tyagi,
 * on 22/Jul/2015,
 * 12:00 AM
 * TODO:Add class comment.
 */
public class SplashActivity extends AppCompatActivity {
    Handler handler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getLoaderManager().initLoader(12, null, mLoaderCallbacks).forceLoad();
            }
        }, 1300);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacksAndMessages(null);
    }

    private LoaderManager.LoaderCallbacks<UserInfo> mLoaderCallbacks = new LoaderManager.LoaderCallbacks<UserInfo>() {
        @Override
        public Loader<UserInfo> onCreateLoader(int id, Bundle args) {
            return new UserLoader(getApplicationContext());
        }

        @Override
        public void onLoadFinished(Loader<UserInfo> loader, UserInfo data) {
            if (data != null) {
                Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                intent.putExtra("Account", data);
                startActivity(intent);
            } else {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
            }
            finish();
        }

        @Override
        public void onLoaderReset(Loader<UserInfo> loader) {
            //do nothing
        }
    };

    private static class UserLoader extends AsyncTaskLoader<UserInfo> {

        private Context mContext;

        public UserLoader(Context context) {
            super(context);
            mContext = context;
        }

        @Override
        public UserInfo loadInBackground() {
            UserInfoSQLDataAdapter sqlDataAdapter = new UserInfoSQLDataAdapter(mContext);
            List<UserInfo> userInfoList = sqlDataAdapter.getAll();
            if (userInfoList != null && userInfoList.size() > 0) {
                return userInfoList.get(0);
            }
            return null;
        }
    }

}