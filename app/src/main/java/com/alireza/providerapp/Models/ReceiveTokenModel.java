package com.alireza.providerapp.Models;

/**
 * Created by alireza on 3/29/18.
 */

public class ReceiveTokenModel {

    String Token;
    boolean IsUserRegistered;



    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public boolean isUserRegistered() {
        return IsUserRegistered;
    }

    public void setUserRegistered(boolean userRegistered) {
        IsUserRegistered = userRegistered;
    }
}
