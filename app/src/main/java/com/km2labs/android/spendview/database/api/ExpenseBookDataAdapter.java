/*
 * Copyright (c) 2016. . The Km2Labs Project
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.km2labs.android.spendview.database.api;

import com.km2labs.android.spendview.database.content.Member;
import com.km2labs.android.spendview.database.content.ExpenseBook;

import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by styagi on 5/28/2015.
 */
public interface ExpenseBookDataAdapter extends IDataAdapter<ExpenseBook> {

    void addMember(ExpenseBook t);

    void addMembers(List<Member> memberList, long expenseBookId);

    void addMembers(long expenseBookId, List<Long> memberList);

    void addMembers(List<Long> members, ExpenseBook expenseBook);

    Observable<List<ExpenseBook>> getByMemberId(long id);


    void addMembers(Map<Long, Long> expenseBooks);

    Observable<List<ExpenseBook>> getPrivateExpenseBook();
}
