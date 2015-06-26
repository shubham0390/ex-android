package com.mmt.shubh.expensemanager.database.provider;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;

import com.mmt.shubh.expensemanager.database.DatabaseHelper;
import com.mmt.shubh.expensemanager.database.ExpenseDataEntity.ExpenseBookEntity;
import com.mmt.shubh.expensemanager.database.ExpenseDataEntity;
import com.mmt.shubh.expensemanager.database.ExpenseDataEntity.AccountEntity;
import com.mmt.shubh.expensemanager.database.ExpenseDataEntity.CategoryEntity;
import com.mmt.shubh.expensemanager.database.ExpenseDataEntity.ExpenseEntity;
import com.mmt.shubh.expensemanager.database.ExpenseDataEntity.MemberEntity;
import com.mmt.shubh.expensemanager.database.ExpenseDataEntity.MemberExpenseBookEntity;
import com.mmt.shubh.expensemanager.database.content.Category;
import com.mmt.shubh.expensemanager.database.content.contract.AccountContract;
import com.mmt.shubh.expensemanager.database.content.contract.BaseContract;
import com.mmt.shubh.expensemanager.database.content.contract.CategoryContract;
import com.mmt.shubh.expensemanager.database.content.contract.ExpenseBookContract;
import com.mmt.shubh.expensemanager.database.content.contract.ExpenseContract;
import com.mmt.shubh.expensemanager.database.content.contract.MemberContract;
import com.mmt.shubh.expensemanager.database.dataadapters.BaseSQLDataAdapter;

public class ExpenseProvider extends ContentProvider {

    private static final String EXPENSE_BASE_PATH = ContentPath.PATH_EXPENSE;
    private static final int EXPENSE_BASE = 0x0000;
    private static final int EXPENSE = EXPENSE_BASE;
    private static final int EXPENSE_ID = EXPENSE + 1;
    private static final int EXPENSE_BOOK_EXPENSE = EXPENSE + 2;
    private static final int MEMBER_EXPENSE = EXPENSE + 3;

    private static final String MEMBER_BASE_PATH = MemberContract.PATH_MEMBER;
    private static final int MEMBER_BASE = 0x1000;
    private static final int MEMBER = MEMBER_BASE;
    private static final int MEMBER_ID = MEMBER + 1;

    private static final String EXPENSE_BOOK_BASE_PATH = ExpenseBookContract.PATH_EXPENSE_BOOK;
    private static final int EXPENSE_BOOK_BASE = 0x2000;
    private static final int EXPENSE_BOOK = EXPENSE_BOOK_BASE;
    private static final int EXPENSE_BOOK_ID = EXPENSE_BOOK + 1;
    private static final int EXPENSE_BOOK_MEMBER = EXPENSE_BOOK + 2;

    private static final String USER_BASE_PATH = AccountContract.PATH_USER;
    private static final int USER_BASE = 0x3000;
    private static final int USER = USER_BASE;
    private static final int USER_ID = USER + 1;

    private static final String CATEGORY_BASE_PATH = CategoryContract.PATH_CATEGORY;
    private static final int CATEGORY_BASE = 0x4000;
    private static final int CATEGORY = CATEGORY_BASE;
    private static final int CATEGORY_ID = CATEGORY + 1;

    private static final String MEMBER_EXPANSE_BOOK_BASE_PATH = ContentPath.PATH_MEMBER_EXPENESE_BOOK;
    private static final int MEMBER_EXPANSE_BOOK_BASE = 0x6000;
    private static final int MEMBER_EXPANSE_BOOK = MEMBER_EXPANSE_BOOK_BASE;

    private static final String MEMBER_EXPANSE_BASE_PATH = ContentPath.PATH_MEMBER_EXPENSE;
    private static final int MEMBER_EXPANSE_BASE = 0x7000;
    private static final int MEMBER_EXPANSE = MEMBER_EXPANSE_BASE;


    private static final int BASE_SHIFT = 12; // 12 bits to the base type: 0,
    // 0x1000, 0x2000, etc.

