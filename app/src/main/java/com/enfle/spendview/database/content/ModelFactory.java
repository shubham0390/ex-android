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

package com.enfle.spendview.database.content;

/**
 * Created by subhamtyagi on 3/12/16.
 */
public class ModelFactory {


    public static Expense getExpense() {
        return new Expense();
    }

    public static User getUserInfo() {
        return new User();
    }

    public static MemberExpense getNewMemberExpense() {
        return new MemberExpense();
    }

    public static ExpenseCategory getCategory() {
        return new ExpenseCategory();
    }
}
