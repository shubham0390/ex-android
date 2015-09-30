/*
 * Copyright (c) 2014. The MMT group Project
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.mmt.shubh.expensemanager.database.dataadapters;

import android.content.Context;

import com.mmt.shubh.expensemanager.database.content.ExpenseBook;

import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by Subham Tyagi,
 * on 06/Sep/2015,
 * 5:49 PM
 * TODO:Add class comment.
 */
public abstract class AbsRealmDataAdapter<T extends RealmObject> {

    /* Database Name */
    public static final String DATABASE_NAME = "Expense_Manager.realm";

    /* Database version */
    public static final int DATABASE_VERSION = 1;

    protected Context mContext;

    public AbsRealmDataAdapter(Context context) {
        mContext = context;
    }

    /* The RealmConfiguration is created using the builder pattern.*/

    public T save(T t) {
        Realm  mRealm = Realm.getDefaultInstance();
        mRealm.executeTransaction(realm -> mRealm.copyToRealm(t));

        return t;
    }

    public T restoreContentWithField(Class<T> tClass, String filedName, String value, boolean caseSensitive) {
        Realm  mRealm = Realm.getDefaultInstance();
        RealmQuery<T> query = mRealm.where(tClass);
        query.equalTo(filedName, value, caseSensitive);
        return query.findFirst();
    }

    public T restoreContentWithField(Class<T> tClass, String filedName, int value) {
        Realm  mRealm = Realm.getDefaultInstance();
        RealmQuery<T> query = mRealm.where(tClass);
        query.equalTo(filedName, value);
        return query.findFirst();
    }

    public T restoreContentWithField(Class<T> tClass, String filedName, long value) {
        Realm  mRealm = Realm.getDefaultInstance();
        RealmQuery<T> query = mRealm.where(tClass);
        query.equalTo(filedName, value);
        return query.findFirst();
    }

    public T restoreContentWithField(Class<T> tClass, String filedName, Date value) {
        Realm  mRealm = Realm.getDefaultInstance();
        RealmQuery<T> query = mRealm.where(tClass);
        query.equalTo(filedName, value);
        return query.findFirst();
    }

    public List<T> restoreAllContent(Class<T> tClass) {
        Realm  mRealm = Realm.getDefaultInstance();
        RealmResults<T> results = mRealm.allObjects(tClass);
        List<T> realmList = new RealmList<>();
        for (T result : results) {
            realmList.add(result);
        }
        return realmList;
    }

    /**
     * Check if any group present for provided group name.
     *
     * @return <code>true</code> if {@link  ExpenseBook} already present otherwise
     * <code>false</code>.
     */
    public boolean isExsist(Class<T> tClass, String filedName, String value, boolean caseSensitive) {
        Realm  mRealm = Realm.getDefaultInstance();
        RealmQuery<T> query = mRealm.where(tClass);
        query.equalTo(filedName, value, caseSensitive);
        return query.findFirst() != null;
    }

    /**
     * Check if any group present for provided group name.
     *
     * @return <code>true</code> if {@link  ExpenseBook} already present otherwise
     * <code>false</code>.
     */
    public boolean isExsist(Class<T> tClass, String filedName, int value) {
        Realm  mRealm = Realm.getDefaultInstance();
        RealmQuery<T> query = mRealm.where(tClass);
        query.equalTo(filedName, value);
        return query.findFirst() != null;
    }

    /**
     * Check if any group present for provided group name.
     *
     * @return <code>true</code> if {@link  ExpenseBook} already present otherwise
     * <code>false</code>.
     */
    public boolean isExsist(Class<T> tClass, String filedName, long value) {
        Realm  mRealm = Realm.getDefaultInstance();
        RealmQuery<T> query = mRealm.where(tClass);
        query.equalTo(filedName, value);
        return query.findFirst() != null;
    }

    /**
     * Check if any group present for provided group name.
     *
     * @return <code>true</code> if {@link  ExpenseBook} already present otherwise
     * <code>false</code>.
     */
    public boolean isExsist(Class<T> tClass, String filedName, Date value) {
        Realm  mRealm = Realm.getDefaultInstance();
        RealmQuery<T> query = mRealm.where(tClass);
        query.equalTo(filedName, value);
        return query.findFirst() != null;
    }

    public T update(T t) {
        Realm  mRealm = Realm.getDefaultInstance();
        mRealm.executeTransaction(realm -> mRealm.copyToRealmOrUpdate(t));
        return t;
    }

    public void deleteAll(Class<T> tClass) {
        Realm  mRealm = Realm.getDefaultInstance();
        mRealm.executeTransaction(realm -> mRealm.clear(tClass));

    }

    public long create(List<T> list) {
        Realm  mRealm = Realm.getDefaultInstance();
        mRealm.executeTransaction(realm -> realm.copyToRealm(list));
        return 0;
    }
}
