package com.mmt.shubh.expensemanager.database.dataadapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.mmt.shubh.expensemanager.database.content.BaseContent;

/**
 * Created by styagi on 6/1/2015.
 */
public abstract class SQLDataAdapter<T extends BaseContent> {
    // Newly created objects get this id
    public static final int NOT_SAVED = -1;

    // Write the Content into a ContentValues container
    public abstract ContentValues toContentValues(T t);

    // Read the Content from a ContentCursor
    public abstract void restore(Cursor cursor);


    public long create(Context context, Uri uri, T t) {
        if (isSaved(t)) {
            throw new UnsupportedOperationException();
        }
        Uri res = context.getContentResolver().insert(uri, toContentValues(t));
        return Long.parseLong(res.getLastPathSegment());
    }

    private boolean isSaved(T t) {
        return t.getId() == NOT_SAVED;
    }

}
