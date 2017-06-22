package com.enfle.spendview.login;

/**
 * Created by subhamtyagi on 22/06/17.
 */

public class LoginResponse {

    private LoginType mLoginType;
    private String mToken;

    public LoginResponse(LoginType loginType, String token) {
        this.mLoginType = loginType;
        this.mToken = token;
    }

    public LoginType getLoginType() {
        return mLoginType;
    }

    public void setLoginType(LoginType mLoginType) {
        this.mLoginType = mLoginType;
    }

    public String getToken() {
        return mToken;
    }

    public void setToken(String mToken) {
        this.mToken = mToken;
    }
}
