package com.mmt.shubh.expensemanager.setup;

import android.content.Context;

import com.mmt.shubh.expensemanager.UserSettings;
import com.mmt.shubh.expensemanager.database.content.Member;
import com.mmt.shubh.expensemanager.database.content.UserInfo;
import com.mmt.shubh.expensemanager.database.dataadapters.MemberSQLDataAdapter;
import com.mmt.shubh.expensemanager.database.dataadapters.UserInfoSQLDataAdapter;

/**
 * Created by styagi on 6/4/2015.
 */
public abstract class ProfileFetcher {

    public abstract UserInfo fetchUserAccountDetails(Context context);

    public void saveUser(Context context, UserInfo userInfo) {
        UserInfoSQLDataAdapter sqlDataAdapter = new UserInfoSQLDataAdapter(context);
        Member member = createMember(userInfo, context);
        userInfo.setMemberKey(member.getId());
        sqlDataAdapter.create(userInfo);
        UserSettings userSettings = UserSettings.getInstance();
        userSettings.setUserInfo(userInfo);
    }

    public Member createMember(UserInfo userInfo, Context context) {
        Member member = new Member();
        if (userInfo != null) {
            member.setCoverPhotoUrl(userInfo.getCoverPhotoUrl());
            member.setMemberEmail(userInfo.getEmailAddress());
            member.setMemberName(userInfo.getDisplayName());
            member.setProfilePhotoUrl(userInfo.getProfilePhotoUrl());
            member.setMemberPhoneNumber(userInfo.getPhoneNumber());
        }
        MemberSQLDataAdapter sqlMemberDataAdapter = new MemberSQLDataAdapter(context);
        sqlMemberDataAdapter.create(member);
        return member;
    }

    public void update(Context context, UserInfo userInfo) {
        UserInfoSQLDataAdapter sqlDataAdapter = new UserInfoSQLDataAdapter(context);
        sqlDataAdapter.update(userInfo);
    }
}