    /*
     * TABLE_NAMES MUST remain in the order of the BASE constants above (e.g.
     * USER_BASE = 0x0000, MESSAGE_HEADER_BASE = 0x1000, etc.)
     */
    private static final String[] TABLE_NAMES = {
            ExpenseContract.TABLE_NAME,
            MemberContract.TABLE_NAME,
            ExpenseBookContract.TABLE_NAME,
            AccountContract.TABLE_NAME,
            CategoryContract.TABLE_NAME,
            ExpenseDataEntity.MemberExpenseBookEntity.TABLE_NAME,
            MemberContract.TABLE_NAME

    };

    private static final UriMatcher sURIMatcher = new UriMatcher(
            UriMatcher.NO_MATCH);

    static {
        // Email URI matching table
        UriMatcher matcher = sURIMatcher;
        /*Get all Expense*/
        matcher.addURI(BaseContract.AUTHORITY, EXPENSE_BASE_PATH, EXPENSE);
        /*Get Single Expense with id*/
        matcher.addURI(BaseContract.AUTHORITY, EXPENSE_BASE_PATH + "/#", EXPENSE_ID);

        /*Get all member*/
        matcher.addURI(BaseContract.AUTHORITY, MEMBER_BASE_PATH, MEMBER);
        /*Get single member with id*/
        matcher.addURI(BaseContract.AUTHORITY, MEMBER_BASE_PATH + "/#", MEMBER_ID);
        /*Get all Member for a expense book with id*/
        matcher.addURI(BaseContract.AUTHORITY, ExpenseBookContract.PATH_EXPENSE_BOOK
                + "/" + MemberContract.PATH_MEMBER + "/#", EXPENSE_BOOK_MEMBER);

        /*Get all expense book*/
        matcher.addURI(BaseContract.AUTHORITY, EXPENSE_BOOK_BASE_PATH, EXPENSE_BOOK);
        /*Get single expense book with id*/
        matcher.addURI(BaseContract.AUTHORITY, EXPENSE_BOOK_BASE_PATH + "/#", EXPENSE_BOOK_ID);

        //  matcher.addURI(BaseContract.AUTHORITY, ContentPath.PATH_EXPENSE_BOOK_FOR_MEMBER + "/#", EXPENSE_BOOK_FOR_MEMBER_ID);

		/* User */
        // All Users
        matcher.addURI(BaseContract.AUTHORITY, USER_BASE_PATH, USER);
        // Specific user
        matcher.addURI(BaseContract.AUTHORITY, USER_BASE_PATH + "/#", USER_ID);

        matcher.addURI(BaseContract.AUTHORITY, CATEGORY_BASE_PATH, CATEGORY);
        matcher.addURI(BaseContract.AUTHORITY, CATEGORY_BASE_PATH + "/#", CATEGORY_ID);

        matcher.addURI(BaseContract.AUTHORITY, MEMBER_EXPANSE_BOOK_BASE_PATH, MEMBER_EXPANSE_BOOK);
        matcher.addURI(BaseContract.AUTHORITY, MEMBER_EXPANSE_BASE_PATH, MEMBER_EXPANSE);

    }

    private SQLiteDatabase mReadDatabase;
    private SQLiteDatabase mWriteDatabase;

