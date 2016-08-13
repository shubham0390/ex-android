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

package com.km2labs.android.spendview.disribution;

import com.km2labs.spendview.android.R;
import com.km2labs.android.spendview.core.mvp.lce.MVPLCEView;
import com.km2labs.android.spendview.core.mvp.MVPFragment;

/**
 * Created by Subham Tyagi,
 * on 15/Sep/2015,
 * 4:53 PM
 * TODO:Add class comment.
 */
public class ExpenseDistributionFragment extends MVPFragment<DistributionPresenter> implements MVPLCEView<DistributionModel> {


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_distribution;
    }

    @Override
    public void showLoading(boolean pullToRefresh) {

    }

    @Override
    public void showContent() {

    }

    @Override
    public void showError(Throwable e, boolean pullToRefresh) {

    }

    @Override
    public void setData(DistributionModel data) {

    }

    @Override
    public void loadData(boolean pullToRefresh) {

    }

}
