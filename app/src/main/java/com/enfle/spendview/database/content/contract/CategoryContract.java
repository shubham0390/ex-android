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

package com.enfle.spendview.database.content.contract;

import android.provider.BaseColumns;

public interface CategoryContract extends BaseColumns, BaseContract {
    String TABLE_NAME = "expense_category";
    String PATH_CATEGORY = "Category";

    int ID_COLUMN = 0;
    int NAME_COLUMN = 1;
    String CATEGORY_NAME = "category_name";
    String CATEGORY_TYPE = "category_type";
    String CATEGORY_IMAGE_NAME = "category_image";
}