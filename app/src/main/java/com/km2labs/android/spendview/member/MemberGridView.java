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

package com.km2labs.android.spendview.member;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.km2labs.android.spendview.core.recyclerview.GridRecyclerView;
import com.km2labs.android.spendview.core.recyclerview.ListRecyclerView;
import com.km2labs.android.spendview.database.content.Member;

import java.util.List;


public class MemberGridView extends FrameLayout {

    private MemberAdapter mMemberAdapter;

    public MemberGridView(Context context) {
        super(context);
    }

    public MemberGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MemberGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MemberGridView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void init(Context context) {
        GridRecyclerView listRecyclerView = new GridRecyclerView(context);
        addView(listRecyclerView);
        mMemberAdapter = new MemberAdapter();
        mMemberAdapter.setCanDelete(false);
        listRecyclerView.setAdapter(mMemberAdapter);
        listRecyclerView.setOnItemClickListener(mItemClickListener);
    }

    public void addData(List<Member> members) {
        mMemberAdapter.setMembers(members);
    }

    public void showEmptyMessage() {
    }

    private ListRecyclerView.OnItemClickListener mItemClickListener = (parent, view, position, id) -> {
        //tyrgud
        return false;
    };
}
