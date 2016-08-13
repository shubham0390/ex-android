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

package com.km2labs.android.spendview.expensebook.setting;

import com.km2labs.android.spendview.database.content.Member;
import com.km2labs.android.spendview.core.mvp.MVPView;

/**
 * Created by Subham Tyagi,
 * on 16/Sep/2015,
 * 4:59 PM
 * TODO:Add class comment.
 */
public interface IExpenseBookSettingView extends MVPView {
    void onOwnerLoaded(Member member);
}
