package com.km2labs.android.spendview.database;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.km2labs.android.spendview.database.api.exceptions.ContentNotFoundException;
import com.km2labs.android.spendview.database.content.ExpenseBook;
import com.km2labs.android.spendview.database.content.Member;
import com.km2labs.android.spendview.settings.UserSettings;

import java.util.Map;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by : Subham Tyagi
 * Created on :  19/09/16.
 */

public class FirebaseDataManager {

    private static final String FIREBASE_CHILD_MEMBERS = "members";
    private static final String FIREBASE_CHILD_EXPENSEBOOKS = "expensebooks";
    private static final String FIREBASE_CHILD_EXPENSES = "expenses";
    private static final String FIREBASE_CHILD_ACCOUNTS = "accounts";
    private static final String FIREBASE_CHILD_TRANSACTIONS = "transactions";
    private static final String FIREBASE_CHILD_CARD_DETAILS = "cardDetails";
    private static final String FIREBASE_CHILD_CATEGORY = "categories";

    FirebaseDatabase mFirebaseDatabase;

    @Inject
    public FirebaseDataManager(FirebaseDatabase firebaseDatabase) {
        mFirebaseDatabase = firebaseDatabase;
    }

    public void createExpenseBook(ExpenseBook expenseBook) {
        mFirebaseDatabase.getReference(FIREBASE_CHILD_EXPENSEBOOKS).child(getExpenseBookId(expenseBook.getName())).setValue(expenseBook);
    }

    public void addMemberToExpenseBook(Map<String, Boolean> memberMap, String name, String phoneNo) {
        mFirebaseDatabase.getReference(FIREBASE_CHILD_EXPENSEBOOKS).child(FIREBASE_CHILD_MEMBERS).setValue(memberMap);
    }

    private String getExpenseBookId(String name) {
        String phoneNo = UserSettings.getInstance().getUser().getPhoneNumber();
        return phoneNo + name;
    }

    public Observable<ExpenseBook> getExpenseBookWithName(String expenseName) {
        final ExpenseBook[] expenseBook = {null};
        return Observable.create(subscriber -> {

            mFirebaseDatabase.getReference(FIREBASE_CHILD_EXPENSEBOOKS)
                    .child(getExpenseBookId(expenseName))
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                expenseBook[0] = (ExpenseBook) dataSnapshot.getValue();
                                subscriber.onNext(expenseBook[0]);
                                subscriber.onCompleted();
                                return;
                            }
                            subscriber.onError(new ContentNotFoundException("No expense book found"));
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            subscriber.onError(new IllegalStateException(databaseError.getMessage()));
                        }
                    });
        });
    }

    public void createMember(Member member) {
        mFirebaseDatabase.getReference(FIREBASE_CHILD_MEMBERS).child(member.getMemberPhoneNumber()).setValue(member);
    }

    public Observable<Member> getMember(String memberPhoneNumber) {
        final Member[] member = {null};
        return Observable.create(subscriber -> {
            mFirebaseDatabase.getReference(FIREBASE_CHILD_MEMBERS)
                    .child(memberPhoneNumber)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                member[0] = (Member) dataSnapshot.getValue();
                                subscriber.onNext(member[0]);
                                subscriber.onCompleted();
                                return;
                            }
                            subscriber.onError(new ContentNotFoundException("No Member found"));
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            subscriber.onError(new IllegalStateException(databaseError.getMessage()));
                        }
                    });
        });
    }


}
