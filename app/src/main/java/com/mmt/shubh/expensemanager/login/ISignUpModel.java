package com.mmt.shubh.expensemanager.login;


import com.mmt.shubh.expensemanager.setup.ProfileFetcher;

import rx.Observable;

/**
 * Created by Subham Tyagi,
 * on 19/Aug/2015,
 * 11:31 PM
 * TODO:Add class comment.
 */
public interface ISignUpModel {

    Observable<Boolean> registerUserWithSocial(ProfileFetcher profileFetcher);
}
