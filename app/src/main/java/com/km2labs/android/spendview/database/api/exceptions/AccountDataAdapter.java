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

package com.km2labs.android.spendview.database.api.exceptions;

import com.km2labs.android.spendview.database.api.IDataAdapter;
import com.km2labs.android.spendview.database.content.Account;

import java.util.List;

import rx.Observable;

/**
 * Created by Subham Tyagi,
 * on 03/Jul/2015,
 * 3:30 PM
 * TODO:Add class comment.
 */
public interface AccountDataAdapter extends IDataAdapter<Account> {
    double getAccountBalance(long accountId);

    void updateAmount(long accountId, double balanceAmount);

    Observable<List<Account>> loadAllAccounts();

    Observable<List<Account>> getAccountByMember(long memberId);
}
