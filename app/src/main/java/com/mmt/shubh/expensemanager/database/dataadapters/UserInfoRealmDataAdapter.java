package com.mmt.shubh.expensemanager.database.dataadapters;

import android.content.Context;

import com.mmt.shubh.expensemanager.database.api.UserInfoDataAdapter;
import com.mmt.shubh.expensemanager.database.content.UserInfo;
import com.mmt.shubh.expensemanager.database.content.contract.UserInfoContract;

import java.util.List;

/**
 * Created by Subham Tyagi,
 * on 01/Jul/2015,
 * 7:26 AM
 * TODO:Add class comment.
 */
public class UserInfoRealmDataAdapter extends AbsRealmDataAdapter<UserInfo> implements UserInfoDataAdapter<UserInfo>, UserInfoContract {


    public UserInfoRealmDataAdapter(Context context) {
        super(context);
    }


    @Override
    public long create(UserInfo userInfo) {
        super.save(userInfo);
        return 0;
    }

    @Override
    public UserInfo delete(UserInfo userInfo) {
        return delete(userInfo.getId());
    }

    @Override
    public UserInfo delete(long id) {
        return null;
    }

    @Override
    public UserInfo get(long id) {
        return restoreContentWithField(UserInfo.class,UserInfoContract._ID,id);
    }

    @Override
    public List<UserInfo> getAll() {
        return restoreAllContent(UserInfo.class);
    }


}