    public ExpenseProvider() {

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int match = findMatch(uri, "insert");
        int result;
        Context context = getContext();

        SQLiteDatabase db = getWritableableDatabase(context);
        int table = match >> BASE_SHIFT;
        String id;

        switch (match) {
            case EXPENSE:
            case MEMBER:
            case EXPENSE_BOOK:
            case USER:
            case CATEGORY:
            case MEMBER_EXPANSE:
            case MEMBER_EXPANSE_BOOK:
                result = db.delete(TABLE_NAMES[table], selection, selectionArgs);
                break;
            case EXPENSE_ID:
                id = uri.getPathSegments().get(1);
                result = db.delete(TABLE_NAMES[table],
                        ExpenseDataEntity.ExpenseEntity._ID + " =?",
                        new String[]{id});
                break;
            case MEMBER_ID:

                id = uri.getPathSegments().get(1);
                result = db.delete(TABLE_NAMES[table],
                        ExpenseDataEntity.MemberEntity._ID + " =?",
                        new String[]{id});
                break;
            case EXPENSE_BOOK_ID:

                id = uri.getPathSegments().get(1);
                result = db.delete(TABLE_NAMES[table],
                        ExpenseDataEntity.ExpenseBookEntity._ID + " =?",
                        new String[]{id});
                break;
            case USER_ID:

                id = uri.getPathSegments().get(1);
                result = db.delete(TABLE_NAMES[table],
                        AccountEntity._ID + " =?",
                        new String[]{id});
                break;
            case CATEGORY_ID:
                id = uri.getPathSegments().get(1);
                result = db.delete(TABLE_NAMES[table],
                        ExpenseDataEntity.CategoryEntity._ID + " =?",
                        new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Not yet implemented");
        }
        return result;
    }

    @Override
    public String getType(Uri uri) {
        int match = findMatch(uri, "getType");
        switch (match) {
            case EXPENSE:
                return "vnd.android.cursor.dir/vnd.Expenseprovider.EXPENSE";
            case MEMBER:
                return "vnd.android.cursor.dir/vnd.Expenseprovider.member";
            case EXPENSE_BOOK:
                return "vnd.android.cursor.dir/vnd.Expenseprovider.group";
            case USER:
                return "vnd.android.cursor.dir/vnd.Expenseprovider.user";
            case CATEGORY:
                return "vnd.android.cursor.dir/vnd.Expenseprovider.category";


            case EXPENSE_ID:
                return "vnd.android.cursor.item/vnd.Expenseprovider.EXPENSE";
            case MEMBER_ID:
                return "vnd.android.cursor.item/vnd.Expenseprovider.member";
            case EXPENSE_BOOK_ID:
                return "vnd.android.cursor.item/vnd.Expenseprovider.group";
            case USER_ID:
                return "vnd.android.cursor.item/vnd.Expenseprovider.user";
            case CATEGORY_ID:
                return "vnd.android.cursor.item/vnd.Expenseprovider.category";


        }
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int match = findMatch(uri, "insert");
        Context context = getContext();

        SQLiteDatabase db = getWritableableDatabase(context);
        int table = match >> BASE_SHIFT;
        long longId = -1;

        Uri resultUri;
        switch (match) {
            case EXPENSE:
            case MEMBER:
            case EXPENSE_BOOK:
            case USER:
            case CATEGORY:
            case MEMBER_EXPANSE:
            case MEMBER_EXPANSE_BOOK:
                longId = db.insert(TABLE_NAMES[table], "foo", values);
                break;
            case EXPENSE_ID:
                break;
            case MEMBER_ID:
                break;
            case EXPENSE_BOOK_ID:
                break;
            case USER_ID:
                break;
            case CATEGORY_ID:
                break;
            default:
                throw new UnsupportedOperationException("Not yet implemented");
        }
        resultUri = ContentUris.withAppendedId(uri, longId);
        getContext().getContentResolver().notifyChange(resultUri, null);
        return resultUri;
    }

    @Override
    public boolean onCreate() {
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor c;
        int match;
        try {
            match = findMatch(uri, "query");
        } catch (IllegalArgumentException e) {
            String uriString = uri.toString();
            // If we were passed an illegal uri, see if it ends in /-1
            // if so, and if substituting 0 for -1 results in a valid uri,
            // return an empty cursor
            if (uriString != null && uriString.endsWith("/-1")) {
                // uri = Uri.parse(uriString.substring(0, uriString.length() -
                // 2)
                // + "0");
                // match = findMatch(uri, "query");
                /* switch (match) { */
                return new MatrixCursor(projection, 0);
                // }
            }
            throw e;
        }
        Context context = getContext();
        int table = match >> BASE_SHIFT;
        String limit = uri.getQueryParameter(BaseSQLDataAdapter.PARAMETER_LIMIT);
        String id;

        SQLiteDatabase db = getReadableDatabase(context);
        switch (match) {
            case EXPENSE:
                String selectQuery = "SELECT "
                        + ExpenseContract._ID + " , "
                        + ExpenseContract.EXPENSE_NAME + " , "
                        + ExpenseContract.EXPENSE_DATE + " , "
                        + ExpenseContract.EXPENSE_AMOUNT + " , "
                        + "( SELECT "
                        + ExpenseBookContract.EXPENSE_BOOK_NAME
                        + " FROM " + ExpenseBookEntity.TABLE_NAME
                        + " WHERE " + ExpenseBookContract._ID + " = " + ExpenseContract.EXPENSE_BOOK_KEY + " ) "
                        + " AS " + ExpenseBookContract.EXPENSE_BOOK_NAME + " , "

                        + " ( SELECT " + CategoryEntity.CATEGORY_NAME
                        + " FROM " + CategoryEntity.TABLE_NAME
                        + " WHERE " + CategoryEntity._ID + " = " + ExpenseContract.CATEGORY_KEY + " )"
                        + " AS " + CategoryEntity.CATEGORY_NAME + ","

                        + " ( SELECT " + MemberContract.MEMBER_NAME
                        + " FROM " + MemberContract.TABLE_NAME
                        + " WHERE " + MemberContract._ID + " = " + ExpenseContract.MEMBER_KEY + " )"
                        + " AS " + MemberContract.MEMBER_NAME
                        + " FROM " + ExpenseContract.TABLE_NAME;
                c = db.rawQuery(selectQuery, null);
                break;
            case MEMBER:
            case EXPENSE_BOOK:
            case USER:
            case CATEGORY:
                c = db.query(TABLE_NAMES[table], projection, selection,
                        selectionArgs, null, null, sortOrder, limit);
                break;
            case EXPENSE_ID:
                id = uri.getPathSegments().get(1);
                c = db.query(TABLE_NAMES[table], projection, ExpenseEntity._ID
                                + " = ?", new String[]{id}, null, null, sortOrder,
                        limit
                );
                break;
            case MEMBER_ID:
                id = uri.getPathSegments().get(1);
                c = db.query(TABLE_NAMES[table], projection, MemberEntity._ID
                                + " = ?", new String[]{id}, null, null, sortOrder,
                        limit
                );
                break;
            case EXPENSE_BOOK_MEMBER:
                id = uri.getPathSegments().get(3);

                String selectQuery1 = " SELECT "
                        + MemberEntity.TABLE_NAME + "." + MemberEntity._ID + " , "
                        + MemberEntity.TABLE_NAME + "." + MemberEntity.MEMBER_NAME + " , "
                        + MemberEntity.TABLE_NAME + "." + MemberEntity.MEMBER_EMAIL + " , "
                        + MemberEntity.TABLE_NAME + "." + MemberEntity.EXPENSE_BOOK_KEY
                        + " FROM "
                        + MemberEntity.TABLE_NAME
                        + " WHERE "
                        + MemberEntity.TABLE_NAME + "." + MemberEntity._ID
                        + " IN "
                        + "( SELECT "
                        + ExpenseDataEntity.MemberExpenseBookEntity.TABLE_NAME + " ." + MemberExpenseBookEntity.MEMBER_KEY
                        + " FROM "
                        + MemberExpenseBookEntity.TABLE_NAME
                        + " WHERE "
                        + MemberExpenseBookEntity.TABLE_NAME + " ." + MemberExpenseBookEntity
                        .EXPENSE_BOOK_KEY + " = " + id
                        + ")";
                c = db.rawQuery(selectQuery1, null);

                break;

            case EXPENSE_BOOK_ID:
                id = uri.getPathSegments().get(1);
                c = db.query(TABLE_NAMES[table], projection, ExpenseDataEntity.ExpenseBookEntity._ID
                                + " = ?", new String[]{id}, null, null, sortOrder,
                        limit
                );
                break;
            /*case EXPENSE_BOOK_FOR_MEMBER_ID:
                id = uri.getPathSegments().get(3);

                String expenseBookForMemberQuery = "SELECT "
                        + ExpenseBookEntity.TABLE_NAME + "." + ExpenseBookEntity._ID + " , "
                        + ExpenseBookEntity.TABLE_NAME + "." + ExpenseBookEntity.EXPENSE_BOOK_NAME + " , "
                        + ExpenseBookEntity.TABLE_NAME + "." + ExpenseBookEntity.EXPENSE_BOOK_PROFILE_IMAGE
                        + " FROM "
                        + ExpenseBookEntity.TABLE_NAME
                        + " WHERE "
                        + ExpenseBookEntity.TABLE_NAME + "." + ExpenseBookEntity._ID
                        + " IN "
                        + "( SELECT "
                        + MemberExpenseBookEntity.TABLE_NAME + " ." + MemberExpenseBookEntity.EXPENSE_BOOK_KEY
                        + " FROM "
                        + MemberExpenseBookEntity.TABLE_NAME
                        + " WHERE "
                        + MemberExpenseBookEntity.TABLE_NAME + " ." + MemberExpenseBookEntity
                        .MEMBER_KEY + " = " + id
                        + ")";
                c = db.rawQuery(expenseBookForMemberQuery, null);


                break;*/
            case USER_ID:
                id = uri.getPathSegments().get(1);
                c = db.query(TABLE_NAMES[table], projection, AccountEntity._ID
                                + " = ?", new String[]{id}, null, null, sortOrder,
                        limit
                );
                break;
            case CATEGORY_ID:
                id = uri.getPathSegments().get(1);
                c = db.query(TABLE_NAMES[table], projection, CategoryEntity._ID
                                + " = ?", new String[]{id}, null, null, sortOrder,
                        limit
                );
                break;
            default:
                throw new UnsupportedOperationException("Not yet implemented");
        }
        return c;

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int match = findMatch(uri, "insert");
        int result;
        Context context = getContext();

        SQLiteDatabase db = getWritableableDatabase(context);
        int table = match >> BASE_SHIFT;

        switch (match) {
            case EXPENSE:
            case MEMBER:
            case EXPENSE_BOOK:
            case USER:
            case CATEGORY:
            case MEMBER_EXPANSE:
            case MEMBER_EXPANSE_BOOK:
                result = db.update(TABLE_NAMES[table], values, selection, selectionArgs);
                break;
            case EXPENSE_ID:
                result = db.update(TABLE_NAMES[table], values, selection, selectionArgs);
                break;
            case MEMBER_ID:
                result = db.update(TABLE_NAMES[table], values, selection, selectionArgs);
                break;
            case EXPENSE_BOOK_ID:
                result = db.update(TABLE_NAMES[table], values, selection, selectionArgs);
                break;
            case USER_ID:
                result = db.update(TABLE_NAMES[table], values, selection, selectionArgs);
                break;
            case CATEGORY_ID:
                result = db.update(TABLE_NAMES[table], values, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Not yet implemented");
        }
        return result;
    }

    /**
     * Wrap the UriMatcher call so we can throw a runtime exception if an
     * unknown Uri is passed in
     *
     * @param uri the Uri to match
     * @return the match value
     */
    private int findMatch(Uri uri, String methodName) {
        int match = sURIMatcher.match(uri);
        if (match < 0) {
            throw new IllegalArgumentException("Unknown uri: " + uri);
        } else {
            /*Logger.debug(ContentProvider.class.getName(), methodName + ": uri="
                    + uri + ", match is " + match);*/
        }
        return match;
    }

    /**
     * return the readable database instance.
     *
     * @return {@link SQLiteDatabase}
     */
    private SQLiteDatabase getReadableDatabase(Context context) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        if (mReadDatabase == null) {
            mReadDatabase = dbHelper.getReadableDatabase();
        }
        return mReadDatabase;
    }

    /**
     * return the writable database instance.
     *
     * @return {@link SQLiteDatabase}
     */

    private SQLiteDatabase getWritableableDatabase(Context context) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        if (mWriteDatabase == null) {
            mWriteDatabase = dbHelper.getWritableDatabase();
        }
        return mWriteDatabase;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void shutdown() {
        if (mWriteDatabase != null) {
            mWriteDatabase.close();
        }
        if (mReadDatabase != null) {
            mReadDatabase.close();
        }
        super.shutdown();
    }
}
