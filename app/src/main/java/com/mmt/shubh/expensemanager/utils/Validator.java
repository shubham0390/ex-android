package com.mmt.shubh.expensemanager.utils;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;

import com.mmt.shubh.expensemanager.database.content.contract.ExpenseBookContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by STyagi on 5/3/2014.
 */
public class Validator {

    /**
     * Check if provided string as correct email format or not.
     * It does not check the validity of email address
     *
     * @param emailAdrress
     * @return
     */
    public static boolean isEmailAddressValid(String emailAdrress) {
        // Check for a valid email address.
        if (TextUtils.isEmpty(emailAdrress)) {
            return false;
        }


        return false;
    }
    public static boolean expenseNameExist(Context context, String expenseName){
        Cursor cursor = context.getContentResolver().query(ExpenseBookContract.EXPENSE_BOOK_URI,
                new String[]{ExpenseBookContract.EXPENSE_BOOK_NAME}, null, null, null);
        List<String> expenseBookNames = new ArrayList<>();
        try {
            if (cursor != null && cursor.moveToFirst()){
                do{
                    expenseBookNames.add(cursor.getString(cursor.getColumnIndexOrThrow
                            (ExpenseBookContract.EXPENSE_BOOK_NAME)));
                }while (cursor.moveToNext());
            }
        }finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        if (expenseBookNames.size() == 0){
            return false;
        }
        return expenseBookNames.contains(expenseName);
    }
}
