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

package com.km2labs.android.spendview.expense.adapters;

import com.km2labs.android.spendview.expense.ExpenseListViewModel;
import com.km2labs.android.spendview.core.recyclerview.adapter.section.BaseSection;
import com.km2labs.android.spendview.core.recyclerview.adapter.section.BaseSectionIndexer;
import com.km2labs.android.spendview.utils.DateUtil;

import org.threeten.bp.Instant;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;


public class ExpenseTimeSectionIndexer extends BaseSectionIndexer<ExpenseListViewModel> {

    @Override
    protected BaseSection getSectionForData(ExpenseListViewModel data) {
        ExpenseSection section = new ExpenseSection();
        section.update(data.getExpenseAmount());
        long time = data.getExpenseDateInMill();
        Instant instant = Instant.ofEpochMilli(time);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        String mnth = DateUtil.getMonthName(localDateTime.getMonthValue());
        int year = localDateTime.getYear();
        section.mSectionName = mnth + ", " + year;
        return section;
    }
}
