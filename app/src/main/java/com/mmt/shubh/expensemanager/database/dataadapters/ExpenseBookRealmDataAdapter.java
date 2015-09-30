package com.mmt.shubh.expensemanager.database.dataadapters;

import android.content.Context;
import android.database.Cursor;

import com.mmt.shubh.expensemanager.database.api.ExpenseBookDataAdapter;
import com.mmt.shubh.expensemanager.database.content.ExpenseBook;
import com.mmt.shubh.expensemanager.database.content.Member;
import com.mmt.shubh.expensemanager.database.content.contract.ExpenseBookContract;

import java.util.List;

import io.realm.RealmList;

/**
 * Created by Subham Tyagi,
 * on 03/Jul/2015,
 * 4:04 PM
 * TODO:Add class comment.
 */
public class ExpenseBookRealmDataAdapter extends AbsRealmDataAdapter<ExpenseBook> implements ExpenseBookDataAdapter, ExpenseBookContract {


    public ExpenseBookRealmDataAdapter(Context context) {
        super(context);
    }

    public static Cursor getAllExpenseBook(Context context) {
        return context.getContentResolver().query(EXPENSE_BOOK_URI, null, null, null,
                ExpenseBookContract.EXPENSE_BOOK_NAME);
    }


    private Member loadMember(long aLong) {
        MemberRealmDataAdapter sqlDataAdapter = new MemberRealmDataAdapter(mContext);
        return sqlDataAdapter.get(aLong);
    }


    public void addMembers(RealmList<Member> members, ExpenseBook expenseBook) {
        expenseBook.setMemberList(members);
    }

    @Override
    public long create(ExpenseBook expenseBook) {
        expenseBook = save(expenseBook);
        return expenseBook.getId();
    }

    @Override
    public ExpenseBook delete(ExpenseBook expenseBook) {
        return null;
    }

    @Override
    public ExpenseBook delete(long id) {
        return null;
    }


    @Override
    public ExpenseBook get(long id) {
        return super.restoreContentWithField(ExpenseBook.class,ExpenseBookContract._ID,id);
    }

    @Override
    public List<ExpenseBook> getAll() {
        return null;
    }

    @Override
    public void addMember(ExpenseBook expenseBook) {

    }
}
