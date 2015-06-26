package com.mmt.shubh.expensemanager.ui.activity;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mmt.shubh.expensemanager.Constants;
import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.database.content.contract.MemberContract;

/**
 * Created by Subham Tyagi,
 * on 22/Jun/2015,
 * 5:55 PM
 * TODO:Add class comment.
 */
public class MemberDetailActivity extends AppCompatActivity {
    private ImageView mImageView;


    @Nullable
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            String name = getIntent().getStringExtra(Constants.KEY_MEMBER_NAME);
            actionBar.setTitle(name);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mImageView = (ImageView) findViewById(R.id.backdrop);

    }

    @Override
    protected void onStart() {
        super.onStart();
        long id = getIntent().getLongExtra(Constants.KEY_MEMBER_ID, 1);
        Bundle bundle = new Bundle();
        bundle.putLong(Constants.KEY_MEMBER_ID, id);
        getSupportLoaderManager().restartLoader(2, bundle, mLoaderCallbacks);
    }

    private LoaderManager.LoaderCallbacks<Cursor> mLoaderCallbacks = new LoaderManager.LoaderCallbacks<Cursor>() {
        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            long memberId = args.getLong(Constants.KEY_MEMBER_ID);
            return new MemberLoader(getApplicationContext(), MemberContract.MEMBER_URI, null,
                    MemberContract.ID_SELECTION, new String[]{String.valueOf(memberId)}, null);
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {


            if (data.moveToNext()) {
                Glide.with(getApplicationContext())
                        .load(data.getString(data.getColumnIndex(MemberContract.MEMBER_IMAGE_URI)))
                        .into(mImageView);
            }
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }
    };

    private static class MemberLoader extends CursorLoader {

        public MemberLoader(Context context, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
            super(context, uri, projection, selection, selectionArgs, sortOrder);
        }

        @Override
        public Cursor loadInBackground() {
            return super.loadInBackground();
        }
    }


}
